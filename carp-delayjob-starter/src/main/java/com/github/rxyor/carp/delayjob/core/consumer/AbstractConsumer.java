package com.github.rxyor.carp.delayjob.core.consumer;

import com.github.rxyor.carp.delayjob.core.handler.JobHandlerDelegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
@Slf4j
public abstract class AbstractConsumer implements Consumer, InitializingBean {

    /**
     * 需要把自身注册到JobHandlerDelegate
     */
    @Override
    public void register() {
        boolean success = JobHandlerDelegate.getSingleStance().registerConsumer(this);
        if (!success) {
            log.warn("Consumer:[{}] register fail", this.getClass().getName());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        register();
    }
}
