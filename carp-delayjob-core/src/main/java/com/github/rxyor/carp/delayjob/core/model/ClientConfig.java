package com.github.rxyor.carp.delayjob.core.model;

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
    protected final KeyConfig keyConfig;

    public ClientConfig(RedissonClient redissonClient, KeyConfig keyConfig) {
        Objects.requireNonNull(redissonClient, "redissonClient can't be null");
        Objects.requireNonNull(keyConfig, "keyConfig can't be null");
        this.redissonClient = redissonClient;
        this.keyConfig = keyConfig;
    }
}
