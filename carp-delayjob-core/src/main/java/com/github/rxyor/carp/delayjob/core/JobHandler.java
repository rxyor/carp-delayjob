package com.github.rxyor.carp.delayjob.core;

/**
 *<p>
 * 消费者
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public interface JobHandler {

    /**
     * consume DelayJob
     *
     * @param delayJob delayJob
     * @return Result
     */
    Result consume(DelayJob<?> delayJob);

}
