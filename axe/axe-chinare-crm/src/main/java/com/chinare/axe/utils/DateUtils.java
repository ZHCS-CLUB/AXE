package com.chinare.axe.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.nutz.lang.Times;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class DateUtils extends Times {

    public static final String DATE_FORMAT_MONTH = "yyyy-MM";
    public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";

    /**
     * 日期加减
     * 
     * @param base
     *            开始日期
     * @param days
     *            增加天数 正数为之后日期负数为之前日期
     * @return 目标日期
     */
    public static Date addDays(Date base, int days) {
        return D(base.getTime() + days * 24 * 60 * 60 * 1000L);
    }

    /**
     * 从今日开始的日期加减
     * 
     * @param days
     *            增加天数 正数为之后日期负数为之前日期
     * @return 目标日期
     */
    public static Date addDays(int days) {
        return addDays(now(), days);
    }

    /**
     * 两个日期之间的天数差
     * 
     * @param start
     *            开始日期
     * @param end
     *            结束日期
     * @return 天数
     */
    public static double daysBetween(Date start, Date end) {
        return BigDecimal.valueOf((end.getTime() - start.getTime()) / 1000.0d / 24.0d / 60.0d / 60.0d)
                         .setScale(0, RoundingMode.HALF_UP)
                         .doubleValue();
    }

    /**
     * 获取当日凌晨
     * 
     * @param d
     *            日期
     * @return 当日凌晨 date 对象
     */
    public static Date getDayStart(Date d) {
        return D(format("yyyy-MM-dd 00:00:00", d));
    }

    public static Date getDayEnd(Date d) {
        return D(format("yyyy-MM-dd 23:59:59", d));
    }

    /**
     * 获取今日凌晨
     * 
     * @return 今日凌晨0:0:0的时间实例
     */
    public static Date getDayStart() {
        return D(format("yyyy-MM-dd 00:00:00", now()));
    }

    public static Date getDayEnd() {
        return D(format("yyyy-MM-dd 23:59:59", now()));
    }

    /**
     * 获取当前日期的周结束(周日或者周六)
     * 
     * @param startFromSunday
     *            从周日开始计算周 标识
     * @return startFromSunday 为true 则返回下一个周六 为false则返回下一个周日
     */
    public static Date getWeekEnd(boolean startFromSunday) {
        Date date = addDays(getWeekStart(startFromSunday), 6);
        return getDayEnd(date);
    }

    public static Date getWeekEnd(boolean startFromSunday, Date date) {
        Date newDate = addDays(getWeekStart(startFromSunday, date), 6);
        return getDayEnd(newDate);
    }

    /**
     * 获取当前日期的周开始(周一或者周日)
     * 
     * @param startFromSunday
     *            从周日开始计算周 标识
     * @return startFromSunday 为true 则返回上一个周日 为false则返回上一个周一
     */
    public static Date getWeekStart(boolean startFromSunday) {
        return addDays(getDayStart(),
                       -1
                                      * (Times.C(now()).get(Calendar.DAY_OF_WEEK)
                                         - 1
                                         - (startFromSunday ? 0 : 1)));
    }

    public static Date getWeekStart(boolean startFromSunday, Date date) {
        return addDays(getDayStart(date),
                       -1
                                          * (Times.C(now()).get(Calendar.DAY_OF_WEEK)
                                             - 1
                                             - (startFromSunday ? 0 : 1)));
    }

    /**
     * 增加秒
     * 
     * @param base
     *            基础时间
     * @param seconds
     *            秒数
     * @return 日期对象
     */
    public static Date addSeconds(Date base, long seconds) {
        return D(base.getTime() + seconds * 1000);
    }

    /**
     * 增加秒
     * 
     * @param seconds
     *            秒数
     * @return 日期对象
     */
    public static Date addSeconds(long seconds) {
        return addSeconds(now(), seconds);
    }

    /**
     * 获取元旦日期
     * 
     * @param d
     *            日期
     * @return 元旦日期
     */
    public static Date getYearStart(Date d) {
        String year = format("yyyy", d);
        year += "-01-01";
        return D(year);
    }

    /**
     * 获取元旦日期
     * 
     * @return 元旦日期
     */
    public static Date getYearStart() {
        return getYearStart(now());
    }

    /**
     * 获取年末日期
     * 
     * @param d
     *            日期
     * @return 年末日期
     */
    public static Date getYearEnd(Date d) {
        String year = format("yyyy", d);
        year += "-12-31";
        return getDayEnd(D(year));
    }

    /**
     * 获取年末日期
     * 
     * @return 年末日期
     */
    public static Date getYearEnd() {
        return getYearEnd(now());
    }

    /**
     * 获取月初日期
     * 
     * @param d
     *            日期
     * @return 月初日期
     */
    public static Date getMonthStart(Date d) {
        String month = format(DATE_FORMAT_MONTH, d) + "-01";
        return D(month);
    }

    /**
     * 获取月初日期
     * 
     * @return 月初日期
     */
    public static Date getMonthStart() {
        return getMonthStart(now());
    }

    /**
     * 获取月末日期
     * 
     * @param d
     *            日期
     * @return 月末日期
     */
    public static Date getMonthEnd(Date d) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getDayEnd(ca.getTime());
    }

    /**
     * 获取月末日期
     * 
     * @return 月末日期
     */
    public static Date getMonthEnd() {
        return getMonthEnd(now());
    }

    // 获取时间相关序列
    public static String getSequence() {
        String sequence = format("yyyyMMDD", now());
        sequence += ms() % 10000;
        return sequence;
    }

    public static String getYearStartMonth() {
        return format(DATE_FORMAT_MONTH, getYearStart());
    }

    public static String getYearStartMonth(Date date) {
        return format(DATE_FORMAT_MONTH, getYearStart(date));
    }

    public static String getYearEndMonth() {
        return format(DATE_FORMAT_MONTH, getYearEnd());
    }

    public static String getYearEndMonth(Date date) {
        return format(DATE_FORMAT_MONTH, getYearEnd(date));
    }

    public static String getMonthStartDate() {
        return format(DATE_FORMAT_DAY, getMonthStart());
    }

    public static String getMonthStartDate(Date date) {
        return format(DATE_FORMAT_DAY, getMonthStart(date));
    }

    public static String getMonthEndDate() {
        return format(DATE_FORMAT_DAY, getMonthEnd());
    }

    public static String getMonthEndDate(Date date) {
        return format(DATE_FORMAT_DAY, getMonthEnd(date));
    }

    public static int getDayOfMonth() {
        return Calendar.getInstance(Locale.CHINA).getActualMaximum(Calendar.DATE);
    }
}
