package com.github.rxyor.carp.delayjob.samples.config;

import com.alibaba.fastjson.JSON;
import com.github.rxyor.carp.delayjob.core.consumer.AbstractConsumer;
import com.github.rxyor.carp.delayjob.core.consumer.Consumer;
import com.github.rxyor.carp.delayjob.core.model.DelayJob;
import com.github.rxyor.carp.delayjob.core.model.Result;
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
    public Consumer consumer() {
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
