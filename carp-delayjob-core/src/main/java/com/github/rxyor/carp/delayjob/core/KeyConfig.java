package com.github.rxyor.carp.delayjob.core;

import org.apache.commons.lang3.StringUtils;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public class KeyConfig {

    /**
     * 默认应用名
     */
    private static final String DEFAULT_APP_NAME = "carp_delay_job";
    /**
     * 默认等待队列
     */
    private static final String DEFAULT_WAIT_QUEUE_KEY = "wait_queue";
    /**
     * 默认就绪 topic key 前缀
     */
    private static final String DEFAULT_READY_TOPIC_KEY_PREFIX = "ready_topic";
    /**
     * 默认任务详情池
     */
    private static final String DEFAULT_JOB_DETAILS_KEY = "job_details";
    /**
     * 默认失败任务
     */
    private static final String DEFAULT_FAIL_JOBS_KEY = "fail_jobs";
    /**
     * 分割符
     */
    private static final String SPLITTER = ":";
    /**
     * 默认redis key salt 防止key冲突
     */
    private static final String SALT = "JXU2MEEw";
    /**
     * 应用名
     */
    private final String appName;

    public KeyConfig(String appName) {
        if (StringUtils.isBlank(appName)) {
            this.appName = DEFAULT_APP_NAME;
        } else {
            this.appName = appName;
        }
    }

    public String waitQueueKey() {
        return prefix() + DEFAULT_WAIT_QUEUE_KEY;
    }

    public String readyQueueKey(String topic) {
        if (StringUtils.isBlank(topic)) {
            throw new IllegalArgumentException("topic can't be blank");
        }
        return prefix() + DEFAULT_READY_TOPIC_KEY_PREFIX + SPLITTER + topic;
    }

    public String jobStoreMapKey() {
        return prefix() + DEFAULT_JOB_DETAILS_KEY;
    }

    public String failJobsKey(String jobId) {
        if (StringUtils.isBlank(jobId)) {
            throw new IllegalArgumentException("jobId can't be blank");
        }
        return prefix() + DEFAULT_FAIL_JOBS_KEY + SPLITTER + jobId;
    }

    private String prefix() {
        return appName() + SPLITTER + SALT + SPLITTER;
    }

    private String appName() {
        return StringUtils.isBlank(this.appName) ? DEFAULT_APP_NAME : this.appName;
    }


}
