package com.github.rxyor.carp.delayjob.core.model;

/**
 *<p>
 *  执行结果
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public enum Result {

    /**
     * 执行结果
     */
    SUCCESS,
    FAIL,
    LATER;

    @Override
    public String toString() {
        return this.name();
    }
}
