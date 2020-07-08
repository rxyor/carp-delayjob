package com.github.rxyor.carp.delayjob.core;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
@Slf4j
public class JobHandlerDelegate implements JobHandler {

    /**
     * 同步锁
     */
    private final Object LOCK = new Object();
    /**
     * 消费者列表
     */
    private final List<Consumer> consumers = new ArrayList<>(64);

    /**
     * consume DelayJob
     *
     * @param delayJob delayJob
     * @return Result
     */
    @Override
    public Result consume(DelayJob<?> delayJob) {
        List<Result> results = new ArrayList<>(consumers.size());

        for (Consumer consumer : consumers) {
            if (consumer.topic().equals(delayJob.getTopic())) {
                try {
                    Result result = consumer.consume(delayJob);
                    results.add(result);
                    if (log.isDebugEnabled()) {
                        log.debug("consumer:[{}] consume topic:[{}] job:[{}],result:[{}]",
                            consumer.getClass().getName(), consumer.topic(), JSON.toJSONString(delayJob), result);
                    }
                } catch (Throwable e) {
                    log.error("consumer:[{}] consume topic:[{}] job:[{}] fail, error:",
                        consumer.getClass().getName(), consumer.topic(), JSON.toJSONString(delayJob), e);
                }
            }
        }
        if (results.contains(Result.FAIL)) {
            return Result.FAIL;
        } else if (results.contains(Result.LATER)) {
            return Result.LATER;
        }
        return Result.SUCCESS;
    }

    /**
     * 添加任务处理器
     *
     * @param consumer 任务处理器
     * @return Boolean
     */
    public Boolean addConsumer(Consumer consumer) {
        Objects.requireNonNull(consumer, "Consumer can't be null");

        if (StringUtils.isBlank(consumer.id())) {
            throw new IllegalArgumentException("consumer's id can't be blank");
        }
        if (StringUtils.isBlank(consumer.topic())) {
            throw new IllegalArgumentException("topic can't be blank");
        }

        synchronized (LOCK) {
            for (Consumer c : consumers) {
                if (consumer.getClass().getName().equals(c.getClass().getName())) {
                    return false;
                }
            }
            consumers.add(consumer);
        }
        return true;
    }

    /**
     * 移除任务处理器
     *
     * @param consumer 处理器ID
     * @return Boolean
     */
    public Boolean removeConsumer(Consumer consumer) {
        if (consumer == null) {
            return false;
        }
        synchronized (LOCK) {
            return consumers.remove(consumer);
        }
    }

    /**
     * 清空处理器
     */
    public void clearConsumer() {
        synchronized (LOCK) {
            consumers.clear();
        }
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }
}
