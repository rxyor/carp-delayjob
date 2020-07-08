package com.github.rxyor.carp.delayjob.samples.config;

import com.alibaba.fastjson.JSON;
import com.github.rxyor.carp.delayjob.core.Consumer;
import com.github.rxyor.carp.delayjob.core.DelayJob;
import com.github.rxyor.carp.delayjob.core.FailJobSet;
import com.github.rxyor.carp.delayjob.core.JobHandlerDelegate;
import com.github.rxyor.carp.delayjob.core.JobStoreMap;
import com.github.rxyor.carp.delayjob.core.KeyConfig;
import com.github.rxyor.carp.delayjob.core.Producer;
import com.github.rxyor.carp.delayjob.core.ProducerImpl;
import com.github.rxyor.carp.delayjob.core.ReadyQueue;
import com.github.rxyor.carp.delayjob.core.Result;
import com.github.rxyor.carp.delayjob.core.Scanner;
import com.github.rxyor.carp.delayjob.core.WaitZSet;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
@Configuration
public class DelayJobConfig {

    @Bean
    public KeyConfig keyConfig() {
        return new KeyConfig("simples");
    }

    @Bean
    public JobHandlerDelegate jobHandlerDelegate() {
        return new JobHandlerDelegate();
    }

    @Bean
    public JobStoreMap jobStoreMap(RedissonClient redissonClient, KeyConfig keyConfig) {
        return new JobStoreMap(redissonClient, keyConfig);
    }

    @Bean
    public ReadyQueue readyQueue(RedissonClient redissonClient, KeyConfig keyConfig) {
        return new ReadyQueue(redissonClient, keyConfig);
    }

    @Bean
    public WaitZSet waitZSet(RedissonClient redissonClient, KeyConfig keyConfig) {
        return new WaitZSet(redissonClient, keyConfig);
    }

    @Bean
    public FailJobSet failJobSet(RedissonClient redissonClient, KeyConfig keyConfig) {
        return new FailJobSet(redissonClient, keyConfig);
    }

    @Bean
    public Producer producer(WaitZSet waitZSet, JobStoreMap jobStoreMap) {
        return new ProducerImpl(waitZSet, jobStoreMap);
    }

    @Bean(initMethod = "startup", destroyMethod = "shutdown")
    public Scanner scanner(WaitZSet waitZSet, ReadyQueue readyQueue, JobStoreMap jobStoreMap, FailJobSet failJobSet,
        Producer producer, JobHandlerDelegate delegate) {
        return new Scanner(waitZSet, readyQueue, jobStoreMap, failJobSet, producer, delegate);
    }

    @Bean
    public Consumer consumer(JobHandlerDelegate jobHandlerDelegate) {
        Consumer consumer = new Consumer() {
            @Override
            public String topic() {
                return "TEST";
            }

            @Override
            public Result consume(DelayJob<?> delayJob) {
                System.out.println(JSON.toJSONString(delayJob));
                return Result.FAIL;
            }
        };
        jobHandlerDelegate.addConsumer(consumer);
        return consumer;
    }

}
