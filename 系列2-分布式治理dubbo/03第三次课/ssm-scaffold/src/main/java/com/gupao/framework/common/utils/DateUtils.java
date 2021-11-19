package com.gupao.framework.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qingyin on 2016/8/23.
 */
public class DateUtils {

    private static final String YYYYMMDD = "yyyyMMdd";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期对象转换为yyyymmdd的字符串形式
     *
     * @param date
     *            日期对象
     * @return
     */
    public static String fortmat2yyyyMMdd(Date date) {
        return new SimpleDateFormat(YYYYMMDD).format(date);
    }

    /**
     * 日期对象转换为yyyy_mm_dd的字符串形式
     *
     * @param date
     *            日期对象
     * @return
     */
    public static String fortmat2yyyy_MM_dd(Date date) {
        return new SimpleDateFormat(YYYY_MM_DD).format(date);
    }

    /**
     * 日期对象转换为yyyymmddhhmmss的字符串形式
     *
     * @param date
     *            日期对象
     * @return
     */
    public static String fortmat2yyyyMMddHHmmss(Date date) {
        return new SimpleDateFormat(YYYYMMDDHHMMSS).format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss形式的字符串转换为日期对象
     *
     * @param date
     *            日期字符串
     * @return
     */
    public static Date parse2yyyyMMddHHmmss(String date) {
        try {
            return new SimpleDateFormat(YYYYMMDDHHMMSS).parse(date);
        } catch (ParseException e) {
            ;
        }
        return null;
    }
}
