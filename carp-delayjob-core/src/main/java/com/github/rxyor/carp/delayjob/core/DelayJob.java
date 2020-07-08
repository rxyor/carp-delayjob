package com.github.rxyor.carp.delayjob.core;

import java.io.Serializable;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

/**
 *<p>
 * 延时任务实体
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
@Builder
public class DelayJob<T extends Serializable> implements Job {

    private static final long serialVersionUID = -644484165397777526L;
    /**
     * job id
     */
    private String id;
    /**
     * 消息类型
     */
    private String topic;
    /**
     * 任务执行时间(时间戳:精确到秒)
     */
    private Long execTime = 0L;
    /**
     * 重试次数
     */
    private Integer retryTimes = 0;
    /**
     * 消费失败，重新消费间隔(单位秒)
     * 默认0L, 消费失败不重新消费
     */
    private Long retryDelay = 0L;

    /**
     * 消息体
     */
    private T body;

    /**
     * 非严格的builder模式，Redisson反序列化需要一个无参构造器
     */
    public DelayJob() {
    }

    public DelayJob(String id, String topic, Long execTime, Integer retryTimes, Long retryDelay, T body) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id can't be blank");
        }
        if (StringUtils.isBlank(topic)) {
            throw new IllegalArgumentException("topic can't be blank");
        }
        if (execTime < 0) {
            throw new IllegalArgumentException("execTime must >= 0");
        }
        if (retryTimes < 0) {
            throw new IllegalArgumentException("retryTimes must >= 0");
        }
        if (retryDelay < 0) {
            throw new IllegalArgumentException("retryDelay must >= 0");
        }
        if (body == null) {
            throw new IllegalArgumentException("body can't be null");
        }

        this.id = id;
        this.topic = topic;
        this.execTime = execTime;
        this.retryTimes = retryTimes;
        this.retryDelay = retryDelay;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getExecTime() {
        return execTime;
    }

    public void setExecTime(Long execTime) {
        this.execTime = execTime;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Long getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(Long retryDelay) {
        this.retryDelay = retryDelay;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    /**
     * 任务ID
     *
     * @return String
     */
    @Override
    public String id() {
        return getId();
    }

    /**
     * 执行时间
     *
     * @return Long
     */
    @Override
    public Long execTime() {
        return getExecTime();
    }

    public static class Builder<T extends Serializable> {

        private String id;
        private String topic;
        private Long execTime;
        private Integer retryTimes;
        private Long retryDelay;
        private T body;

        Builder() {
        }

        public DelayJob.Builder<T> id(String id) {
            this.id = id;
            return this;
        }

        public DelayJob.Builder<T> topic(String topic) {
            this.topic = topic;
            return this;
        }

        public DelayJob.Builder<T> execTime(Long execTime) {
            this.execTime = execTime;
            return this;
        }

        public DelayJob.Builder<T> retryTimes(Integer retryTimes) {
            this.retryTimes = retryTimes;
            return this;
        }

        public DelayJob.Builder<T> retryDelay(Long retryDelay) {
            this.retryDelay = retryDelay;
            return this;
        }

        public DelayJob.Builder<T> body(T body) {
            this.body = body;
            return this;
        }

        public DelayJob<T> build() {
            return new DelayJob<>(this.id, this.topic, this.execTime, this.retryTimes, this.retryDelay, this.body);
        }

        public String toString() {
            return "DelayJob.Builder(id=" + this.id + ", topic=" + this.topic + ", execTime=" + this.execTime
                + ", retryTimes=" + this.retryTimes + ", retryDelay=" + this.retryDelay + ", body=" + this.body + ")";
        }
    }
}
