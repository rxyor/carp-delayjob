package com.github.rxyor.carp.delayjob.samples.controller;

import com.github.rxyor.carp.delayjob.core.producer.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @since 2020-06-20 v1.0
 */
@AllArgsConstructor
@Api(value = "DelayJobController")
@RestController
@RequestMapping("/job")
public class DelayJobController {

    private final Producer producer;

    @ApiOperation("add job")
    @PostMapping("/add")
    public Object add(String data) {
        producer.offer("TEST", 10L,3,3L, data);
        return "ok";
    }

}
