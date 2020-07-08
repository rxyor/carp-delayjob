package com.github.rxyor.carp.delayjob.core;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public class FailJobSet extends ClientConfig {

    public FailJobSet(RedissonClient redissonClient, KeyConfig keyConfig) {
        super(redissonClient, keyConfig);
    }

    /**
     * 添加延时任务
     *
     * @param delayJob 任务
     * @param <T> 消息类型
     */
    public <T extends Serializable> void add(DelayJob<T> delayJob) {
        if (delayJob == null || StringUtils.isBlank(delayJob.getId())) {
            return;
        }

        RSet<DelayJob<?>> set = redissonClient.getSet(keyConfig.failJobsKey(delayJob.getId()));
        set.addAsync(delayJob);
        set.expireAsync(7, TimeUnit.DAYS);
    }
}
