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
public class ProducerImpl {

    private final KeyConfig keyConfig;
    private final RedisQueue<String> waitQueue;

    public ProducerImpl(KeyConfig keyConfig, RedisQueue<String> waitQueue) {
        Objects.requireNonNull(keyConfig, "keyConfig can't be null");
        Objects.requireNonNull(waitQueue, "waitQueue can't be null");

        this.keyConfig = keyConfig;
        this.waitQueue = waitQueue;
    }

    /**
     * 添加延时任务
     *
     * @param delayJob 任务
     * @param <T> 消息类型
     */
    protected <T extends Serializable> void offer(DelayJob<T> delayJob) {
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

    }

}
