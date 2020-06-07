package com.github.daqun.jira.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

/**
 * @Classname DateHelper
 * @Description
 * @Date 2019/3/6 14:34
 * @Created by chenq
 */
public class DateHelper {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_MONTH_FORMAT = "yyyy-MM";
    public static final String DATE_TIME_S_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_M_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String TIME_MINUTE_FORMAT = "HH:mm";
    public static final String TIME_SECOND_FORMAT = "HH:mm:ss";

    public static String toString(Date date, String pattern){
        return DateFormatUtils.format(date, pattern);
    }

    public static String toString(Date date){
        return toString(date, DATE_TIME_S_FORMAT);
    }

    public static int differDays(Date from, Date to) {
        Days days = Days.daysBetween(parseLocalDate(from), parseLocalDate(to));
        return days.getDays();
    }

    public static LocalDate parseLocalDate(Date date) {
        return new LocalDate(date);
    }
    public static LocalDate parseLocalDate(String date) {
        return new LocalDate(date);
    }

    /**
     * 将字符串转为LocalDate
     * @param date 日期字符串.'2018-12-12'
     * @return LocalDate
     */
    public static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormat.forPattern(DATE_FORMAT));
    }
    /**
     * 将字符串转为DateTime
     * @param date 日期时间字符串 '2018-12-12 12:12:12'
     * @return DateTime
     */
    public static DateTime toDateTime(String date) {
        return DateTime.parse(date, DateTimeFormat.forPattern(DATE_TIME_S_FORMAT));
    }


}
