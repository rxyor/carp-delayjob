package com.github.rxyor.carp.delayjob.core;

import java.util.Objects;
import org.redisson.api.RedissonClient;

/**
 *<p>
 *  配置
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public class ClientConfig {

    protected final RedissonClient redissonClient;

    public ClientConfig(RedissonClient redissonClient) {
        Objects.requireNonNull(redissonClient, "redissonClient can't be null");
        this.redissonClient = redissonClient;
    }
}
