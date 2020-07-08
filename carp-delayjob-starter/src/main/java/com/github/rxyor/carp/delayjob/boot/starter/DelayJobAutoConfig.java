package com.github.rxyor.carp.delayjob.boot.starter;

import com.github.rxyor.carp.delayjob.core.handler.JobHandlerDelegate;
import com.github.rxyor.carp.delayjob.core.handler.ScanProcessor;
import com.github.rxyor.carp.delayjob.core.model.KeyConfig;
import com.github.rxyor.carp.delayjob.core.producer.Producer;
import com.github.rxyor.carp.delayjob.core.producer.ProducerImpl;
import com.github.rxyor.carp.delayjob.core.repository.FailJobSetRepository;
import com.github.rxyor.carp.delayjob.core.repository.JobDetailMapRepository;
import com.github.rxyor.carp.delayjob.core.repository.ReadyJobQueueRepository;
import com.github.rxyor.carp.delayjob.core.repository.WaitJobZSetRepository;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *<p>
 * 自动化配置
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
@Configuration
@ComponentScan({"com.github.rxyor.carp.delayjob.boot.starter"})
public class DelayJobAutoConfig {

    @Value("${spring.application.name}:DELAY_JOB")
    private String appName;

    @Bean
    public KeyConfig keyConfig() {
        return new KeyConfig(appName);
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

}
