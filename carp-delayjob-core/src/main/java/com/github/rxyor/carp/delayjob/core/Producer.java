package com.github.rxyor.carp.delayjob.core;

import java.io.Serializable;

/**
 *<p>
 *  生产者
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public interface Producer {

    /**
     * 添加延时任务
     *
     * @param topic 任务类型
     * @param delaySeconds 延时时间(单位秒)
     * @param body 消息体
     * @param <T> 消息类型
     */
    <T extends Serializable> void offer(String topic, Long delaySeconds, T body);

    /**
     * 添加延时任务
     *
     * @param topic 任务类型
     * @param delaySeconds 延时时间(单位秒)
     * @param retryTimes 重试次数
     * @param retryDelay 重试间隔
     * @param body 消息体
     * @param <T> 消息类型
     */
    <T extends Serializable> void offer(String topic, Long delaySeconds, Integer retryTimes, Long retryDelay, T body);

    /**
     * 添加延时任务
     *
     * @param delayJob 任务
     * @param <T> 消息类型
     */
    <T extends Serializable> void offer(DelayJob<T> delayJob);

}
