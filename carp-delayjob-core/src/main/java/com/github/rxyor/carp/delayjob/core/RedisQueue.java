package com.github.rxyor.carp.delayjob.core;

/**
 *<p>
 * Redis队列接口定义
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public interface RedisQueue<T> {

    /**
     *<p>
     *  添加
     *</p>
     *
     * @author liuyang
     * @since 2020-07-08 15:45:46 v1.0
     * @param key key
     * @param data data
     * @return boolean
     */
    boolean offer(String key, T data);

    /**
     *<p>
     * 取出并删除
     *</p>
     *
     * @author liuyang
     * @since 2020-07-08 15:48:17 v1.0
     * @param key key
     * @return [T data]
     */
    T poll(String key);

    /**
     *<p>
     * 删除
     *</p>
     *
     * @author liuyang
     * @since 2020-07-08 15:53:52 v1.0
     * @param key key
     * @param data data
     * @return boolean
     */
    boolean remove(String key, T data);
}
