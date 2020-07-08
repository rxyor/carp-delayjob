package com.github.rxyor.carp.delayjob.core;

import java.io.Serializable;

/**
 *<p>
 * JOB定义
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public interface Job extends Serializable {

    /**
     * 任务ID
     *
     * @return String
     */
    String id();

    /**
     * 执行时间
     *
     * @return Long
     */
    Long execTime();

}
