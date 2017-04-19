package com.common.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Administrator
 *         <p/>
 *         时间相关转换类
 */
public class C_TimeUtil {

    public static String getTimeFromStamp(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        return format.format(timeStamp);
    }

    public static String getTimeFromStamp(Object timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        return format.format(timeStamp);
    }

    public static String getTimeMD(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm",
                Locale.CHINA);
        return format.format(timeStamp);
    }

    public static String getTimeHM(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.CHINA);
        return format.format(timeStamp);
    }

    public static String getTimeFromStampWithHour(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINA);
        return format.format(timeStamp);
    }

    public static String getTimeChinaFromStampWithHour(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm",
                Locale.CHINA);
        return format.format(timeStamp);
    }

    public static String getDotFormatTimeFromStampWithHour(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm",
                Locale.CHINA);
        return format.format(timeStamp);
    }

    public static String getTimeWithFormat(long timeStamp, String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString,
                Locale.CHINA);
        return format.format(timeStamp);
    }

    // 获取yyyy-MM-dd时间 ( 1000 )
    protected String getDate(final long s) {
        if (s > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm",
                    Locale.CHINA);
            return sdf.format(s * 1000);
        }
        return "";
    }

    protected static String getDateMonthDate(final long s) {
        if (s > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm",
                    Locale.CHINA);
            return sdf.format(s);
        }
        return "";
    }

    // 获取HH:mm时间
    protected static String getHourMinTime(long mills) {
        if (mills > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
            return sdf.format(mills);
        }
        return "";
    }

    public static Date getDate(String s) {
        if (TextUtils.isEmpty(s))
            return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateChina(String s) {
        if (TextUtils.isEmpty(s))
            return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            return format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static final long _1min = 60 * 1000L; // 1分钟
    private static final long _1h = 60 * _1min;
    private static final long _1day = 24 * _1h;
    private static final long _15min = 15 * _1min; // 15分钟
    private static final long _30min = 30 * _1min; // 30分钟
    private static final long _60min = _1h; // 60分钟
    private static final long _24h = _1day;
    private static final long _48h = 2 * _1day;
    private static final long _72h = 3 * _1day;
    private static final long _1year = 365 * _1day;

    /**
     * 产品统一这样：发帖时间（最后回复时间）：小于15分钟文案为：“××分钟前”；大于等于15分钟小于等于30分钟文案为：“半小时前”；
     * 大于30分钟小于等于60分钟文案为：“1小时前”；大于60分钟小于等于24小时文案为“××小时前”；大于24小时小于等于48小时文案为：“昨天”；
     * 大于48小时小于等于72小时文案为：“前天”；大于72小时则统一格式：“年-月-日”
     */

    public static String getTime(long timeStamp) {

        String tag = "";
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        String paramTime = formater.format(timeStamp);

        long curTimeStamp = System.currentTimeMillis();
        long duration = Math.abs(curTimeStamp - timeStamp);

        if (paramTime.equals(getDayBeforeYesterday())) {
            // 前天
            tag = "前天";
        } else if (paramTime.equals(getYesterday())) {
            // 昨天
            tag = "昨天";
        } else if (paramTime.equals(getToday())) {
            // 今天
            if (duration < _1min) {
                tag = "刚刚";
            } else if (duration < _60min) {
                int time = (int) (duration / _1min);
                tag = time + "分钟前";
            } else if (duration > _30min && duration <= _60min) {
                tag = "1小时前";
            } else if (duration > _60min && duration <= _24h) {
                int time = (int) (duration / _1h);
                tag = time + "小时前";
            }
        } else {
            tag = getTimeFromStamp(timeStamp);
        }

        return tag;
    }

    /**
     * 根据生日（"1999-08-01"），返回出年龄
     */
    public static int birth2Age(String str) {
        if (!isDateStringFormat(str)) {
            return 0;
        }
        Date birthDay = onDateString2Date(str);
        //获取当前系统时间
        Calendar cal = Calendar.getInstance();
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        //将日期设置为出生日期
        cal.setTime(birthDay);
        //取出出生日期的年、月、日部分
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth;
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) {
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 判断时间格式是否为"1999-08-01"
     */
    public static boolean isDateStringFormat(String str) {
        //String str = "1999-08-01";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = formatter.parse(str);
            return str.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断时间格式是否为"1999年08月01日"
     */
    public static boolean isDateStringCnFormat(String str) {
        //String str = "1999-08-01";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        try {
            Date date = formatter.parse(str);
            return str.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * "1999-08-01"转换成Date格式的时间
     */
    public static Date onDateString2Date(String str) {
        if (!isDateStringFormat(str)) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * "yyyy年MM月dd日"转换成Date格式的时间
     */
    public static Date onDateCnString2Date(String str) {
        if (!isDateStringCnFormat(str)) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 1999-08-01"格式的时间转换成"1999年08月01日"
     */
    public static String onNormalDate2CnStringData(String str) {
        Date date = onDateString2Date(str);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        if (date != null) {
            return sdf.format(date);
        } else {
            return null;
        }
    }


    /**
     * 1999年08月01日"格式的时间转换成"1999-08-01"
     */
    public static String onCnStrDate2NormalStrDate(String str) {
        Date date = onDateCnString2Date(str);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if (date != null) {
            return sdf.format(date);
        } else {
            return null;
        }
    }

    /**
     * Date格式的时间转换成"1999-08-01"
     */
    public static String onDate2StringDate(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 判断是否为当天
     *
     * @param day1
     * @param day2
     * @return
     */
    public static final boolean isCurrentDay(long day1, long day2) {
        long dp = Math.abs(day2 - day1);
        if (dp < _1day && getTimeFromStamp(day1).equals(getTimeFromStamp(day2))) {
            return true;
        }
        return false;
    }

    /**
     * 用于判断时间为当天时间
     *
     * @param seperateTime
     * @return
     */
    public static final boolean isCurrentDay(long seperateTime) {
        String day = getTimeDay(seperateTime);
        String today = getToday();
        if (day.equals(today)) {
            return true;
        }
        return false;
    }

    /**
     * 获取某一天日期
     *
     * @param millis
     * @return 2014-09-22
     */
    public static String getTimeDay(long millis) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        String paramTime = formater.format(millis);
        return paramTime;
    }

    /**
     * 获取昨天 日期
     *
     * @return
     */
    private static String getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        String dateStr = formater.format(date);// 获取昨天日期
        return dateStr;
    }

    /**
     * 获取前天日期
     *
     * @return
     */
    private static String getDayBeforeYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        Date date = cal.getTime();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        String dateStr = formater.format(date);// 获取昨天日期
        return dateStr;
    }

    /**
     * 获取今天日期
     *
     * @return
     */
    public static String getToday() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        String dateStr = formater.format(date);// 获取今天日期
        return dateStr;
    }


    /**
     * 获取明天日期
     *
     * @return
     */
    public static String getTomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1); //添加一天
        Date date = cal.getTime();

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        return formater.format(date);
    }


    /**
     * 获取后天日期
     *
     * @return
     */
    public static String getDayAfterTomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 2); //增加两天
        Date date = cal.getTime();

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        return formater.format(date);
    }

    /**
     * 获取一年后的日期
     *
     * @return
     */
    public static String getDayAfterOneYear() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 365); //增加365天
        Date date = cal.getTime();

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        return formater.format(date);
    }

    public static int getMonth() {
        final Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getYear() {
        final Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public static int getYear(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return c.get(Calendar.YEAR);
    }

    public static int getDay() {
        final Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断两个时间戳是否是同一天
     */
    public static boolean isSameDay(long time1, long time2) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time1);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.setTimeInMillis(time2);
        return year == c.get(Calendar.YEAR)
                && month == c.get(Calendar.MONTH)
                && day == c.get(Calendar.DAY_OF_MONTH);
    }

    public static String getStringDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return getTimeFromStamp(c.getTime());
    }

    /**
     * 服务端时间格式化模板"yyyy/MM/dd HH:mm:ss"
     *
     * @return
     */
    public static String parserTime(String time) {
        if (time == null)
            return null;
        // 逻辑更好，更容易理解
        long begin = System.currentTimeMillis();// System.nanoTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
                Locale.CHINA);
        SimpleDateFormat targetSdf = new SimpleDateFormat("MM月dd日",
                Locale.CHINA);
        try {
            Date date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 方案二 性能好耗时小于1毫秒 但是耦合性高
        // long bg = System.currentTimeMillis();
        String[] times = time.split(" ");
        if (times != null) {
            String[] ts = times[0].split("/");
            if (ts != null && ts.length >= 3) {
                String s = ts[0] + "-" + ts[1] + "-" + ts[2];
                return s;
            }
        }
        return null;
    }

    /**
     * 0～1分钟：刚刚 1分钟～1小时：X分钟前（X表示分钟数） 1小时～当天结束：HH：MM（H表示小时，M表示分钟，发帖时间）
     * 当天结束～第2天内：昨天HH：MM 第2天后：X月X日 HH：MM
     */
    public static String convertTimeToText(long timeStamp) {
        String tag = "";
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        String paramTime = formater.format(timeStamp);

        long curTimeStamp = System.currentTimeMillis();
        long duration = Math.abs(curTimeStamp - timeStamp);

        if (paramTime.equals(getToday())) {
            if (duration <= _1min) {
                tag = "刚刚";
            } else if (duration <= _60min) {
                int time = (int) (duration / _1min);
                tag = time + "分钟前";
            } else {
                tag = getTimeHM(timeStamp / 1000);
            }
        } else if (paramTime.equals(getYesterday())) {
            if (duration <= _1min) {
                tag = "刚刚";
            } else if (duration <= _60min) {
                int time = (int) (duration / _1min);
                tag = time + "分钟前";
            } else {
                tag = "昨天 " + getTimeHM(timeStamp / 1000);
            }
        } else {
            tag = getTimeMD(timeStamp / 1000);
        }
        return tag;
    }

    /**
     * 帖子时间转换
     */
    public static String convertTimeToTextWithinTopi(long timeStamp) {
        String tag = "";
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        String paramTime = formater.format(timeStamp);
        long curTimeStamp = System.currentTimeMillis();
        long duration = Math.abs(curTimeStamp - timeStamp);
        if (paramTime.equals(getDayBeforeYesterday())) {
            // 前天
            tag = "前天";
        } else if (paramTime.equals(getYesterday())) {
            // 昨天
            tag = "昨天";
        } else if (paramTime.equals(getToday())) {
            // 今天
            if (duration < _1min) {
                tag = "刚刚";
            } else if (duration < _60min) {
                int time = (int) (duration / _1min);
                tag = time + "分钟前";
            } else if (duration > _30min && duration <= _60min) {
                tag = "1小时前";
            } else if (duration > _60min && duration <= _24h) {
                int time = (int) (duration / _1h);
                tag = time + "小时前";
            }
        } else {
            tag = getTimeFromStamp(timeStamp);
        }

        return tag;
    }

    /**
     * "yyyy/MM/dd HH:mm:ss"
     * <p/>
     * 分段 ：当时 (1小时内 多少分钟前)
     * <p/>
     * 当天 (HH:mm)
     * <p/>
     * 当年 (MM-dd HH:mm)
     * <p/>
     * 年前 (yyyy-MM-dd HH:mm)
     *
     * @param timeStamp
     * @return
     */
    public static String convertTime(long timeStamp) {
        String tag = "";
        long curTimeStamp = System.currentTimeMillis(); // 系统时间
        long duration = Math.abs(curTimeStamp - timeStamp); // 时间间隔
        if (duration < _1min) { // 一分钟内
            tag = "刚刚";
        } else if (duration < _1h) { // 一小时内
            tag = (int) (duration / _1min) + "分钟前";
        } else if (duration < _1day) { // 一天内
            long todayBegin = getTodayBegin();
            if (timeStamp > todayBegin) { // 当天内
                tag = getHourMinTime(timeStamp);
            } else { // 当天前
                tag = "昨天 " + getHourMinTime(timeStamp);
            }
//			LogUtil.i(TAG, getTimeFromStampWithHour(todayBegin));
        } else if (duration < _1year) { // 一年内
            if (timeStamp > getYearBegin()) { // 当年内
                tag = getDateMonthDate(timeStamp);
            } else { // 当年之前的
                tag = getTimeFromStampWithHour(timeStamp);
            }
//			LogUtil.i(TAG, getTimeFromStampWithHour(getYearBegin()));
        } else {
            tag = getTimeFromStampWithHour(timeStamp);
        }
        return tag;
    }

    public static long getTodayBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);
        return cal.getTimeInMillis();

    }

    public static long getYearBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getYear());
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);
        return cal.getTimeInMillis();
    }

    public static String getYearBeginOfStr() {
        return getTimeWithFormat(getYearBegin(), "yyyy-MM-dd");
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static int dayForWeek(String pTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dayForWeek;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    private static String intWeek2String(int dayForWeek) {
        String daForWeek = "";
        switch (dayForWeek) {
            case 1:
                daForWeek = "星期一";
                break;
            case 2:
                daForWeek = "星期二";
                break;
            case 3:
                daForWeek = "星期三";
                break;
            case 4:
                daForWeek = "星期四";
                break;
            case 5:
                daForWeek = "星期五";
                break;
            case 6:
                daForWeek = "星期六";
                break;
            case 7:
                daForWeek = "星期天";
                break;
            default:
                break;
        }
        return daForWeek;
    }

    public static String dayForWeekCustom(String pTtime) {
        if (getYesterday().equals(pTtime))
            return "昨天";
        if (getToday().equals(pTtime))
            return "今天";
        if (getTomorrow().equals(pTtime))
            return "明天";
        if (getDayAfterTomorrow().equals(pTtime))
            return "后天";
        return intWeek2String(dayForWeek(pTtime));
    }

    /**
     * 把格式"1999-08-01"或"1999年08月01日"转换成{1999,08,01}；
     */
    public static Calendar strTime2Date(@NonNull String time) {
        String timeStr = "";
        if (isDateStringFormat(time)) {
            timeStr = time;
        } else if (isDateStringCnFormat(time)) {
            timeStr = onCnStrDate2NormalStrDate(time);
        }
        if (!TextUtils.isEmpty(timeStr)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(onDateString2Date(timeStr));
            return calendar;
        }
        return null;
    }

    //通过年和月获得这个月份的天数
    public static int getMaxDayByYearMonth(int year, int month) {
        int maxDay = 0;
        int day = 1;
        /**
         * 与其他语言环境敏感类一样，Calendar 提供了一个类方法 getInstance，
         * 以获得此类型的一个通用的对象。Calendar 的 getInstance 方法返回一
         * 个 Calendar 对象，其日历字段已由当前日期和时间初始化：
         */
        Calendar calendar = Calendar.getInstance();
        /**
         * 实例化日历各个字段,这里的day为实例化使用
         */
        calendar.set(year, month - 1, day);
        /**
         * Calendar.Date:表示一个月中的某天
         * calendar.getActualMaximum(int field):返回指定日历字段可能拥有的最大值
         */
        maxDay = calendar.getActualMaximum(Calendar.DATE);
        return maxDay;
    }

    /**
     * 通过year、month、day和format获取相应str类型的时间
     */
    public static String getStrDateByIntArrayWithFormat(int year, int month, int day, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date date = calendar.getTime();

        SimpleDateFormat formater = new SimpleDateFormat(format, Locale.CHINA);
        return formater.format(date);
    }
}
