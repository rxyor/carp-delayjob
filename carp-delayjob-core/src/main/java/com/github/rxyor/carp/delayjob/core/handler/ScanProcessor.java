package com.github.rxyor.carp.delayjob.core.handler;

import com.github.rxyor.carp.delayjob.core.consumer.Consumer;
import com.github.rxyor.carp.delayjob.core.model.DelayJob;
import com.github.rxyor.carp.delayjob.core.model.Result;
import com.github.rxyor.carp.delayjob.core.producer.Producer;
import com.github.rxyor.carp.delayjob.core.repository.FailJobSetRepository;
import com.github.rxyor.carp.delayjob.core.repository.JobDetailMapRepository;
import com.github.rxyor.carp.delayjob.core.repository.ReadyJobQueueRepository;
import com.github.rxyor.carp.delayjob.core.repository.WaitJobZSetRepository;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RScoredSortedSet;
import org.redisson.client.protocol.ScoredEntry;

/**
 *<p>
 * 扫描任务
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
@Slf4j
public class ScanProcessor {

    private final static ScheduledThreadPoolExecutor SCAN_THREAD = new ScheduledThreadPoolExecutor(1);
    private final static ExecutorService WORKER_THREAD = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(4096), Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.error("the task is discarded:{}", r);
        }
    });
    private final AtomicBoolean shutdown = new AtomicBoolean(false);

    private final WaitJobZSetRepository waitJobZSetRepository;
    private final ReadyJobQueueRepository readyJobQueueRepository;
    private final JobDetailMapRepository jobDetailMapRepository;
    private final FailJobSetRepository failJobSetRepository;
    private final Producer producer;
    private final JobHandlerDelegate jobHandlerDelegate;

    public ScanProcessor(WaitJobZSetRepository waitJobZSetRepository, ReadyJobQueueRepository readyJobQueueRepository,
        JobDetailMapRepository jobDetailMapRepository, FailJobSetRepository failJobSetRepository,
        Producer producer, JobHandlerDelegate delegate) {
        Objects.requireNonNull(waitJobZSetRepository, "waitZSet can't be null");
        Objects.requireNonNull(readyJobQueueRepository, "readyQueue can't be null");
        Objects.requireNonNull(jobDetailMapRepository, "jobStoreMap can't be null");
        Objects.requireNonNull(failJobSetRepository, "failJobSet can't be null");
        Objects.requireNonNull(delegate, "delegate can't be null");
        Objects.requireNonNull(producer, "producer can't be null");

        this.waitJobZSetRepository = waitJobZSetRepository;
        this.readyJobQueueRepository = readyJobQueueRepository;
        this.jobDetailMapRepository = jobDetailMapRepository;
        this.failJobSetRepository = failJobSetRepository;
        this.jobHandlerDelegate = delegate;
        this.producer = producer;
    }

    private static void sleepSeconds(Long seconds) {
        if (seconds == null || seconds <= 0) {
            return;
        }
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 开始扫描和处理任务
     */
    public synchronized void startup() {
        shutdown.set(false);
        WORKER_THREAD.submit(() -> {
            doScan();
            sleepSeconds(5L);
            doProcess();
        });
    }

    /**
     * 关闭任务
     */
    public synchronized void shutdown() {
        shutdown.set(true);
        if (!SCAN_THREAD.isShutdown()) {
            SCAN_THREAD.shutdown();
        }
    }

    /**
     * 扫描出就绪的任务
     */
    protected void doScan() {
        SCAN_THREAD.scheduleAtFixedRate(this::popsNowAndPushToReady, 30L, 1L, TimeUnit.SECONDS);
    }

    /**
     * 处理任务
     */
    private void doProcess() {
        WORKER_THREAD.submit(() -> {
            while (!shutdown.get()) {
                processJobFromReadyQueue();
            }
        });
    }

    /**
     * 从就绪队列中取出任务并处理
     */
    private void processJobFromReadyQueue() {
        if (jobHandlerDelegate.getConsumers().isEmpty()) {
            sleepSeconds(60L);
            return;
        }
        List<String> topics = jobHandlerDelegate.getConsumers().stream().map(Consumer::topic)
            .collect(Collectors.toList());
        for (String topic : topics) {
            DelayJob<?> delayJob = popReadyJob(topic);
            if (delayJob != null) {
                Result result = jobHandlerDelegate.consume(delayJob);
                //消费失败重试
                if (!Result.SUCCESS.equals(result)) {
                    if (delayJob.getRetryTimes() > 0) {
                        long newDelay = 3;
                        if (delayJob.getRetryDelay() > 0L) {
                            newDelay = delayJob.getRetryDelay();
                        }
                        delayJob.setExecTime(System.currentTimeMillis() + newDelay * 1000L);
                        delayJob.setRetryTimes(delayJob.getRetryTimes() - 1);
                        producer.offer(delayJob);
                    } else {
                        //记录失败JOB
                        failJobSetRepository.add(delayJob);
                    }

                }
            }

        }

    }

    /**
     * 从就绪队列中取任务
     *
     * @param topic 任务类型
     * @param <T> 消息类型
     * @return DelayJob
     */
    public <T extends Serializable> DelayJob<T> popReadyJob(String topic) {
        String jobId = readyJobQueueRepository.poll(topic);
        DelayJob<T> delayJob = null;
        if (jobId != null) {
            delayJob = jobDetailMapRepository.get(jobId);
            if (delayJob != null) {
                jobDetailMapRepository.remove(jobId);
            }
        }
        return delayJob;
    }

    /**
     * 把就绪任务放到就绪队列
     */
    public void popsNowAndPushToReady() {
        long start = 0L;
        long end = System.currentTimeMillis();
        RScoredSortedSet<String> bucket = waitJobZSetRepository.getRScoredSortedSet();
        Collection<ScoredEntry<String>> readyJobs = bucket.entryRange(start, true, end, true);
        for (ScoredEntry<String> entry : readyJobs) {
            String jobId = entry.getValue();
            if (StringUtils.isBlank(jobId)) {
                continue;
            }

            DelayJob<?> delayJob = jobDetailMapRepository.get(jobId);
            if (delayJob != null) {
                readyJobQueueRepository.offer(delayJob.getTopic(), delayJob.getId());
            }
            bucket.remove(jobId);
        }
    }
}
