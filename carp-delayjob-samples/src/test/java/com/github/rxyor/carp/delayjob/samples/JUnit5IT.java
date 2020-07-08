package com.github.rxyor.carp.delayjob.samples;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/10/31 周四 15:32:00
 * @since 1.0.0
 */
@RunWith(JUnitPlatform.class)
public class JUnit5IT {

    protected static Logger log = LoggerFactory.getLogger(JUnit5IT.class);

    private long start;

    @BeforeEach
    public void before() {
        start = System.currentTimeMillis();
    }

    @AfterEach
    public void after() {
        long diff = System.currentTimeMillis()-start;
        String msg = "[" + Thread.currentThread().getName() + "]单测执行时间:%sms";
        if (diff > 1000) {
            log.warn(String.format(msg, diff));
        } else {
            log.info(String.format(msg, diff));
        }
    }

}
