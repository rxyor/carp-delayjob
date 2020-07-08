package com.github.rxyor.carp.delayjob.core;

import java.io.Serializable;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 *<p>
 * 生产者实现类
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public class ProducerImpl implements Producer {

    private final WaitZSet waitZSet;
    private final JobStoreMap jobStoreMap;

    public ProducerImpl(WaitZSet waitZSet, JobStoreMap jobStoreMap) {
        Objects.requireNonNull(waitZSet, "waitZSet can't be null");
        Objects.requireNonNull(jobStoreMap, "jobStoreMap can't be null");

        this.waitZSet = waitZSet;
        this.jobStoreMap = jobStoreMap;
    }

    /**
     * 添加延时任务
     *
     * @param topic 任务类型
     * @param delaySeconds 延时时间(单位秒)
     * @param body 消息体
     * @param <T> 消息类型
     */
    @Override
    public <T extends Serializable> void offer(String topic, Long delaySeconds, T body) {
        this.offer(topic, delaySeconds, 0, 0L, body);
    }

    /**
     * 添加延时任务
     *
     * @param topic 任务类型
     * @param delaySeconds 延时时间(单位秒)
     * @param retryTimes 重试次数
     * @param retryDelay 重试间隔
     * @param body 消息体
     * @param <T> 消息类型
     */
    @Override
    public <T extends Serializable> void offer(String topic, Long delaySeconds, Integer retryTimes, Long retryDelay,
        T body) {
        if (delaySeconds == null || delaySeconds < 0L) {
            throw new IllegalArgumentException("delaySeconds must >= 0");
        }
        DelayJob<T> delayJob = new DelayJob<T>(IdUtil.randomId(), topic,
            System.currentTimeMillis() + delaySeconds * 1000L, retryTimes, retryDelay, body);
        offer(delayJob);
    }

    /**
     * 添加延时任务
     *
     * @param delayJob 任务
     * @param <T> 消息类型
     */
    @Override
    public <T extends Serializable> void offer(DelayJob<T> delayJob) {
        if (delayJob == null) {
            throw new IllegalArgumentException("delayJob can't be null");
        }
        if (StringUtils.isBlank(delayJob.getTopic())) {
            throw new IllegalArgumentException("topic can't be blank");
        }
        if (delayJob.getExecTime() == null || delayJob.getExecTime() < 0L) {
            throw new IllegalArgumentException("execTime must >= 0");
        }

        if (StringUtils.isBlank(delayJob.getId())) {
            delayJob.setId(IdUtil.randomId());
        }
        if (delayJob.getRetryTimes() == null || delayJob.getRetryTimes() < 0) {
            delayJob.setRetryTimes(0);
        }
        if (delayJob.getRetryDelay() == null || delayJob.getRetryDelay() < 0) {
            delayJob.setRetryDelay(0L);
        }
        if (StringUtils.isBlank(delayJob.getId())) {
            delayJob.setId(IdUtil.randomId());
        }

        waitZSet.offer(delayJob.getId(), delayJob.getExecTime());
        jobStoreMap.add(delayJob);
    }

}
