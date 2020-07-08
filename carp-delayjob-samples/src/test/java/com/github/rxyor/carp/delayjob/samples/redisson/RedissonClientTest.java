package com.github.rxyor.carp.delayjob.samples.redisson;

import com.github.rxyor.carp.delayjob.samples.SpringWithJUnit5IT;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public class RedissonClientTest extends SpringWithJUnit5IT {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test1() {
        RBucket<Object> bucket = redissonClient.getBucket("a");
        Data data = new StringData();
        bucket.set(data);
    }
}
