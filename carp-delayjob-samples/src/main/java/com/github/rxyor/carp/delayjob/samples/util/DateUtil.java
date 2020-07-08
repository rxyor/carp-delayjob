package com.github.rxyor.carp.delayjob.samples.util;

import com.google.common.base.Preconditions;
import java.util.Calendar;
import java.util.Date;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *<p>
 *  DateUtil
 *</p>
 *
 * @author liuyang
 * @date 2020-06-05 周五 10:07:14
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class DateUtil {

    private DateUtil() {
    }

    /**
     * 解析日期
     *
     * @author rxyor
     * @date 2020-05-09 周六 17:44:29
     * @param date date
     * @return [Date]
     */
    public static Date parse(String date) {
        return parse(date, Pattern.NORM_DATETIME_PATTERN);
    }

    /**
     *<p>
     * 当前时间
     *</p>
     *
     * @author liuyang
     * @date 2020-05-12 周二 11:07:42
     * @return [String]
     */
    public static String now() {
        return DateTime.now().toString(Pattern.NORM_DATETIME_PATTERN);
    }

    /**
     *<p>
     * 格式日期
     *</p>
     *
     * @author liuyang
     * @date 2020-05-12 周二 11:11:33
     * @param date date
     * @param pattern pattern
     * @return [String]
     */
    public static String format(Date date, String pattern) {
        Preconditions.checkArgument(date != null, "日期不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(pattern), "日期格式不能为空");
        try {
            return new DateTime(date).toString(pattern);
        } catch (Exception e) {
            String msg = String
                .format("日期格式异常, 日期:[%s], 格式:[%s],错误:[%s]", date, pattern, e.getMessage());
            throw new IllegalArgumentException(msg, e);
        }
    }

    /**
     * 解析日期
     *
     * @author rxyor
     * @date 2020-05-09 周六 17:44:29
     * @param date date
     * @param pattern pattern
     * @return [Date]
     */
    public static Date parse(String date, String pattern) {
        Preconditions.checkArgument(StringUtils.isNotBlank(date), "日期不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(pattern), "日期格式不能为空");

        try {
            return DateTimeFormat.forPattern(pattern).parseDateTime(date).toDate();
        } catch (Exception e) {
            String msg = String.format("日期解析异常, 日期:[%s], 格式:[%s],错误:[%s]",
                date, pattern, e.getMessage());
            throw new IllegalArgumentException(msg, e);
        }
    }


    public static Date plusYears(Date date, int years) {
        Preconditions.checkArgument(date != null, "日期不能为空");

        return new DateTime(date).plusYears(years).toDate();
    }

    public static Date plusMonths(Date date, int months) {
        Preconditions.checkArgument(date != null, "日期不能为空");

        return new DateTime(date).plusMonths(months).toDate();
    }

    public static Date plusDays(Date date, int days) {
        Preconditions.checkArgument(date != null, "日期不能为空");

        return new DateTime(date).plusDays(days).toDate();
    }

    public static Date plusHours(Date date, int hours) {
        Preconditions.checkArgument(date != null, "日期不能为空");

        return new DateTime(date).plusHours(hours).toDate();
    }

    public static Date plusMinutes(Date date, int minutes) {
        Preconditions.checkArgument(date != null, "日期不能为空");

        return new DateTime(date).plusMinutes(minutes).toDate();
    }

    public static Date plusSeconds(Date date, int seconds) {
        Preconditions.checkArgument(date != null, "日期不能为空");

        return new DateTime(date).plusSeconds(seconds).toDate();
    }

    public static Date plusMills(Date date, int mills) {
        Preconditions.checkArgument(date != null, "日期不能为空");

        return new DateTime(date).plusMillis(mills).toDate();
    }

    /**
     *<p>
     *  一天的开始，常用于时间筛选
     *</p>
     *
     * @author liuyang
     * @date 2020-06-05 周五 09:47:47
     * @param date date
     * @return [Date]
     */
    public static Date dayStart(Date date) {
        Preconditions.checkArgument(date != null, "日期不能为空");

        DateTime dt = new DateTime(date);
        return new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(),
            0, 0, 0).toDate();
    }

    /**
     *<p>
     *  一天的末尾，常用于时间筛选
     *</p>
     *
     * @author liuyang
     * @date 2020-06-05 周五 09:47:47
     * @param date date
     * @return [Date]
     */
    public static Date dayEnd(Date date) {
        Preconditions.checkArgument(date != null, "日期不能为空");

        DateTime dt = new DateTime(date);
        return new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(),
            23, 59, 59).toDate();
    }

    public interface Pattern {

        /** 标准日期格式：yyyy-MM-dd */
        String NORM_DATE_PATTERN = "yyyy-MM-dd";
        String NORM_DATE_PATTERN2 = "yyyy/MM/dd";

        /** 标准时间格式：HH:mm:ss */
        String NORM_TIME_PATTERN = "HH:mm:ss";

        /** 标准日期时间格式，精确到分：yyyy-MM-dd HH:mm */
        String NORM_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
        String NORM_DATETIME_MINUTE_PATTERN2 = "yyyy/MM/dd HH:mm";

        /** 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss */
        String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
        String NORM_DATETIME_PATTERN2 = "yyyy/MM/dd HH:mm:ss";

        /** 标准日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss.SSS */
        String NORM_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
        String NORM_DATETIME_MS_PATTERN2 = "yyyy/MM/dd HH:mm:ss.SSS";

        /** 标准日期格式：yyyy年MM月dd日 */
        String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";

        //-------------------------------------------------------------------------------------------------------------------------------- Pure
        /** 标准日期格式：yyyyMMdd */
        String PURE_DATE_PATTERN = "yyyyMMdd";

        /** 标准日期格式：HHmmss */
        String PURE_TIME_PATTERN = "HHmmss";

        /** 标准日期格式：yyyyMMddHHmmss */
        String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";

        /** 标准日期格式：yyyyMMddHHmmssSSS */
        String PURE_DATETIME_MS_PATTERN = "yyyyMMddHHmmssSSS";

        //-------------------------------------------------------------------------------------------------------------------------------- Others
        /** HTTP头中日期时间格式：EEE, dd MMM yyyy HH:mm:ss z */
        String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

        /** JDK中日期时间格式：EEE MMM dd HH:mm:ss zzz yyyy */
        String JDK_DATETIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

        /** UTC时间：yyyy-MM-dd'T'HH:mm:ss'Z' */
        String UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

        /** UTC时间：yyyy-MM-dd'T'HH:mm:ssZ */
        String UTC_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

        /** UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS'Z' */
        String UTC_MS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        /** UTC时间：yyyy-MM-dd'T'HH:mm:ssZ */
        String UTC_MS_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    }
}
