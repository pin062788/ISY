package top.zbeboy.isy.service.util;


import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by lenovo on 2016-09-15.
 * 时间转换类
 */
public class DateTimeUtils {

    /**
     * timestamp
     *
     * @param timestamp java.sql.timestamp
     * @return java.util.date
     */
    public static java.util.Date timestampToDate(java.sql.Timestamp timestamp) {
        return new java.util.Date(timestamp.getTime());
    }

    /**
     * timestamp to string
     *
     * @param timestamp sql
     * @param format    格式
     * @return string
     */
    public static String timestampToString(java.sql.Timestamp timestamp, String format) {
        return timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * util date to sql date
     *
     * @param date util date
     * @return sql date
     */
    public static java.sql.Date utilDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 格式化date
     *
     * @param date   日期
     * @param format 格式
     * @return 格式化后的时间
     */
    public static String formatDate(java.util.Date date, String format) {
        return date.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 格式化date
     *
     * @param date   日期
     * @param format 格式
     * @return 格式化后的时间
     */
    public static String formatDate(java.sql.Date date, String format) {
        return date.toLocalDate().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 格式化date
     *
     * @param date 日期
     * @return 格式化后的时间
     */
    public static String formatDate(java.util.Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 格式化Timestamp
     *
     * @param timestamp 日期
     * @return 格式化后的时间
     */
    public static String formatDate(java.sql.Timestamp timestamp) {
        return timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 格式化成sql date
     *
     * @param date 日期
     * @return sql date
     * @throws ParseException
     */
    public static java.sql.Date formatDate(String date) throws ParseException {
        return new java.sql.Date(java.sql.Date.from(LocalDate.parse(StringUtils.trim(date), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
    }

    /**
     * 格式化成sql date
     *
     * @param date   date 日期
     * @param format 格式
     * @return sql date
     * @throws ParseException
     */
    public static java.sql.Date formatDate(String date, String format) throws ParseException {
        return new java.sql.Date(java.sql.Date.from(LocalDate.parse(StringUtils.trim(date), DateTimeFormatter.ofPattern(format)).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
    }

    /**
     * 格式化成util date
     *
     * @param date   date 日期
     * @param format 格式
     * @return util date
     * @throws ParseException
     */
    public static java.sql.Date formatDateTime(String date, String format) throws ParseException {
        return new java.sql.Date(java.sql.Date.from(LocalDateTime.parse(StringUtils.trim(date), DateTimeFormatter.ofPattern(format)).atZone(ZoneId.systemDefault()).toInstant()).getTime());
    }

    /**
     * 格式化成sql date
     *
     * @param date   date 日期
     * @param format 格式
     * @return sql date
     * @throws ParseException
     */
    public static java.sql.Timestamp formatDateToTimestamp(String date, String format) throws ParseException {
        return new java.sql.Timestamp(java.sql.Timestamp.from(LocalDateTime.parse(StringUtils.trim(date), DateTimeFormatter.ofPattern(format)).atZone(ZoneId.systemDefault()).toInstant()).getTime());
    }

    /**
     * 当前时间是否在时间范围
     *
     * @param after  之前时间
     * @param before 之后时间
     * @return true or false
     */
    public static boolean timestampRangeDecide(java.sql.Timestamp after, java.sql.Timestamp before) {
        java.sql.Timestamp now = new Timestamp(Clock.systemDefaultZone().millis());
        return now.after(after) && now.before(before);
    }

    /**
     * 当前时间大于某一时间
     *
     * @param after 某一时间
     * @return true or false
     */
    public static boolean timestampAfterDecide(java.sql.Timestamp after) {
        java.sql.Timestamp now = new Timestamp(Clock.systemDefaultZone().millis());
        return now.after(after);
    }

    /**
     * 当前时间小于某一时间
     *
     * @param before 某一时间
     * @return true or false
     */
    public static boolean timestampBeforeDecide(java.sql.Timestamp before) {
        java.sql.Timestamp now = new Timestamp(Clock.systemDefaultZone().millis());
        return now.before(before);
    }

    /**
     * 得到当前时间
     *
     * @return 当前时间
     */
    public static Timestamp getNow() {
        return new Timestamp(Clock.systemDefaultZone().millis());
    }

    /**
     * 拆分时间
     *
     * @param splitSymbol 分隔符
     * @param dateTime    需要拆分的时间
     * @return 拆分的数组时间
     */
    public static String[] splitDateTime(String splitSymbol, String dateTime) throws ParseException {
        if (StringUtils.isNotBlank(dateTime)) {
            String[] arr = dateTime.split(splitSymbol);
            if (!ObjectUtils.isEmpty(arr) && arr.length >= 2) {
                return arr;
            } else {
                throw new ParseException("时间长度不够", -1);
            }
        } else {
            throw new ParseException("时间为空", -1);
        }
    }
}
