package com.github.rxyor.carp.delayjob.core;

import java.util.UUID;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public class IdUtil {

    private IdUtil() {
    }

    /**
     *<p>
     * 随机ID
     *</p>
     *
     * @author liuyang
     * @since 2020-07-08 16:38:49 v1.0
     * @return [String]
     */
    public static String randomId() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

}
