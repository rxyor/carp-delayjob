package com.github.rxyor.carp.delayjob.core;

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
     * handler id
     *
     * @return handler id
     */
    String id();

    /**
     * handler可以处理的topic
     *
     * @return topic
     */
    String topic();

}
