package com.github.rxyor.carp.delayjob.core.repository;

import com.github.rxyor.carp.delayjob.core.model.KeyConfig;
import com.github.rxyor.carp.delayjob.core.model.ClientConfig;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;

/**
 *<p>
 *  就绪队列
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public class ReadyJobQueueRepository extends ClientConfig {

    public ReadyJobQueueRepository(RedissonClient redissonClient, KeyConfig keyConfig) {
        super(redissonClient, keyConfig);
    }

    /**
     *<p>
     *  添加
     *</p>
     *
     * @author liuyang
     * @since 2020-07-08 15:45:46 v1.0
     * @param topic topic
     * @param jobId jobId
     * @return boolean
     */
    public boolean offer(String topic, String jobId) {
        if (StringUtils.isBlank(topic)) {
            throw new IllegalArgumentException("topic can't be blank");
        }
        if (StringUtils.isBlank(jobId)) {
            throw new IllegalArgumentException("jobId can't be blank");
        }

        RBlockingQueue<String> queue = redissonClient.getBlockingQueue(keyConfig.readyQueueKey(topic));
        return queue.offer(jobId);
    }

    /**
     *<p>
     * 取出并删除
     *</p>
     *
     * @author liuyang
     * @since 2020-07-08 15:48:17 v1.0
     * @param topic topic
     * @return [T data]
     */
    public String poll(String topic) {
        if (StringUtils.isBlank(topic)) {
            throw new IllegalArgumentException("topic can't be blank");
        }

        RBlockingQueue<String> queue = redissonClient.getBlockingQueue(keyConfig.readyQueueKey(topic));
        return queue.poll();
    }

    /**
     *<p>
     * 删除
     *</p>
     *
     * @author liuyang
     * @since 2020-07-08 15:53:52 v1.0
     * @param topic topic
     * @param jobId jobId
     * @return boolean
     */
    public boolean remove(String topic, String jobId) {
        if (StringUtils.isBlank(topic)) {
            throw new IllegalArgumentException("topic can't be blank");
        }
        if (StringUtils.isBlank(jobId)) {
            throw new IllegalArgumentException("jobId can't be blank");
        }

        RBlockingQueue<String> queue = redissonClient.getBlockingQueue(keyConfig.readyQueueKey(topic));
        return queue.remove(jobId);
    }
}
