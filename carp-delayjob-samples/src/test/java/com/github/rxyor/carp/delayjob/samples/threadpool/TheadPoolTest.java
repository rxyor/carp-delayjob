package com.github.rxyor.carp.delayjob.samples.threadpool;

import com.github.rxyor.carp.delayjob.samples.JUnit5IT;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public class TheadPoolTest extends JUnit5IT {

    @Test
    public void test1() throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(() -> System.out.println("do"), 0, 1, TimeUnit.SECONDS);
        Thread.sleep(5000);
    }

}
