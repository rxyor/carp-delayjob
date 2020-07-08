package com.github.rxyor.carp.delayjob.samples.config;

import com.alibaba.fastjson.JSON;
import com.github.rxyor.carp.delayjob.core.consumer.AbstractConsumer;
import com.github.rxyor.carp.delayjob.core.model.DelayJob;
import com.github.rxyor.carp.delayjob.core.model.Result;
import com.github.rxyor.carp.delayjob.samples.util.DateUtil;
import com.github.rxyor.carp.delayjob.samples.util.DateUtil.Pattern;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @since 2020-07-09 v1.0
 */
@Slf4j
@Component
public class TestConsumer extends AbstractConsumer {

    /**
     * handler可以处理的topic
     *
     * @return topic
     */
    @Override
    public String topic() {
        return "TEST";
    }

    /**
     * consume DelayJob
     *
     * @param delayJob delayJob
     * @return Result
     */
    @Override
    public Result consume(DelayJob<?> delayJob) {
        log.info("now:[{}],job config execTime:[{}], job:[{}]",
            DateUtil.format(new Date(), Pattern.NORM_DATETIME_PATTERN),
            DateUtil.format(new Date(delayJob.getExecTime()), Pattern.NORM_DATETIME_PATTERN),
            JSON.toJSONString(delayJob));
        return Result.SUCCESS;
    }
}
