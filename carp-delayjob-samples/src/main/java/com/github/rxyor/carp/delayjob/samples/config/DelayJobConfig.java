package com.github.rxyor.carp.delayjob.samples.config;

import com.alibaba.fastjson.JSON;
import com.github.rxyor.carp.delayjob.core.consumer.AbstractConsumer;
import com.github.rxyor.carp.delayjob.core.consumer.Consumer;
import com.github.rxyor.carp.delayjob.core.handler.JobHandlerDelegate;
import com.github.rxyor.carp.delayjob.core.handler.ScanProcessor;
import com.github.rxyor.carp.delayjob.core.model.DelayJob;
import com.github.rxyor.carp.delayjob.core.model.KeyConfig;
import com.github.rxyor.carp.delayjob.core.model.Result;
import com.github.rxyor.carp.delayjob.core.producer.Producer;
import com.github.rxyor.carp.delayjob.core.producer.ProducerImpl;
import com.github.rxyor.carp.delayjob.core.repository.FailJobSetRepository;
import com.github.rxyor.carp.delayjob.core.repository.JobDetailMapRepository;
import com.github.rxyor.carp.delayjob.core.repository.ReadyJobQueueRepository;
import com.github.rxyor.carp.delayjob.core.repository.WaitJobZSetRepository;
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
        return JobHandlerDelegate.getSingleStance();
    }

    @Bean
    public JobDetailMapRepository jobStoreMap(RedissonClient redissonClient, KeyConfig keyConfig) {
        return new JobDetailMapRepository(redissonClient, keyConfig);
    }

    @Bean
    public ReadyJobQueueRepository readyQueue(RedissonClient redissonClient, KeyConfig keyConfig) {
        return new ReadyJobQueueRepository(redissonClient, keyConfig);
    }

    @Bean
    public WaitJobZSetRepository waitZSet(RedissonClient redissonClient, KeyConfig keyConfig) {
        return new WaitJobZSetRepository(redissonClient, keyConfig);
    }

    @Bean
    public FailJobSetRepository failJobSet(RedissonClient redissonClient, KeyConfig keyConfig) {
        return new FailJobSetRepository(redissonClient, keyConfig);
    }

    @Bean
    public Producer producer(WaitJobZSetRepository waitJobZSetRepository,
        JobDetailMapRepository jobDetailMapRepository) {
        return new ProducerImpl(waitJobZSetRepository, jobDetailMapRepository);
    }

    @Bean(initMethod = "startup", destroyMethod = "shutdown")
    public ScanProcessor scanner(WaitJobZSetRepository waitJobZSetRepository,
        ReadyJobQueueRepository readyJobQueueRepository, JobDetailMapRepository jobDetailMapRepository,
        FailJobSetRepository failJobSetRepository,
        Producer producer, JobHandlerDelegate delegate) {
        return new ScanProcessor(waitJobZSetRepository, readyJobQueueRepository, jobDetailMapRepository,
            failJobSetRepository, producer, delegate);
    }

    @Bean
    public Consumer consumer(JobHandlerDelegate jobHandlerDelegate) {
        Consumer consumer = new AbstractConsumer() {
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
        return consumer;
    }

}
