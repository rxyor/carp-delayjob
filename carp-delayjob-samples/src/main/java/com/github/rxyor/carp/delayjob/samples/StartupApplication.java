package com.github.rxyor.carp.delayjob.samples;

import com.github.rxyor.carp.delayjob.boot.starter.EnableDelayJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
@EnableDelayJob
@SpringBootApplication
public class StartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartupApplication.class);
    }
}
