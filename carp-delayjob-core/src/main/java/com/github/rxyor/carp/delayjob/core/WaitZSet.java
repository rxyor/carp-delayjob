package com.github.rxyor.carp.delayjob.core;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;

/**
 *<p>
 * 等待队列
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public class WaitZSet extends ClientConfig {

    public WaitZSet(RedissonClient redissonClient, KeyConfig keyConfig) {
        super(redissonClient, keyConfig);
    }

    /**
     *<p>
     *  添加
     *</p>
     *
     * @author liuyang
     * @since 2020-07-08 18:11:47 v1.0
     * @param jobId jobId
     * @param score score
     * @return [boolean]
     */
    public boolean offer(String jobId, Long score) {
        if (StringUtils.isBlank(jobId)) {
            throw new IllegalArgumentException("jobId can't be blank");
        }
        if (score < 0) {
            throw new IllegalArgumentException("score must >= 0");
        }

        RScoredSortedSet<String> bucket = redissonClient.getScoredSortedSet(keyConfig.waitQueueKey());
        return bucket.add(score, jobId);
    }

    /**
     *<p>
     *  取出
     *</p>
     *
     * @author liuyang
     * @since 2020-07-08 18:12:29 v1.0
     * @return [RScoredSortedSet < String>]
     */
    public RScoredSortedSet<String> getRScoredSortedSet() {
        return redissonClient.getScoredSortedSet(keyConfig.waitQueueKey());
    }


}
