package com.cqf.hn.tool.util;

//public class DateUtil {

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * @author Tan Tailiang
 *         创建日期：2006-4-21
 *         类说明：<p>对日期进行操作类</p>
 */
public class DateUtil {

    public static final long SECOND_UNIT = 1000;
    public static final long DAY_UNIT = 24 * 60 * 60 * SECOND_UNIT;
    public static final long HOUR_UNIT = 60 * 60 * SECOND_UNIT;
    public static final long MIN_UNIT = 60 * SECOND_UNIT;

    private static DateFormat dateTimeFormatter = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private static final long MSECONDS_OF_ONE_DAY = 60 * 60 * 1000 * 24;

    private static final long MSECONDS_OF_ONE_MINUTE = 60 * 1000;


    private static final String[] days = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"};

    private static DateFormat dateTimeFormatter1 = new SimpleDateFormat(
            "HHmmss");
    private static DateFormat dateTimeFormatter2 = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    private static DateFormat dateTimeFormatter3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * write by steven.shi 2004-10-18
     * 方法说明：<p>取得指定月份的第一天</p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR, year);
        cl.set(Calendar.MONTH, month - 1);
        cl.set(Calendar.DAY_OF_MONTH, 1);
        return cl.getTime();
    }

    /**
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param date
     * @return
     */
    public static String dateTimeToString1(Date date) {
        if (date == null)
            return "";
        return dateTimeFormatter1.format(date).trim();

    }

    /**
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param date
     * @return
     */
    public static String dateTimeToString3(Date date) {
        if (date == null)
            return "";
        return dateTimeFormatter3.format(date).trim();

    }

    /**
     * 方法说明：<p>取得指定月份的最后一天</p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(int year, int month) {
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR, year);
        cl.set(Calendar.MONTH, month - 1);
        cl.set(Calendar.DAY_OF_MONTH, cl
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        return cl.getTime();
    }

    /**
     * 方法说明：<p>将String型的日期转换成Date型</p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date stringToDateTime(String dateString) throws ParseException {
        Date date = dateTimeFormatter.parse(dateString);
        return date;
    }

    public static Date stringToDateTime1(String dateString) throws ParseException {
        Date date = dateTimeFormatter3.parse(dateString);
        return date;
    }

    /**
     * 方法说明：<p>将String型的日期转换成Date型</p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date stringToDateTime3(String dateString) throws ParseException {
        Date date = dateTimeFormatter3.parse(dateString);
        return date;
    }

    /**
     * 方法说明：<p>将String型的日期转换成Date型</p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param dateString
     * @param format
     * @param locale
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String dateString, String format, Locale locale) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
        return formatter.parse(dateString);
    }


    /**
     * write by steven.shi 2004-10-18
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param orgDate
     * @return
     */
    public static Date convertOrgDate(String orgDate) {
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR, Integer.parseInt(orgDate.substring(0, 4)));
        cl.set(Calendar.MONTH, Integer.parseInt(orgDate.substring(5, 7)) - 1);
        cl.set(Calendar.DAY_OF_MONTH, Integer
                .parseInt(orgDate.substring(8, 10)));
        cl.set(Calendar.HOUR_OF_DAY, 0);
        cl.set(Calendar.MINUTE, 0);
        cl.set(Calendar.SECOND, 0);
        return cl.getTime();
    }

    /**
     * write by steven.shi 2004-10-18
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param orgDate
     * @return
     */
    public static Date convertRealTimeDate(String orgDate) {
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR, Integer.parseInt(orgDate.substring(0, 4)));
        cl.set(Calendar.MONTH, Integer.parseInt(orgDate.substring(5, 7)) - 1);
        cl.set(Calendar.DAY_OF_MONTH, Integer
                .parseInt(orgDate.substring(8, 10)));
        return cl.getTime();
    }

    /**
     * Get the Next Date Write by Jeffy pan 2004-10-21
     * Date Format:YYYY-MM-DD YYYY:M:D YYYY/M/DD
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param date
     * @return
     */
    public static String getNextDate(String date) {

        Calendar cd = Calendar.getInstance();
        StringTokenizer token = new StringTokenizer(date, "-/ :");
        if (token.hasMoreTokens()) {
            cd.set(Calendar.YEAR, Integer.parseInt(token.nextToken()));
        } else {
            cd.set(Calendar.YEAR, 1970);
        }
        if (token.hasMoreTokens()) {
            cd.set(Calendar.MONTH, Integer.parseInt(token.nextToken()) - 1);
        } else {
            cd.set(Calendar.MONTH, 0);
        }
        if (token.hasMoreTokens()) {
            cd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(token.nextToken()));
        } else {
            cd.set(Calendar.DAY_OF_MONTH, 1);
        }
        if (token.hasMoreTokens()) {
            cd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(token.nextToken()));
        } else {
            cd.set(Calendar.HOUR_OF_DAY, 0);
        }
        if (token.hasMoreTokens()) {
            cd.set(Calendar.MINUTE, Integer.parseInt(token.nextToken()));
        } else {
            cd.set(Calendar.MINUTE, 0);
        }
        if (token.hasMoreTokens()) {
            cd.set(Calendar.SECOND, Integer.parseInt(token.nextToken()));
        } else {
            cd.set(Calendar.SECOND, 0);
        }
        if (token.hasMoreTokens()) {
            cd.set(Calendar.MILLISECOND, Integer.parseInt(token.nextToken()));
        } else {
            cd.set(Calendar.MILLISECOND, 0);
        }

        long nextTime = cd.getTimeInMillis() + 24 * 60 * 60 * 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(nextTime));
    }

    /**
     * Get the Next Date Write by Jeffy pan 2004-10-21
     * Date Format:(YYYY-MM-DD) (YYYY:M:D HH:MM:SS)  (YYYY/M/DD hh:MM)
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param date
     * @return
     */
    public static Date stringToDate(String date) {
        if (date == null)
            return null;

        Calendar cd = Calendar.getInstance();
        StringTokenizer token = new StringTokenizer(date, "-/ :");
        if (token.hasMoreTokens()) {
            cd.set(Calendar.YEAR, Integer.parseInt(token.nextToken()));
        } else {
            cd.set(Calendar.YEAR, 1970);
        }
        if (token.hasMoreTokens()) {
            cd.set(Calendar.MONTH, Integer.parseInt(token.nextToken()) - 1);
        } else {
            cd.set(Calendar.MONTH, 0);
        }
        if (token.hasMoreTokens()) {
            cd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(token.nextToken()));
        } else {
            cd.set(Calendar.DAY_OF_MONTH, 1);
        }
        if (token.hasMoreTokens()) {
            cd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(token.nextToken()));
        } else {
            cd.set(Calendar.HOUR_OF_DAY, 0);
        }
        if (token.hasMoreTokens()) {
            cd.set(Calendar.MINUTE, Integer.parseInt(token.nextToken()));
        } else {
            cd.set(Calendar.MINUTE, 0);
        }
        if (token.hasMoreTokens()) {
            cd.set(Calendar.SECOND, Integer.parseInt(token.nextToken()));
        } else {
            cd.set(Calendar.SECOND, 0);
        }
        if (token.hasMoreTokens()) {
            cd.set(Calendar.MILLISECOND, Integer.parseInt(token.nextToken()));
        } else {
            cd.set(Calendar.MILLISECOND, 0);
        }

        return cd.getTime();
    }

    /**
     * Get the Next Date Write by Jeffy pan 2004-10-21
     * Date Format:(YYYY-MM-DD) (YYYY:M:D HH:MM:SS)  (YYYY/M/DD hh:MM)
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        if (date == null)
            return "";
        return dateFormatter.format(date).trim();

    }

    /**
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param date
     * @return
     */
    public static String dateTimeToString2(Date date) {
        if (date == null)
            return "";
        return dateTimeFormatter2.format(date).trim();

    }

    /**
     * Get the Next Date Write by Jeffy pan 2004-10-21
     * Date Format:(YYYY-MM-DD) (YYYY:M:D HH:MM:SS)  (YYYY/M/DD hh:MM)
     */
    public static String dateToString(Date date, String format, Locale locale) {

        SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
        return formatter.format(date).trim();
    }

    /**
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param date
     * @return
     */
    public static String dateTimeToString(Date date) {
        if (date == null)
            return "";
        return dateTimeFormatter.format(date).trim();

    }

    /* Get the Next Date Write by Jeffy pan 2004-10-21
     * Date Format:(YYYY-MM-DD) (YYYY:M:D HH:MM:SS)  (YYYY/M/DD hh:MM)
     */
    public static String dateToString(Date date, String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date).trim();
    }

    /* Get the Next Date Write by Jeffy pan 2004-10-21
     * Date Format:(YYYY-MM-DD) (YYYY:M:D HH:MM:SS)  (YYYY/M/DD hh:MM)
     */
    public static int getDays(String fromDate, String endDate) {

        long from = stringToDate(fromDate).getTime();
        long end = stringToDate(endDate).getTime();

        return (int) ((end - from) / (24 * 60 * 60 * 1000)) + 1;
    }

    /**
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param fromDate
     * @param endDate
     * @return
     */
    public static int getDays(Date fromDate, Date endDate) {

        long from = fromDate.getTime();
        long end = endDate.getTime();

        return (int) ((end - from) / (24 * 60 * 60 * 1000)) + 1;
    }

    /**
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String getTakeTime(Date startDate, Date endDate) {
        int minute = 0;
        try {
            minute = (int) (endDate.getTime() - startDate.getTime())
                    / (1000 * 60);
            return String.valueOf(minute);
        } catch (Exception e) {
            return "";
        }

    }

    /* 获取月份的第一天
     * written by Sammy: 2004-10-26
     */
    public static int getFirstDateOfMonth(int year, int month) {

        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR, year);
        cl.set(Calendar.MONDAY, month - 1);
        return cl.getActualMinimum(Calendar.DAY_OF_MONTH);
    }

    /* 获取月份的最后一天
     * written by Sammy: 2004-10-26
     */
    public static int getLastDateOfMont(int year, int month) {
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR, year);
        cl.set(Calendar.MONDAY, month - 1);
        return cl.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param date
     * @return
     */
    public static java.sql.Date convertUtilDateToSQLDate(Date date) {
        if (date == null)
            return null;
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        java.sql.Date jd = new java.sql.Date(cl.getTimeInMillis());
        return jd;
    }

    /**
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param dateString
     * @return
     */
    public static java.sql.Date convertStringToSQLDate(String dateString) {
        return (convertUtilDateToSQLDate(stringToDate(dateString)));
    }

    public static java.sql.Date convertToSQLDateWithoutTime(Date date) {
        String dateString = dateFormatter.format(date);
        return convertStringToSQLDate(dateString);
    }

    /**
     * get offset to the previous sunday from the specific date
     *
     * @param from the specific start date
     * @param from the specific end date
     * @return offset to the previous sunday
     * @throws ParseException
     */
    public static List getAllSundays(Date from, Date to) {
        List sundayList = new ArrayList();
        int offset = getOffsetToNextSunday(from);
        Date firstSunday = addDate(from, offset);
        Date current = firstSunday;
        while (current.compareTo(to) <= 0) {
            sundayList.add(current);
            current = addDate(current, 7);
        }
        return sundayList;
    }

    /**
     * get offset to the next sunday from the specific date
     *
     * @param date the specific date
     * @return offset to the next sunday
     * @throws ParseException
     */
    public static int getOffsetToNextSunday(Date date) {
        if (getDayOfWeek2(date) == 1)
            return 0;
        return 8 - getDayOfWeek2(date);
    }

    /**
     * get day index of a week for the specific date
     *
     * @param date the specific date
     * @return day index of a week,Mon. is 1,Tues. is 2,Wed. is 3,Thurs. is 4,Fri. is 5,Sat. is 6,Sun. is 7
     * @throws ParseException
     */
    public static int getDayOfWeek(Date date) {
        if (date == null)
            return 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int result = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (result == 0)
            result = 7;
        return result;
    }

    /**
     * get day index of a week for the specific date
     *
     * @param date the specific date
     * @return day index of a week,Sun. is 1,Mon. is 2,Tues. is 3,Wed. is 4,Thurs. is 5,Fri. is 6,Sat. is 7
     * @throws ParseException
     */
    public static int getDayOfWeek2(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * get day index of a week for the specific date
     *
     * @param date the specific date
     * @return day index of a week,Sun. is 1,Mon. is 2,Tues. is 3,Wed. is 4,Thurs. is 5,Fri. is 6,Sat. is 7
     * @throws ParseException
     */
    public static String getDayOfWeek3(Date date) {
        int day = getDayOfWeek(date);
        return days[day - 1];
    }

    /**
     * add days to the specific date
     *
     * @return java.util.Date object after add days
     * @throws ParseException
     */
    public static Date addDateMonth(Date sourceDate, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * add days to the specific date
     *
     * @param days       day count to be added
     * @return java.util.Date object after add days
     * @throws ParseException
     */
    public static Date addDate(Date sourceDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * add days to the specific date
     *
     * @return java.util.Date object after add days
     * @throws ParseException
     */
    public static Date addHour(Date sourceDate, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * add second to the specific date
     *
     * @return java.util.Date object after add seconds
     * @throws ParseException
     */
    public static Date addMinute(Date sourceDate, int Minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.add(Calendar.MINUTE, Minute);
        return calendar.getTime();
    }

    /**
     * add second to the specific date
     *
     * @return java.util.Date object after add seconds
     * @throws ParseException
     */
    public static Date addSecond(Date sourceDate, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }


    /**
     * add days to the specific date
     *
     * @param days       day count to be added
     * @return java.util.Date object after add days
     * @throws ParseException
     */
    public static Date addDate(String stringDate, int days) {
        Date sourceDate = stringToDate(stringDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * @return
     */
    public static String addOneDay(Date sourceDate) {

        Date newDate = addDate(sourceDate, 1);
        return dateToString(newDate);

    }

    /**
     * @param from
     * @param to
     * @return
     * @throws ParseException
     */
    public static long subDate(Date from, Date to) throws ParseException {
        long value = Math.abs(to.getTime() - from.getTime());
        return value / MSECONDS_OF_ONE_DAY;
    }

    public static long subDateMinute(Date from, Date to) throws ParseException {
        long value = Math.abs(to.getTime() - from.getTime());
        return value / MSECONDS_OF_ONE_MINUTE;
    }

    /**
     * <br>Author：zy_fu<br>
     * <br>Date：2007-7-20<br>
     * <br>Mender：<br>
     * <br>Date：YYYY-MM-DD<br>
     * <br>Version:<br>
     * <p>计算两个时间的间隔
     * <p>
     * <p>@param from
     * <p>@param to
     * <p>@param unitInMSecond 时间间隔的单位，1000=秒，60*1000=分钟，60*60*1000=小时...
     * <p>@return
     * <p>@throws ParseException
     */
    public static long getTimespan(Date from, Date to, long unitInMSecond) throws ParseException {
        long value = Math.abs(to.getTime() - from.getTime());
        return value / unitInMSecond;
    }

    /**
     * @param from
     * @param to
     * @return
     * @throws ParseException
     */
    public static long subDate(String from, String to) throws ParseException {
        return subDate(stringToDate(from), stringToDate(to));
    }

    /**
     * 返回时间列表 (startDate, endDate, days[])
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param startDate
     * @param endDate
     * @param days
     * @return
     */
    public static List getStringDateList(String startDate, String endDate, int[] days) {

        List dateList = new ArrayList();

        int days2 = DateUtil.getDays(startDate, endDate) - 1;
        Date fromDate2 = DateUtil.stringToDate(startDate);
        Date endDate2 = DateUtil.stringToDate(endDate);

        Calendar cal = Calendar.getInstance();

        for (int i = 0; i < days2; i++) {

            cal.setTime(fromDate2);
            cal.add(Calendar.DATE, i);

            for (int j = 0; j < days.length; j++) {
                // 星期数等于所选的
                if (days[j] == cal.get(Calendar.DAY_OF_WEEK)) {

                    dateList.add(DateUtil.dateToString(cal.getTime()));
                }//if
            }//for

        }//for

        return dateList;
    }

    /**
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param startDate
     * @param endDate
     * @param days
     * @return
     */
    public static List getDateList(String startDate, String endDate, int[] days) {

        List dateList = new ArrayList();

        int days2 = DateUtil.getDays(startDate, endDate);
        Date fromDate2 = DateUtil.stringToDate(startDate);
        Date endDate2 = DateUtil.stringToDate(endDate);

        Calendar cal = Calendar.getInstance();

        for (int i = 0; i < days2; i++) {

            cal.setTime(fromDate2);
            cal.add(Calendar.DATE, i);

            for (int j = 0; j < days.length; j++) {
                // 星期数等于所选的
                if (days[j] == cal.get(Calendar.DAY_OF_WEEK)) {
                    dateList.add(cal.getTime());
                }//if
            }//for

        }//for

        return dateList;
    }

    /**
     * 方法说明：<p></p>
     * 创建日期：2006-4-21
     * 创建人： Tan Nailiang
     *
     * @param firstDate
     * @param secondDate
     * @return
     */
    public static int compareDate(Date firstDate, Date secondDate) {
        return firstDate.compareTo(secondDate);
    }

    /**
     * Author：hxiang_xu<br>
     * Date：2005-7-8<br>
     * Mender：<br>
     * Date：YYYY-MM-DD<br>
     * Version:<br>
     * 返回修改的时间。
     *
     * @param date  - 传入的时间 <code>Date</code>
     * @param field 要修改的时间类型（Calendar.DATE，Calendar.HOUR, ...） <code>in</code>
     * @param field 要修改的数值 <code>int</code>
     * @return Date
     */
    public static Date AddDate(Date date, int field, int amount) {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(date);
        newCalendar.add(field, amount);
        return newCalendar.getTime();
    }

    public static int getDayDifference(Date date1, Date date2) {
        Calendar gc1 = Calendar.getInstance();
        Calendar gc2 = Calendar.getInstance();
        gc1.setTime(date1);
        gc2.setTime(date2);

        long millis = gc2.getTimeInMillis() - gc1.getTimeInMillis();
        return Math.abs((int) (millis / 1000 / 24 / 60 / 60));
    }

    /**
     * Returns the difference between the two dates in days.
     *
     * @param c1
     * @param c2
     * @return
     */
    public static int getDayDifference(Calendar c1, Calendar c2) {
        return getDayDifference(c1.getTime(), c2.getTime());
    }

    //返回两个string类型日期之间相差的天数
    public static int daysBetween(String smdate, String bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;

        try {
            cal.setTime(sdf.parse(smdate));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            time2 = cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    //两个string类型的日期比较大小
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static boolean isToday(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH) + 1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH) + 1;
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        if (year1 == year2 && month1 == month2 && day1 == day2) {
            return true;
        }
        return false;
    }

    public static boolean isToday(long millis) {
        Date date = new Date(millis);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH) + 1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH) + 1;
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        if (year1 == year2 && month1 == month2 && day1 == day2) {
            return true;
        }
        return false;
    }

    public static String millisToString(long millis, String pattern) {
        return new SimpleDateFormat(pattern, Locale.CHINA).format(new Date(millis));
    }

    public static Date getDayStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    public static Date getDayEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    public static long stringToMillis(String time, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
            return df.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getChatTimeStr(long timeStamp) {
        if (timeStamp == 0) return "";
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(timeStamp * 1000);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        if (!calendar.after(inputTime)) {
            //当前时间在输入时间之前
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
            return sdf.format(currenTimeZone);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
            return "昨天 " + sdf.format(currenTimeZone);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
                return sdf.format(currenTimeZone);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);
                return sdf.format(currenTimeZone);
            }

        }

    }
}
