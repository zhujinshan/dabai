package com.dabai.proxy.utils;

import org.apache.http.client.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Create by Ranzd on 2021-06-05 11:13
 *
 * @author ranzhendong@maoyan.com
 */
public class TimeUtil {
    public static String timeFormat(Date date, FormatPatternsEnum patternsEnum) {
        SimpleDateFormat sdf = new SimpleDateFormat(patternsEnum.value);
        return sdf.format(date);
    }

    public static Date parseDate(String timeStr, FormatPatternsEnum patternsEnum) {
        return DateUtils.parseDate(timeStr, new String[]{patternsEnum.value});
    }

    /**
     * 获取当前时间（精确到秒）的字符串
     * @return 返回时间字符串yyyy-MM-dd HH:mm:ss
     */
    public static String getTheTimeInSeconds() {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return time.format(date);
    }

    /**
     * 获取月份的第一天
     *
     * @param date
     * @return
     */
    public static Date getDateForMonth(Date date) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
        calendar.set(GregorianCalendar.MINUTE, 0);
        calendar.set(GregorianCalendar.SECOND, 0);
        calendar.set(GregorianCalendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public enum FormatPatternsEnum {
        TIMESTAMP("yyyyMMddHHmmssSSS"),
        YYYYMMDD("yyyyMMdd");

        private String value;

        FormatPatternsEnum(String value) {
            this.value = value;
        }
    }
    public static Date longStampForDate(long timestamp){
        return new Date(timestamp);
    }

    public static Date stringForDate(String time){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间的格式
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取当天0点时间
     * @return
     */
    public static Date getCurrentDay0h(){
        Date now = new Date(); //获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowStr = sdf.format(now)+" 00:00:00"; //得到今天凌晨时间
        return stringForDate(nowStr);
    }

    public static void main(String[] args) {
//        System.out.println(timeFormat(getDateForMonth(new Date()),FormatPatternsEnum.TIMESTAMP));
        System.out.println(getCurrentDay0h());
    }
}
