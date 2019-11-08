package com.bigdata.uno.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class DateTimeUtil {

    /**
     * 默认的时间格式（注意：与ISO标准时间格式是不同的）
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认的日期格式
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 匹配日期的正则表达式
     */
    private static final String DATE_EL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-/\\s]?"
            + "((((0?[13578])|(1[02]))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|"
            + "(((0?[469])|(11))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-/\\s]?((0?[1-9])|([1-2][0-9])))))"
            + "|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-/\\s]?"
            + "((((0?[13578])|(1[02]))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|"
            + "(((0?[469])|(11))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|"
            + "(0?2[\\-/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
    /**
     * 匹配时间的正则表达式（注意：不会处理闰秒的情况）
     */
    private static final String DATETIME_EL = "^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|"
            + "(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) "
            + "(20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$";

    /**
     * 匹配是否默认的日期格式字符串
     *
     * @param dateStr 输入日期
     *
     * @return 如果输入日期是默认格式则返回 true
     */
    public static boolean isDateStringFormat(String dateStr) {
        return Pattern.compile(DATE_EL).matcher(dateStr).matches();
    }

    /**
     * 匹配是否默认的时间格式字符串，注意并不能正确处理闰秒等情况
     *
     * @param dateTimeStr 输入时间
     *
     * @return 如果输入时间是默认格式则返回 true
     */
    public static boolean isDateTimeStringFormat(String dateTimeStr) {
        return Pattern.compile(DATETIME_EL).matcher(dateTimeStr).matches();
    }

    // 日期计算

    /**
     * 获取 count 天后的日期。
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return count 天后的日期
     */
    public static Date getDayAfter(Date inputDate, int count) {
        return getDayAfter(new DateTime(inputDate), count).toDate();
    }

    /**
     * 获取 count 天后的日期。
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return count 天后的日期
     */
    public static DateTime getDayAfter(DateTime inputDate, int count) {
        return inputDate.plusDays(count);
    }

    /**
     * 获取 count 天后的日期。
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return count 天后的日期
     */
    public static String getDayAfter(String inputDate, int count) {
        return dateTime2Str(getDayAfter(str2DateTime(inputDate), count));
    }

    /**
     * 获取 count 天后的日期。
     *
     * @param inputDate 输入日期
     * @param count     天数
     * @param format    输入输出格式
     *
     * @return count 天后的日期
     */
    public static String getDayAfter(String inputDate, int count, String format) {
        return dateTime2Str(getDayAfter(str2DateTime(inputDate, format), count), format);
    }

    /**
     * 获取 count 天后的日期。
     *
     * @param inputDate    输入日期
     * @param count        天数
     * @param inputFormat  输入格式
     * @param outputFormat 输出格式
     *
     * @return count 天后的日期
     */
    public static String getDayAfter(String inputDate, int count, String inputFormat, String outputFormat) {
        return dateTime2Str(getDayAfter(str2DateTime(inputDate, inputFormat), count), outputFormat);
    }

    /**
     * 获取 count 天前的日期
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return count 天前的日期
     */
    public static Date getDayBefore(Date inputDate, int count) {
        return getDayBefore(new DateTime(inputDate), count).toDate();
    }

    /**
     * 获取 count 天前的日期
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return count 天前的日期
     */
    public static DateTime getDayBefore(DateTime inputDate, int count) {
        return inputDate.minusDays(count);
    }

    /**
     * 获取 count 天前的日期
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return count 天前的日期
     */
    public static String getDayBefore(String inputDate, int count) {
        return dateTime2Str(getDayBefore(str2DateTime(inputDate), count));
    }

    /**
     * 获取 count 天前的日期
     *
     * @param inputDate 输入日期
     * @param count     天数
     * @param format    输入输出格式
     *
     * @return count 天前的日期
     */
    public static String getDayBefore(String inputDate, int count, String format) {
        return dateTime2Str(getDayBefore(str2DateTime(inputDate, format), count), format);
    }

    /**
     * 获取 count 天前的日期
     *
     * @param inputDate    输入日期
     * @param count        天数
     * @param inputFormat  输入格式
     * @param outputFormat 输出格式
     *
     * @return count 天前的日期
     */
    public static String getDayBefore(String inputDate, int count, String inputFormat, String outputFormat) {
        return dateTime2Str(getDayBefore(str2DateTime(inputDate, inputFormat), count), outputFormat);
    }

    /**
     * 获取前一周同一天的日期
     *
     * @param inputDate 输入日期
     *
     * @return 上周同期日期
     */
    public static Date getWeekBefore(Date inputDate) {
        return getWeekBefore(new DateTime(inputDate)).toDate();
    }

    /**
     * 获取前一周同一天的日期
     *
     * @param inputDate 输入日期
     *
     * @return 上周同期日期
     */
    public static DateTime getWeekBefore(DateTime inputDate) {
        return inputDate.minusWeeks(1);
    }

    /**
     * 获取前一周同一天的日期
     *
     * @param inputDate 输入日期
     *
     * @return 上周同期日期
     */
    public static String getWeekBefore(String inputDate) {
        return date2Str(getWeekBefore(str2Date(inputDate)));
    }

    /**
     * 获取前一周同一天的日期
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return 上周同期日期
     */
    public static String getWeekBefore(String inputDate, String format) {
        return dateTime2Str(getWeekBefore(str2DateTime(inputDate, format)), format);
    }

    /**
     * 获取前一周同一天的日期
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return 上周同期日期
     */
    public static String getWeekBefore(String inputDate, String inputFormat, String outputFormat) {
        return dateTime2Str(getWeekBefore(str2DateTime(inputDate, inputFormat)), outputFormat);
    }

    /**
     * 获取数周前同一天的日期
     *
     * @param inputDate 输入日期
     * @param count     周数
     *
     * @return count周前同期日期
     */
    public static Date getWeeksBefore(Date inputDate, int count) {
        return getWeeksBefore(new DateTime(inputDate), count).toDate();
    }

    /**
     * 获取数周前同一天的日期
     *
     * @param inputDate 输入日期
     * @param count     周数
     *
     * @return count周前同期日期
     */
    public static DateTime getWeeksBefore(DateTime inputDate, int count) {
        return inputDate.minusWeeks(count);
    }

    /**
     * 获取数周前同一天的日期
     *
     * @param inputDate 输入日期
     * @param count     周数
     *
     * @return count周前同期日期
     */
    public static String getWeeksBefore(String inputDate, int count) {
        return dateTime2Str(getWeeksBefore(str2DateTime(inputDate), count));
    }

    /**
     * 获取数周前同一天的日期
     *
     * @param inputDate 输入日期
     * @param count     周数
     * @param format    输入输出日期格式
     *
     * @return count周前同期日期
     */
    public static String getWeeksBefore(String inputDate, int count, String format) {
        return dateTime2Str(getWeeksBefore(str2DateTime(inputDate, format), count), format);
    }

    /**
     * 获取数周前同一天的日期
     *
     * @param inputDate    输入日期
     * @param count        周数
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return count周前同期日期
     */
    public static String getWeeksBefore(String inputDate, int count, String inputFormat, String outputFormat) {
        return dateTime2Str(getWeeksBefore(str2DateTime(inputDate, inputFormat), count), outputFormat);
    }

    /**
     * 获取后一周同一天的日期
     *
     * @param inputDate 输入日期
     *
     * @return 下周同期日期
     */
    public static Date getWeekAfter(Date inputDate) {
        return getWeekAfter(new DateTime(inputDate)).toDate();
    }

    /**
     * 获取后一周同一天的日期
     *
     * @param inputDate 输入日期
     *
     * @return 下周同期日期
     */
    public static DateTime getWeekAfter(DateTime inputDate) {
        return inputDate.plusWeeks(1);
    }

    /**
     * 获取后一周同一天的日期
     *
     * @param inputDate 输入日期
     *
     * @return 下周同期日期
     */
    public static String getWeekAfter(String inputDate) {
        return dateTime2Str(getWeekAfter(str2DateTime(inputDate)));
    }

    /**
     * 获取后一周同一天的日期
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return 下周同期日期
     */
    public static String getWeekAfter(String inputDate, String format) {
        return dateTime2Str(getWeekAfter(str2DateTime(inputDate, format)), format);
    }

    /**
     * 获取后一周同一天的日期
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return 下周同期日期
     */
    public static String getWeekAfter(String inputDate, String inputFormat, String outputFormat) {
        return dateTime2Str(getWeekAfter(str2DateTime(inputDate, inputFormat)), outputFormat);
    }

    /**
     * 获取后几周同一天的日期
     *
     * @param inputDate 输入日期
     * @param count     周数
     *
     * @return count周后同期日期
     */
    public static Date getWeeksAfter(Date inputDate, int count) {
        return getWeeksAfter(new DateTime(inputDate), count).toDate();
    }

    /**
     * 获取后几周同一天的日期
     *
     * @param inputDate 输入日期
     * @param count     周数
     *
     * @return count周后同期日期
     */
    public static DateTime getWeeksAfter(DateTime inputDate, int count) {
        return inputDate.plusWeeks(count);
    }

    /**
     * 获取后几周同一天的日期
     *
     * @param inputDate 输入日期
     * @param count     周数
     *
     * @return count周后同期日期
     */
    public static String getWeeksAfter(String inputDate, int count) {
        return dateTime2Str(getWeeksAfter(str2DateTime(inputDate), count));
    }

    /**
     * 获取后几周同一天的日期
     *
     * @param inputDate 输入日期
     * @param count     周数
     * @param format    输入输出日期格式
     *
     * @return count周后同期日期
     */
    public static String getWeeksAfter(String inputDate, int count, String format) {
        return dateTime2Str(getWeeksAfter(str2DateTime(inputDate, format), count), format);
    }

    /**
     * 获取后几周同一天的日期
     *
     * @param inputDate    输入日期
     * @param count        周数
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return count周后同期日期
     */
    public static String getWeeksAfter(String inputDate, int count, String inputFormat, String outputFormat) {
        return dateTime2Str(getWeeksAfter(str2DateTime(inputDate, inputFormat), count), outputFormat);
    }

    /**
     * 获取传入日期当周第一天的日期。<br>
     * 注意：当周的第一天是从<i> 周一 </i>开始计算的，与java内置的不同。
     *
     * @param inputDate 输入日期
     *
     * @return 当周第一天的日期
     */
    public static Date getWeekBegin(Date inputDate) {
        return getWeekBegin(new DateTime(inputDate)).toDate();
    }

    /**
     * 获取传入日期当周第一天的日期。<br>
     * 注意：当周的第一天是从<i> 周一 </i>开始计算的，与java内置的不同。
     *
     * @param inputDate 输入日期
     *
     * @return 当周第一天的日期
     */
    public static DateTime getWeekBegin(DateTime inputDate) {
        // DayOfWeek: 1 -> Monday, ..., 7 -> Sunday
        return inputDate.withDayOfWeek(1);
    }

    /**
     * 获取传入日期当周第一天的日期。<br>
     * 注意：当周的第一天是从<i> 周一 </i>开始计算的，与java内置的不同。
     *
     * @param inputDate 输入日期
     *
     * @return 当周第一天的日期
     */
    public static String getWeekBegin(String inputDate) {
        return getWeekBegin(inputDate, DATE_FORMAT);
    }

    /**
     * 获取传入日期当周第一天的日期。<br>
     * 注意：当周的第一天是从<i> 周一 </i>开始计算的，与java内置的不同。
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return 当周第一天的日期
     */
    public static String getWeekBegin(String inputDate, String format) {
        return dateTime2Str(getWeekBegin(str2DateTime(inputDate, format)), format);
    }

    /**
     * 获取传入日期当周第一天的日期。<br>
     * 注意：当周的第一天是从<i> 周一 </i>开始计算的，与java内置的不同。
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return 当周第一天的日期
     */
    public static String getWeekBegin(String inputDate, String inputFormat, String outputFormat) {
        return dateTime2Str(getWeekBegin(str2DateTime(inputDate, inputFormat)), outputFormat);
    }

    /**
     * 获取传入日期当周最后一天的日期。<br>
     * 注意：当周的最后一天为<i>周日</i>，与java内置的不同。
     *
     * @param inputDate 输入日期
     *
     * @return 当周最后一天的日期
     */
    public static Date getWeekEnd(Date inputDate) {
        return getWeekEnd(new DateTime(inputDate)).toDate();
    }

    /**
     * 获取传入日期当周最后一天的日期。<br>
     * 注意：当周的最后一天为<i>周日</i>，与java内置的不同。
     *
     * @param inputDate 输入日期
     *
     * @return 当周最后一天的日期
     */
    public static DateTime getWeekEnd(DateTime inputDate) {
        return inputDate.withDayOfWeek(7);
    }

    /**
     * 获取传入日期当周最后一天的日期。<br>
     * 注意：当周的最后一天为<i>周日</i>，与java内置的不同。
     *
     * @param inputDate 输入日期
     *
     * @return 当周最后一天的日期
     */
    public static String getWeekEnd(String inputDate) {
        return getWeekEnd(inputDate, DATE_FORMAT);
    }

    /**
     * 获取传入日期当周最后一天的日期。<br>
     * 注意：当周的最后一天为<i>周日</i>，与java内置的不同。
     *
     * @param inputDate 输入日期
     * @param format    输入输出格式
     *
     * @return 当周最后一天的日期
     */
    public static String getWeekEnd(String inputDate, String format) {
        return dateTime2Str(getWeekEnd(str2DateTime(inputDate, format)), format);
    }

    /**
     * 获取传入日期当周最后一天的日期<br>
     * 注意：当周的最后一天为<i>周日</i>，与java内置的不同。
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入格式
     * @param outputFormat 输出格式
     *
     * @return 当周最后一天的日期
     */
    public static String getWeekEnd(String inputDate, String inputFormat, String outputFormat) {
        return dateTime2Str(getWeekEnd(str2DateTime(inputDate, inputFormat)), outputFormat);
    }

    /**
     * 获取前一月的同一天的日期
     *
     * @param inputDate 输入日期，Date类型
     *
     * @return Date类型
     */
    public static Date getMonthBefore(Date inputDate) {
        return getMonthBefore(inputDate, 1);
    }

    /**
     * 获取before个月的同一天的日期
     *
     * @param inputDate 输入日期，Date类型
     * @param before    月数
     *
     * @return Date类型
     */
    public static Date getMonthBefore(Date inputDate, int before) {
        return new DateTime(inputDate).minusMonths(before).toDate();
    }

    /**
     * 获取before个月的同一天的日期
     *
     * @param inputDate 输入日期，DateTime类型
     * @param before    月数
     *
     * @return DateTime类型
     */
    public static DateTime getMonthBefore(DateTime inputDate, int before) {
        return inputDate.minusMonths(before);
    }

    /**
     * 获取前一月的同一天的日期
     *
     * @param inputDate 输入日期
     *
     * @return String类型
     */
    public static String getMonthBefore(String inputDate) {
        return getMonthBefore(inputDate, DATE_FORMAT);
    }

    /**
     * 获取before个月的同一天的日期
     *
     * @param inputDate 输入日期
     * @param count     月数
     *
     * @return String类型
     */
    public static String getMonthBefore(String inputDate, int count) {
        return dateTime2Str(getMonthBefore(str2DateTime(inputDate), count));
    }

    /**
     * 获取前一月的同一天的日期，可自定义传入的日期格式
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getMonthBefore(String inputDate, String format) {
        return dateTime2Str(getMonthBefore(str2DateTime(inputDate, format), 1), format);
    }

    /**
     * 获取前一月的同一天的日期，可自定义传入的日期格式
     *
     * @param inputDate 输入日期
     * @param count     月数
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getMonthBefore(String inputDate, int count, String format) {
        return dateTime2Str(getMonthBefore(str2DateTime(inputDate, format), count), format);
    }

    /**
     * 获取前一月的同一天的日期，可自定义传入的日期格式
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getMonthBefore(String inputDate, String inputFormat, String outputFormat) {
        return dateTime2Str(getMonthBefore(str2DateTime(inputDate, inputFormat), 1), outputFormat);
    }

    /**
     * 获取前一月的同一天的日期，可自定义传入的日期格式
     *
     * @param inputDate    输入日期
     * @param count        月数
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getMonthBefore(String inputDate, int count, String inputFormat, String outputFormat) {
        return dateTime2Str(getMonthBefore(str2DateTime(inputDate, inputFormat), count), outputFormat);
    }

    /**
     * 获取前一月的同一天的日期，如果输入日期为月末，则对齐为上个月末
     *
     * @param inputDate 输入日期
     * @param count     月数
     *
     * @return DateTime类型
     */
    public static DateTime getMonthBeforeAlign(DateTime inputDate, int count) {
        DateTime val = DateTimeUtil.getMonthBefore(inputDate, count);
        return DateTimeUtil.getMonthEnd(inputDate).equals(inputDate) ? DateTimeUtil.getMonthEnd(val) : val;
    }

    /**
     * 获取前一月的同一天的日期，如果输入日期为月末，则对齐为上个月末
     *
     * @param inputDate 输入日期
     * @param count     月数
     *
     * @return String类型
     */
    public static String getMonthBeforeAlign(String inputDate, int count) {
        return dateTime2Str(getMonthBeforeAlign(str2DateTime(inputDate, DATE_FORMAT), count), DATE_FORMAT);
    }

    /**
     * 获取前一月的同一天的日期，如果输入日期为月末，则对齐为上个月末
     *
     * @param inputDate 输入日期
     * @param count     月数
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getMonthBeforeAlign(String inputDate, int count, String format) {
        return dateTime2Str(getMonthBeforeAlign(str2DateTime(inputDate, format), count), format);
    }

    /**
     * 获取前一月的同一天的日期，如果输入日期为月末，则对齐为上个月末
     *
     * @param inputDate    输入日期
     * @param count        月数
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getMonthBeforeAlign(String inputDate, int count, String inputFormat, String outputFormat) {
        return dateTime2Str(getMonthBeforeAlign(str2DateTime(inputDate, inputFormat), count), outputFormat);
    }

    /**
     * 获取上一季度的同一天的日期
     *
     * @param inputDate 输入日期
     *
     * @return Date类型
     */
    public static Date getQuarterBefore(Date inputDate) {
        return getQuarterBefore(new DateTime(inputDate)).toDate();
    }

    /**
     * 获取上一季度的同一天的日期
     *
     * @param inputDate 输入日期
     *
     * @return DateTime类型
     */
    public static DateTime getQuarterBefore(DateTime inputDate) {
        int quarterMonth = (inputDate.getMonthOfYear() - 1) / 3 * 3 + 1;
        DateTime quarterBegin = inputDate.withMonthOfYear(quarterMonth).withDayOfMonth(1);
        DateTime endDate = quarterBegin.minusDays(1);
        int day = new DateTime(inputDate).getDayOfYear() - quarterBegin.getDayOfYear() + 1;
        DateTime result = quarterBegin.minusMonths(3).plusDays(day - 1);
        if (result.compareTo(endDate) > 0) {
            return endDate;
        } else {
            return result;
        }
    }

    /**
     * 获取上一季度的同一天的日期
     *
     * @param inputDate 输入日期
     *
     * @return String类型
     */
    public static String getQuarterBefore(String inputDate) {
        return getQuarterBefore(inputDate, DATE_FORMAT);
    }

    /**
     * 获取上一季度的同一天的日期
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getQuarterBefore(String inputDate, String format) {
        return dateTime2Str(getQuarterBefore(str2DateTime(inputDate, format)), format);
    }

    /**
     * 获取上一季度的同一天的日期
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getQuarterBefore(String inputDate, String inputFormat, String outputFormat) {
        return dateTime2Str(getQuarterBefore(str2DateTime(inputDate, inputFormat)), outputFormat);
    }

    /**
     * 获取上一季度的同一天的日期，如果输入日期为季末，则对齐为上个季末
     *
     * @param inputDate 输入日期
     *
     * @return DateTime类型
     */
    public static DateTime getQuarterBeforeAlign(DateTime inputDate) {
        DateTime result = getQuarterBefore(inputDate);
        return inputDate.equals(getQuarterEnd(inputDate)) ? getQuarterEnd(result) : result;
    }

    /**
     * 获取上一季度的同一天的日期，如果输入日期为季末，则对齐为上个季末
     *
     * @param inputDate 输入日期
     *
     * @return String类型
     */
    public static String getQuarterBeforeAlign(String inputDate) {
        return getQuarterBeforeAlign(inputDate, DATE_FORMAT);
    }

    /**
     * 获取上一季度的同一天的日期，如果输入日期为季末，则对齐为上个季末
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getQuarterBeforeAlign(String inputDate, String format) {
        return dateTime2Str(getQuarterBeforeAlign(str2DateTime(inputDate, format)), format);
    }

    /**
     * 获取上一季度的同一天的日期，如果输入日期为季末，则对齐为上个季末
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getQuarterBeforeAlign(String inputDate, String inputFormat, String outputFormat) {
        return dateTime2Str(getQuarterBeforeAlign(str2DateTime(inputDate, inputFormat)), outputFormat);
    }

    /**
     * 获取去年的同一天的日期
     *
     * @param inputDate 输入日期
     *
     * @return Date类型
     */
    public static Date getYearBefore(Date inputDate) {
        return getYearBefore(new DateTime(inputDate)).toDate();
    }

    /**
     * 获取去年的同一天的日期
     *
     * @param inputDate 输入日期
     *
     * @return DateTime类型
     */
    public static DateTime getYearBefore(DateTime inputDate) {
        DateTime endDate = inputDate.withDayOfYear(1).minusDays(1);
        if (endDate.getDayOfYear() < inputDate.getDayOfYear()) {
            return endDate;
        } else {
            return inputDate.minusYears(1).withDayOfYear(inputDate.getDayOfYear());
        }
    }

    /**
     * 获取去年的同一天的日期
     *
     * @param inputDate 输入日期
     *
     * @return String类型
     */
    public static String getYearBefore(String inputDate) {
        return getYearBefore(inputDate, DATE_FORMAT);
    }

    /**
     * 获取去年的同一天的日期
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getYearBefore(String inputDate, String format) {
        return dateTime2Str(getYearBefore(str2DateTime(inputDate, format)), format);
    }

    /**
     * 获取去年的同一天的日期
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getYearBefore(String inputDate, String inputFormat, String outputFormat) {
        return dateTime2Str(getYearBefore(str2DateTime(inputDate, inputFormat)), outputFormat);
    }

    /**
     * 返回年末的日期
     *
     * @param inputDate 输入日期
     *
     * @return DateTime类型
     */
    public static DateTime getYearEnd(DateTime inputDate) {
        return inputDate.withDayOfYear(1).plusYears(1).minusDays(1);
    }

    /**
     * 返回年末的日期
     *
     * @param inputDate 输入日期
     *
     * @return DateTime类型
     */
    public static String getYearEnd(String inputDate) {
        return getYearEnd(inputDate, DATE_FORMAT);
    }

    /**
     * 返回年末的日期
     *
     * @param inputDate 输入日期
     * @param format    输出日期格式
     *
     * @return DateTime类型
     */
    public static String getYearEnd(String inputDate, String format) {
        return dateTime2Str(getYearEnd(str2DateTime(inputDate, format)), format);
    }

    /**
     * 返回年末的日期
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return DateTime类型
     */
    public static String getYearEnd(String inputDate, String inputFormat, String outputFormat) {
        return dateTime2Str(getYearEnd(str2DateTime(inputDate, inputFormat)), outputFormat);
    }

    /**
     * 获取去年的同一天的日期，如果输入日期为年末，则对齐为去年年末
     *
     * @param inputDate 输入日期
     *
     * @return DateTime类型
     */
    public static DateTime getYearBeforeAlign(DateTime inputDate) {
        DateTime result = getYearBefore(inputDate);
        return result.equals(getYearEnd(result)) ? getYearEnd(result) : result;
    }

    /**
     * 获取去年的同一天的日期，如果输入日期为年末，则对齐为去年年末
     *
     * @param inputDate 输入日期
     *
     * @return String类型
     */
    public static String getYearBeforeAlign(String inputDate) {
        return getYearBeforeAlign(inputDate, DATE_FORMAT);
    }

    /**
     * 获取去年的同一天的日期，如果输入日期为年末，则对齐为去年年末
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getYearBeforeAlign(String inputDate, String format) {
        return dateTime2Str(getYearBeforeAlign(str2DateTime(inputDate, format)), format);
    }

    /**
     * 获取去年的同一天的日期，如果输入日期为年末，则对齐为去年年末
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getYearBeforeAlign(String inputDate, String inputFormat, String outputFormat) {
        return dateTime2Str(getYearBeforeAlign(str2DateTime(inputDate, inputFormat)), outputFormat);
    }

    /**
     * 获取去年的同一天的日期，日期按月对齐
     *
     * @param inputDate 输入日期
     *
     * @return DateTime类型
     */
    public static DateTime getYearBeforeAlignMonth(DateTime inputDate) {
        DateTime result = inputDate.minusYears(1);
        return result.equals(getMonthEnd(result)) ? getMonthEnd(result) : result;
    }

    /**
     * 获取去年的同一天的日期，日期按月对齐
     *
     * @param inputDate 输入日期
     *
     * @return String类型
     */
    public static String getYearBeforeAlignMonth(String inputDate) {
        return getYearBeforeAlignMonth(inputDate, DATE_FORMAT);
    }

    /**
     * 获取去年的同一天的日期，日期按月对齐
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getYearBeforeAlignMonth(String inputDate, String format) {
        return dateTime2Str(getYearBeforeAlignMonth(str2DateTime(inputDate, format)), format);
    }

    /**
     * 获取去年的同一天的日期，日期按月对齐
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getYearBeforeAlignMonth(String inputDate, String inputFormat, String outputFormat) {
        return dateTime2Str(getYearBeforeAlignMonth(str2DateTime(inputDate, inputFormat)), outputFormat);
    }

    /**
     * 获取传入日期当月第一天的日期。
     *
     * @param inputDate 输入日期
     *
     * @return Date类型
     */
    public static Date getMonthBegin(Date inputDate) {
        return getMonthBegin(inputDate, 1);
    }

    /**
     * 获取传入日期当月第一天的日期。
     *
     * @param inputDate 输入日期
     *
     * @return String类型
     */
    public static String getMonthBegin(String inputDate) {
        return getMonthBegin(inputDate, 1, DATE_FORMAT);
    }

    /**
     * 获取传入日期当月第 day 天的日期。 注意：返回的日期始终在当月的范围内，即 day &gt;= 0时返回第一天，超过当月天数时返回最后一天。
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return Date类型
     */
    public static Date getMonthBegin(Date inputDate, int count) {
        return getMonthBegin(new DateTime(inputDate), count).toDate();
    }

    /**
     * 获取传入日期当月第 day 天的日期。 注意：返回的日期始终在当月的范围内，即 day &gt;= 0时返回第一天，超过当月天数时返回最后一天。
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return DateTime类型
     */
    public static DateTime getMonthBegin(DateTime inputDate, int count) {
        int dayOfMonth = inputDate.dayOfMonth().getMaximumValue();
        count = Math.max(1, Math.min(count, dayOfMonth));
        return inputDate.withDayOfMonth(count);
    }

    /**
     * 获取传入日期当月第 day 天的日期。 注意：返回的日期始终在当月的范围内，即 day &gt;= 0时返回第一天，超过当月天数时返回最后一天。
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return String类型
     */
    public static String getMonthBegin(String inputDate, int count) {
        return getMonthBegin(inputDate, count, DATE_FORMAT);
    }

    /**
     * 获取传入日期当月第一天的日期。
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getMonthBegin(String inputDate, String format) {
        return getMonthBegin(inputDate, 1, format);
    }

    /**
     * 获取传入日期当月第 day 天的日期。 注意：返回的日期始终在当月的范围内，即 day &gt;= 0时返回第一天，超过当月天数时返回最后一天。
     *
     * @param inputDate 输入日期
     * @param count     天数
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getMonthBegin(String inputDate, int count, String format) {
        return dateTime2Str(getMonthBegin(str2DateTime(inputDate, format), count), format);
    }

    /**
     * 获取传入日期当月第 day 天的日期。 注意：返回的日期始终在当月的范围内，即 day &gt;= 0时返回第一天，超过当月天数时返回最后一天。
     *
     * @param inputDate    输入日期
     * @param count        天数
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getMonthBegin(String inputDate, int count, String inputFormat, String outputFormat) {
        return dateTime2Str(getMonthBegin(str2DateTime(inputDate, inputFormat), count), outputFormat);
    }

    /**
     * 获取月末的日期
     *
     * @param inputDate 输入日期
     *
     * @return Date类型
     */
    public static Date getMonthEnd(Date inputDate) {
        return getMonthEnd(new DateTime(inputDate)).toDate();
    }

    /**
     * 获取月末的日期
     *
     * @param inputDate 输入日期
     *
     * @return DateTime类型
     */
    public static DateTime getMonthEnd(DateTime inputDate) {
        return inputDate.withDayOfMonth(1).plusMonths(1).minusDays(1);
    }

    /**
     * 获取月末的日期
     *
     * @param inputDate 输入日期
     *
     * @return String类型
     */
    public static String getMonthEnd(String inputDate) {
        return getMonthEnd(inputDate, DATE_FORMAT);
    }

    /**
     * 获取月末的日期
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getMonthEnd(String inputDate, String format) {
        return dateTime2Str(getMonthEnd(str2DateTime(inputDate, format)), format);
    }

    /**
     * 获取月末的日期
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getMonthEnd(String inputDate, String inputFormat, String outputFormat) {
        return dateTime2Str(getMonthEnd(str2DateTime(inputDate, inputFormat)), outputFormat);
    }

    /**
     * 获取年初的日期
     *
     * @param inputDate 输入日期
     *
     * @return Date类型
     */
    public static Date getYearBegin(Date inputDate) {
        return getYearBegin(inputDate, 1);
    }

    /**
     * 获取年初的日期
     *
     * @param inputDate 输入日期
     *
     * @return String类型
     */
    public static String getYearBegin(String inputDate) {
        return getYearBegin(inputDate, 1, DATE_FORMAT);
    }

    /**
     * 获取年初的日期
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getYearBegin(String inputDate, String format) {
        return getYearBegin(inputDate, 1, format);
    }

    /**
     * 获取年初的日期
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getYearBegin(String inputDate, String inputFormat, String outputFormat) {
        return getYearBegin(inputDate, 1, inputFormat, outputFormat);
    }

    /**
     * 获取当年第 count 天的日期。 注意：返回的日期始终在当年的范围内，即 count &gt;= 0时返回第一天，超过当年天数时返回最后一天。
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return Date类型
     */
    public static Date getYearBegin(Date inputDate, int count) {
        return getYearBegin(new DateTime(inputDate), count).toDate();
    }

    /**
     * 获取当年第 count 天的日期。 注意：返回的日期始终在当年的范围内，即 count &gt;= 0时返回第一天，超过当年天数时返回最后一天。
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return DateTime类型
     */
    public static DateTime getYearBegin(DateTime inputDate, int count) {
        int dayOfYear = inputDate.dayOfYear().getMaximumValue();
        count = Math.max(1, Math.min(count, dayOfYear));
        return inputDate.withDayOfYear(count);
    }

    /**
     * 获取当年第 count 天的日期。 注意：返回的日期始终在当年的范围内，即 count &gt;= 0时返回第一天，超过当年天数时返回最后一天。
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return String类型
     */
    public static String getYearBegin(String inputDate, int count) {
        return getYearBegin(inputDate, count, DATE_FORMAT);
    }

    /**
     * 获取当年第 count 天的日期。 注意：返回的日期始终在当年的范围内，即 count &gt;= 0时返回第一天，超过当年天数时返回最后一天。
     *
     * @param inputDate 输入日期
     * @param count     天数
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getYearBegin(String inputDate, int count, String format) {
        return dateTime2Str(getYearBegin(str2DateTime(inputDate, format), count), format);
    }

    /**
     * 获取当年第 count 天的日期。 注意：返回的日期始终在当年的范围内，即 count &gt;= 0时返回第一天，超过当年天数时返回最后一天。
     *
     * @param inputDate    输入日期
     * @param count        天数
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getYearBegin(String inputDate, int count, String inputFormat, String outputFormat) {
        return dateTime2Str(getYearBegin(str2DateTime(inputDate, inputFormat), count), outputFormat);
    }

    /**
     * 获取当前季度第一天日期。
     *
     * @param inputDate 输入日期
     *
     * @return Date类型
     */
    public static Date getQuarterBegin(Date inputDate) {
        return getQuarterBegin(inputDate, 1);
    }

    /**
     * 获取当前季度第一天日期。
     *
     * @param inputDate 输入日期
     *
     * @return String类型
     */
    public static String getQuarterBegin(String inputDate) {
        return getQuarterBegin(inputDate, 1, DATE_FORMAT);
    }

    /**
     * 获取当前季度第一天日期。
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getQuarterBegin(String inputDate, String format) {
        return getQuarterBegin(inputDate, 1, format);
    }

    /**
     * 获取当前季度第一天日期。
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getQuarterBegin(String inputDate, String inputFormat, String outputFormat) {
        return getQuarterBegin(inputDate, 1, inputFormat, outputFormat);
    }

    /**
     * 获取当前季度第 day 天日期。 注意：返回的日期始终在当季的范围内，即 day &gt;= 0时返回第一天，超过当季天数时返回最后一天。
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return Date类型
     */
    public static Date getQuarterBegin(Date inputDate, int count) {
        return getQuarterBegin(new DateTime(inputDate), count).toDate();
    }

    /**
     * 获取当前季度第 day 天日期。 注意：返回的日期始终在当季的范围内，即 day &gt;= 0时返回第一天，超过当季天数时返回最后一天。
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return DateTime类型
     */
    public static DateTime getQuarterBegin(DateTime inputDate, int count) {
        int quarterMonth = (inputDate.getMonthOfYear() - 1) / 3 * 3 + 1;
        count = Math.max(1, count);
        DateTime quarterBegin = inputDate.withMonthOfYear(quarterMonth).withDayOfMonth(1);
        DateTime endDate = quarterBegin.plusMonths(3).minusDays(1);
        DateTime result = quarterBegin.plusDays(count - 1);
        return result.isAfter(endDate) ? endDate : result;
    }

    /**
     * 获取当前季度第 day 天日期。 注意：返回的日期始终在当季的范围内，即 day &gt;= 0时返回第一天，超过当季天数时返回最后一天。
     *
     * @param inputDate 输入日期
     * @param count     天数
     *
     * @return String类型
     */
    public static String getQuarterBegin(String inputDate, int count) {
        return getQuarterBegin(inputDate, count, DATE_FORMAT);
    }

    /**
     * 获取当前季度第 day 天日期。 注意：返回的日期始终在当季的范围内，即 day &gt;= 0时返回第一天，超过当季天数时返回最后一天。
     *
     * @param inputDate 输入日期
     * @param count     天数
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getQuarterBegin(String inputDate, int count, String format) {
        return dateTime2Str(getQuarterBegin(str2DateTime(inputDate, format), count), format);
    }

    /**
     * 获取当前季度第 day 天日期。 注意：返回的日期始终在当季的范围内，即 day &gt;= 0时返回第一天，超过当季天数时返回最后一天。
     *
     * @param inputDate    输入日期
     * @param count        天数
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getQuarterBegin(String inputDate, int count, String inputFormat, String outputFormat) {
        return dateTime2Str(getQuarterBegin(str2DateTime(inputDate, inputFormat), count), outputFormat);
    }

    /**
     * 获取季末的日期
     *
     * @param inputDate 输入日期
     *
     * @return Date类型
     */
    public static Date getQuarterEnd(Date inputDate) {
        return getQuarterEnd(new DateTime(inputDate)).toDate();
    }

    /**
     * 获取季末的日期
     *
     * @param inputDate 输入日期
     *
     * @return DateTime类型
     */
    public static DateTime getQuarterEnd(DateTime inputDate) {
        int quarterMonth = (inputDate.getMonthOfYear() - 1) / 3 * 3 + 1;
        DateTime quarterBegin = inputDate.withMonthOfYear(quarterMonth).withDayOfMonth(1);
        return quarterBegin.plusMonths(3).minusDays(1);
    }

    /**
     * 获取季末的日期
     *
     * @param inputDate 输入日期
     *
     * @return String类型
     */
    public static String getQuarterEnd(String inputDate) {
        return getQuarterEnd(inputDate, DATE_FORMAT);
    }

    /**
     * 获取季末的日期
     *
     * @param inputDate 输入日期
     * @param format    输入输出日期格式
     *
     * @return String类型
     */
    public static String getQuarterEnd(String inputDate, String format) {
        return dateTime2Str(getQuarterEnd(str2DateTime(inputDate, format)), format);
    }

    /**
     * 获取季末的日期
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String getQuarterEnd(String inputDate, String inputFormat, String outputFormat) {
        return dateTime2Str(getQuarterEnd(str2DateTime(inputDate, inputFormat)), outputFormat);
    }

    // 类型转换

    /**
     * 将字符串转换为时间值
     *
     * @param format 时间格式
     * @param value  包含时间的字符串
     *
     * @return TimeStamp类型
     *
     * @throws IllegalArgumentException 如果解析时间出错
     */
    public static Timestamp stringToTimestamp(String format, String value) {
        DateTime dt = DateTimeFormat.forPattern(format).parseDateTime(value);
        return new Timestamp(dt.getMillis());
    }

    /**
     * 将字符串转换为时间值
     *
     * @param value 默认时间格式的字符串
     *
     * @return TimeStamp类型
     *
     * @throws IllegalArgumentException 如果解析时间出错
     */
    public static Timestamp dateTimeStringToTimestamp(String value) {
        return stringToTimestamp(DATE_TIME_FORMAT, value);
    }

    /**
     * 将字符串转换为时间值
     *
     * @param value 默认日期格式的字符串
     *
     * @return TimeStamp类型
     *
     * @throws IllegalArgumentException 如果解析时间出错
     */
    public static Timestamp dateStringToTimestamp(String value) {
        return stringToTimestamp(DATE_FORMAT, value);
    }

    /**
     * 将timestamp转换为字符串
     *
     * @param format 时间格式
     * @param value  时间值
     *
     * @return String类型
     */
    public static String timestampToString(String format, Timestamp value) {
        return DateTimeFormat.forPattern(format).print(value.getTime());
    }

    /**
     * 将timestamp转换为默认时间格式的字符串
     *
     * @param value 时间值
     *
     * @return TimeStamp类型
     */
    public static String timestampToDateTimeString(Timestamp value) {
        return timestampToString(DATE_TIME_FORMAT, value);
    }

    /**
     * 将timestamp转换为默认日期格式的字符串
     *
     * @param value 时间值
     *
     * @return TimeStamp类型
     */
    public static String timestampToDateString(Timestamp value) {
        return timestampToString(DATE_FORMAT, value);
    }

    /**
     * 将字符串转换为Date
     *
     * @param dateStr 日期字符串
     *
     * @return Date类型
     */
    public static Date str2Date(String dateStr) {
        return str2Date(dateStr, DATE_FORMAT);
    }

    /**
     * 将字符串转换为Date
     *
     * @param dateStr     日期字符串
     * @param inputFormat 输入日期格式
     *
     * @return Date类型
     */
    public static Date str2Date(String dateStr, String inputFormat) {
        return str2Date(dateStr, inputFormat, null);
    }

    /**
     * 将字符串转换为Date
     *
     * @param dateStr     日期字符串
     * @param inputFormat 输入日期格式
     * @param defaultDate 默认日期值
     *
     * @return Date类型
     */
    public static Date str2Date(String dateStr, String inputFormat, Date defaultDate) {
        try {
            DateTime dt = DateTimeFormat.forPattern(inputFormat).parseDateTime(dateStr);
            return dt.toDate();
        } catch (IllegalArgumentException e) {
            return defaultDate;
        }
    }

    /**
     * 将Date转换为默认日期格式的字符串
     *
     * @param date 日期
     *
     * @return String类型
     */
    public static String date2Str(Date date) {
        return date2Str(date, DATE_FORMAT);
    }

    /**
     * 将Date转换为指定格式的字符串
     *
     * @param date         日期
     * @param outputFormat 输出格式
     *
     * @return String类型
     */
    public static String date2Str(Date date, String outputFormat) {
        DateTime dt = new DateTime(date);
        return dt.toString(outputFormat);
    }

    /**
     * 将默认日期格式字符串转换为DateTime
     *
     * @param dateStr 日期字符串
     *
     * @return DateTime类型
     */
    public static DateTime str2DateTime(String dateStr) {
        return str2DateTime(dateStr, DateTimeUtil.DATE_FORMAT, null);
    }

    /**
     * 将字符串转换为DateTime
     *
     * @param dateStr     日期字符串
     * @param inputFormat 输入日期格式
     *
     * @return DateTime类型
     */
    public static DateTime str2DateTime(String dateStr, String inputFormat) {
        return str2DateTime(dateStr, inputFormat, null);
    }

    /**
     * 将字符串转换为DateTime
     *
     * @param dateStr         日期字符串
     * @param inputFormat     输入日期格式
     * @param defaultDateTime 默认日期值
     *
     * @return DateTime类型
     */
    public static DateTime str2DateTime(String dateStr, String inputFormat, DateTime defaultDateTime) {
        try {
            return DateTimeFormat.forPattern(inputFormat).parseDateTime(dateStr);
        } catch (IllegalArgumentException e) {
            return defaultDateTime;
        }
    }

    /**
     * 将DateTime转换为默认日期格式字符串
     *
     * @param dateTime 日期
     *
     * @return String类型
     */
    public static String dateTime2Str(DateTime dateTime) {
        return dateTime2Str(dateTime, DateTimeUtil.DATE_FORMAT);
    }

    /**
     * 将DateTime转换为指定格式字符串
     *
     * @param dateTime     日期
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String dateTime2Str(DateTime dateTime, String outputFormat) {
        return dateTime.toString(outputFormat);
    }

    /**
     * 转换日期字符串格式
     *
     * @param inputDate    输入日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String convert(String inputDate, String inputFormat, String outputFormat) {
        return DateTimeUtil.dateTime2Str(DateTimeUtil.str2DateTime(inputDate, inputFormat), outputFormat);
    }

    /**
     * 转换日期字符串格式
     *
     * @param inputDate    输入默认格式的日期
     * @param outputFormat 输出日期格式
     *
     * @return String类型
     */
    public static String convert(String inputDate, String outputFormat) {
        return convert(inputDate, DateTimeUtil.DATE_FORMAT, outputFormat);
    }

    /**
     * 计算日期间相差的天数
     *
     * @param date1 日期一
     * @param date2 日期二
     *
     * @return 相差的天数，忽略不足一天的部分
     */
    public static int dateDifference(String date1, String date2) {
        return dateDifference(date1, date2, DateTimeUtil.DATE_FORMAT);
    }

    /**
     * 计算日期间相差的天数
     *
     * @param date1       日期一
     * @param date2       日期二
     * @param inputFormat 输入日期的格式
     *
     * @return 相差的天数，忽略不足一天的部分
     */
    public static int dateDifference(String date1, String date2, String inputFormat) {
        return dateDifference(str2DateTime(date1, inputFormat), str2DateTime(date2, inputFormat));
    }

    /**
     * 计算日期间相差的天数
     * 注意：部分包含夏令时的时区中此方法返回的天数不准确，仅作兼容
     *
     * @param date1 日期一
     * @param date2 日期二
     *
     * @return 日期间天数差的绝对值（一天之内为0）
     */
    @Deprecated
    public static int dateDifference(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        return (int) Math.abs(diff / (24 * 60 * 60 * 1000));
    }

    /**
     * 计算日期间相差的天数
     *
     * @param date1 日期一
     * @param date2 日期二
     *
     * @return 日期间天数差的绝对值（一天之内为0）
     */
    public static int dateDifference(ReadableInstant date1, ReadableInstant date2) {
        return Math.abs(Days.daysBetween(date1, date2).getDays());
    }

    /**
     * 获取日期集合
     *
     * @param dateFrom 起始日期
     * @param dateTo   结束日期
     *
     * @return 起始日期至结束日期间的所有日期组成的集合（含始末日期）
     */
    public static Set<String> dateRange(String dateFrom, String dateTo) {
        return dateRange(dateFrom, dateTo, DateTimeUtil.DATE_FORMAT);
    }

    /**
     * 获取日期集合
     *
     * @param dateFrom 起始日期
     * @param dateTo   结束日期
     * @param format   兼容已有代码，为输出日期格式，TODO: 下一版本将会改为输入输出日期格式
     *
     * @return 起始日期至结束日期间的所有日期组成的集合（含始末日期）
     */
    public static Set<String> dateRange(String dateFrom, String dateTo, String format) {
        return dateRange(dateFrom, dateTo, DATE_FORMAT, format);
    }

    /**
     * 获取日期集合
     *
     * @param dateFrom     起始日期
     * @param dateTo       结束日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return 起始日期至结束日期间的所有日期组成的集合（含始末日期）
     */
    public static Set<String> dateRange(String dateFrom, String dateTo, String inputFormat, String outputFormat) {
        Set<String> range = Sets.newHashSet();
        DateTime datefrom = str2DateTime(dateFrom, inputFormat);
        DateTime dateto = str2DateTime(dateTo, inputFormat);
        if (datefrom.compareTo(dateto) > 0) {
            DateTime tmp = dateto;
            dateto = datefrom;
            datefrom = tmp;
        }
        DateTime current = datefrom;
        while (current.compareTo(dateto) <= 0) {
            range.add(dateTime2Str(current, outputFormat));
            current = current.plusDays(1);
        }
        return range;
    }

    /**
     * 获取日期有序列表
     *
     * @param dateFrom 起始日期
     * @param dateTo   结束日期
     *
     * @return 起始日期至结束日期间的按顺序组成的列表（含始末日期）
     */
    public static List<String> dateList(String dateFrom, String dateTo) {
        return dateList(dateFrom, dateTo, DATE_FORMAT);
    }

    /**
     * 获取日期有序列表
     *
     * @param dateFrom 起始日期
     * @param dateTo   结束日期
     * @param format   输入输出日期格式
     *
     * @return 起始日期至结束日期间的按顺序组成的列表（含始末日期）
     */
    public static List<String> dateList(String dateFrom, String dateTo, String format) {
        return dateList(dateFrom, dateTo, format, format);
    }

    /**
     * 获取日期有序列表
     *
     * @param dateFrom     起始日期
     * @param dateTo       结束日期
     * @param inputFormat  输入日期格式
     * @param outputFormat 输出日期格式
     *
     * @return 起始日期至结束日期间的按顺序组成的列表（含始末日期）
     */
    public static List<String> dateList(String dateFrom, String dateTo, String inputFormat, String outputFormat) {
        List<String> range = Lists.newArrayList();
        DateTime datefrom = str2DateTime(dateFrom, inputFormat);
        DateTime dateto = str2DateTime(dateTo, inputFormat);
        DateTime current = datefrom;
        if (datefrom.compareTo(dateto) > 0) {
            for (; current.compareTo(dateto) >= 0; current = current.minusDays(1)) {
                range.add(dateTime2Str(current, outputFormat));
            }
        } else {
            for (; current.compareTo(dateto) <= 0; current = current.plusDays(1)) {
                range.add(dateTime2Str(current, outputFormat));
            }
        }
        return range;
    }

    public static String currentDateString() {
        return date2Str(new Date());
    }

    public static Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Long unixTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    private static String getDateTimeBySeconds(String dateTime, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(str2Date(dateTime, "yyyy-MM-dd HH:mm:ss"));
        calendar.add(Calendar.SECOND, seconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(calendar.getTime());
    }

    public static String getCurrentDateTime() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        return dateFormat.format(now);
    }

    public static int getCurrentHour() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        return Integer.valueOf(sdf.format(new Date()));
    }
}
