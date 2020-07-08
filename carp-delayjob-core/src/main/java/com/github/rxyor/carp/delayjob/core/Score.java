package com.github.rxyor.carp.delayjob.core;

/**
 *<p>
 *  分数，Redis ZSet 存储项
 *</p>
 *
 * @author liuyang
 * @since 2020-07-08 v1.0
 */
public class Score implements Job {

    private static final long serialVersionUID = -4806255498944191290L;
    /**
     * 唯一ID
     */
    private String id;

    /**
     * 分数
     */
    private Long score;

    public Score() {
    }

    public Score(String id, Long score) {
        this.id = id;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
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
        return getScore();
    }
}
