package com.github.rxyor.carp.delayjob.boot.starter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

/**
 *<p>
 *  开启延时注解标识
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ImportAutoConfiguration({DelayJobAutoConfig.class})
public @interface EnableDelayJob {

}
