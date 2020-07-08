package com.github.rxyor.carp.delayjob.core.repository;

import com.github.rxyor.carp.delayjob.core.model.KeyConfig;
import com.github.rxyor.carp.delayjob.core.model.ClientConfig;
import com.github.rxyor.carp.delayjob.core.model.DelayJob;
import java.io.Serializable;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public class JobDetailMapRepository extends ClientConfig {

    public JobDetailMapRepository(RedissonClient redissonClient,
        KeyConfig keyConfig) {
        super(redissonClient, keyConfig);
    }

    /**
     * 任务详情添加至任务池中
     *
     * @param delayJob 任务
     * @param <T> 任务泛型
     */
    public <T extends Serializable> void add(DelayJob<T> delayJob) {
        RMap<String, DelayJob<T>> map = redissonClient.getMap(keyConfig.jobStoreMapKey());
        map.put(delayJob.getId(), delayJob);
    }

    /**
     * 从任务池中获取任务详情
     *
     * @param jobId 任务ID
     * @param <T> 任务泛型
     * @return DelayJob
     */
    public <T extends Serializable> DelayJob<T> get(String jobId) {
        RMap<String, DelayJob<T>> map = redissonClient.getMap(keyConfig.jobStoreMapKey());
        return map.get(jobId);
    }

    /**
     * 删除任务
     *
     * @param jobId 任务ID
     */
    public void remove(String jobId) {
        RMap<String, DelayJob<?>> map = redissonClient.getMap(keyConfig.jobStoreMapKey());
        map.remove(jobId);
    }

    /**
     * 清空任务
     */
    public void clear() {
        RMap<String, DelayJob<?>> map = redissonClient.getMap(keyConfig.jobStoreMapKey());
        map.clear();
    }

}
