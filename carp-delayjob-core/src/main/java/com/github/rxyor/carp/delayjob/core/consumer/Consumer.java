package com.github.rxyor.carp.delayjob.core.consumer;

import com.github.rxyor.carp.delayjob.core.handler.JobHandler;

/**
 *<p>
 * 消费者
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public interface Consumer extends JobHandler {

    /**
     * handler可以处理的topic
     *
     * @return topic
     */
    String topic();

    /**
     * 需要把自身注册到JobHandlerDelegate
     */
    void register();

}
