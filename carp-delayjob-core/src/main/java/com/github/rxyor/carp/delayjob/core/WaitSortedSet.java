package com.github.rxyor.carp.delayjob.core;

import java.util.Collection;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;

/**
 *<p>
 * 等待队列
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public class WaitSortedSet extends ClientConfig {

    public WaitSortedSet(RedissonClient redissonClient, KeyConfig keyConfig) {
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
     * @param startTime startTime
     * @param endTime endTime
     * @return [Collection<ScoredEntry < String>>]
     */
    public Collection<ScoredEntry<String>> range(Long startTime, Long endTime) {
        if (startTime < 0) {
            throw new IllegalArgumentException("startTime must >=0");
        }
        if (endTime < 0) {
            throw new IllegalArgumentException("endTime must >=0");
        }
        if (endTime < startTime) {
            throw new IllegalArgumentException("endTime must >= startTime");
        }

        RScoredSortedSet<String> bucket = redissonClient.getScoredSortedSet(keyConfig.waitQueueKey());
        return bucket.entryRange(startTime, true, endTime, true);
    }


}
