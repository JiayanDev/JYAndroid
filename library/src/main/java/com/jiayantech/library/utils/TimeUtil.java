package com.jiayantech.library.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * java_时间戳与Date_相互转化
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {
    public static long SERVER_TIME_OFFSET = 0;
    private SimpleDateFormat format = new SimpleDateFormat();

    /**
     * 时间戳转化为Sting或Date
     */
    public static String stamp2Date(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(time);
        return d;
    }

    public static String stamp2SimpleDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        String d = format.format(time);
        return d;
    }

    /**
     * @param time
     * @return "HH:mm"
     */
    public static String stamp2HourMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String d = format.format(time);
        return d;
    }

    public static String stamp2YearMonthDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(time);
        return result;
    }

    public static String stamp2MonthDay(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(time);
        return result;
    }

    /**
     * 时间戳转化为Sting或Date
     *
     * @"yyyy-MM-dd HH:mm:ss"
     */
    public static String stamp2Time(long timeMillisecond) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(timeMillisecond);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return null;
        }
        System.out.println("Format To String(Date):" + d);
        System.out.println("Format To Date:" + date);
        return date.toString();
    }

    /**
     * 将时间戳转为字符串 @ "yyyy-MM-dd HH:mm:ss"
     */
    public static String getStrTime(double timeSecond) {
        return getStrTime((long) (timeSecond * 1000));
    }// 将时间戳转为字符串

    /**
     * 将时间戳转为字符串
     *
     * @"yyyy-MM-dd HH:mm:ss"
     */
    public static String getStrDateBySecond(long timeSecond) {
        return new SimpleDateFormat("yyyy.MM.dd").format(new Date(timeSecond * 1000));
    }

    /**
     * 将时间戳转为字符串
     *
     * @"yyyy-MM-dd HH:mm:ss"
     */
    public static String getStrTimeBySecond(long timeSecond) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(timeSecond * 1000));
    }

    /**
     * 将时间戳转为字符串
     *
     * @"yyyy-MM-dd HH:mm:ss"
     */
    public static String getStrTime(long timeMillisecond) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeMillisecond));
    }

    /**
     * 将时间戳转为字符串
     *
     * @"yyyy-MM-dd_HH.mm.ss"
     */
    public static String getStrTimeToFileName(long timeMillisecond) {
        return new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date(timeMillisecond));
    }

    public static String getStrTimeDayToFileName(long timeMillisecond) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(timeMillisecond));
    }

    /**
     * 将时间戳转为字符串
     */
    public static String getStrTime1(double timeSecond) {
        return stamp2Date((long) (timeSecond * 1000));
    }

    /**
     * 时间戳转化为Sting或Date
     */

    public static long date2Stamp(String timeMillisecond) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // String time = "1970-01-06 11:45:55";
        Date date = null;
        try {
            date = format.parse(timeMillisecond);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return 0;
        }
        System.out.print("Format To times:" + date.getTime());
        return date.getTime();
    }

    private final static int TIME_ZONE_OFFSET = 60 * 60 * 1000; // 一个时区的毫秒
    private final static int TIME_MICRO_OFFSET = -4 * 60 * 1000; // 微调的4分钟

    // private final static int TIME_MICRO_OFFSET = -8 * 60 * 1000; // 微调的 分钟

    /**
     * 获取用于显示的时间文本
     */
    public static String getTimeText(double timeSecond) {
        return getTimeText((long) (timeSecond * 1000));
    }

    /**
     * 获取用于显示的时间文本
     */
    public static String getTimeText(long timeMillisecond) {
        String timeText = getTimeText(getTimeDifference(timeMillisecond, true));
        if (timeText != null) {
            return timeText;
        }
        return timeMillisecond + "";
    }

    public static String getFullTimeText(long timeMillisecond) {
        String timeText = getTimeText(getTimeDifference(timeMillisecond, true));
        if (timeText != null) {
            return timeText;
        }
        return timeMillisecond + "";
    }

    /**
     * 获取用于显示的时间文本
     */
    public static String getTimeText(String time) {
        String timeText = getTimeText(getTimeDifference(time));
        if (timeText != null) {
            return timeText;
        }
        return time;
    }

    /**
     * 获取用于显示的时间文本
     */
    public static String getTimeText(Date date) {
        String timeText = getTimeText(getTimeDifference(date));
        if (timeText != null) {
            return timeText;
        }
        return date.toLocaleString();
    }

    /**
     * 获取用于显示的时间文本
     */
    public static String getTimeText(TextTime textTime) {
        if (textTime != null && textTime.day < 1) {
            if (textTime.hour >= 1) {
                return textTime.hour + "小时前";
            } else if (textTime.minute >= 1) {
                return textTime.minute + "分钟前";
            } else {
                return "刚刚";
            }
        } else if (textTime.day == 1) {
            return "昨天 " + stamp2HourMin(textTime.timeMillisecond);
            // } else if (textTime.day == 2) {
            // return "前天 " + stamp2HourMin(textTime.timeMillisecond);
        } else {
            return stamp2SimpleDate(textTime.timeMillisecond);
        }
    }

    /**
     * 获取与当前时间相比的时间差
     */
    public static TextTime getTimeDifference(String time) {
        TextTime textTime = null;
        try {
            textTime = getTimeDifference(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).getTime(), true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return textTime;
    }

    /**
     * 获取与当前时间相比的时间差
     */
    public static TextTime getTimeDifference(Date date) {
        return getTimeDifference(date.getTime(), false);
    }

    static long DAYMillis = 24 * 60 * 60 * 1000;

    /**
     * 获取与当前时间相比的时间差
     */
    public static TextTime getTimeDifference(long timeMillisecond, boolean toMicroOffset) {
        // long microOffset = 0;
        // if (toMicroOffset) {
        // microOffset = TIME_MICRO_OFFSET;
        // }
        // Date date = new Date();
        // long timeDifference = date.getTime() + ((8 + date.getTimezoneOffset()
        // / 60) * TIME_ZONE_OFFSET + microOffset) - timeMillisecond;

        // long curMillis = date.getTime();

        // String timeMillisecondDate = stamp2SimpleDate(timeMillisecond);

        long curMillis = currentTimeMillis();
        // String curMillisDate = stamp2SimpleDate(curMillis);

        long timeDifference = curMillis - timeMillisecond;

        // long testTimeDifferenceMin = timeDifference / (60 * 1000);

        TextTime textTime = new TextTime();
        textTime.timeMillisecond = timeMillisecond;
        textTime.day = timeDifference / DAYMillis;

        long restTime = timeDifference % DAYMillis;
        // long testRestTimeMin = restTime / (60 * 1000);

        long todayMillis = getTodayMillis();

        // String todayMillisDate = stamp2SimpleDate(todayMillis);

        long curTodayDifference = curMillis - todayMillis;
        // long testcurTodayDifferenceMin = curTodayDifference / (60 * 1000);
        if (restTime > curTodayDifference) {
            textTime.day += 1;
        }
        if (textTime.day > 0) {
            return textTime;
        }

        textTime.hour = (timeDifference / (60 * 60 * 1000) - textTime.day * 24);
        textTime.minute = ((timeDifference / (60 * 1000)) - textTime.day * 24 * 60 - textTime.hour * 60);
        textTime.second = (timeDifference / 1000 - textTime.day * 24 * 60 * 60 - textTime.hour * 60 * 60 - textTime.minute * 60);
        return textTime;
    }

    /**
     * 获取与当前正真的日期
     */
    public static Date getCurDate() {
        Date date = new Date();
        date.setTime(System.currentTimeMillis() + (8 + date.getTimezoneOffset() / 60) * TIME_ZONE_OFFSET);
        return date;
    }

    /**
     * 显示时间用的时间数据类
     */
    public static class TextTime {
        public long timeMillisecond;
        public long day;
        public long hour;
        public long minute;
        public long second;

        @Override
        public String toString() {
            return "TextTime{" + "day=" + day + ", hour=" + hour + ", minute=" + minute + ", second=" + second + '}';
        }
    }

    public static long getTodayMillis() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // System.out.println(cal.getTimeInMillis());
        return cal.getTimeInMillis();
    }

    /**
     * 获取当前的时间戳（与服务器一致的）
     */
    public static double getCurTimestamp() {
        return currentTimeMillis() / 1000.0;
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis() - SERVER_TIME_OFFSET;
    }

    public static long toTimeMillis(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        long timeInMillis = calendar.getTimeInMillis();
        return timeInMillis;
    }

    /**
     * 根据出生日期计算年龄
     *
     * @param birthDay
     * @return 未来日期返回0
     * @throws Exception
     */
    public static int getAge(Date birthDay) throws Exception {

        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            return 0;
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }

        return age;
    }
}
