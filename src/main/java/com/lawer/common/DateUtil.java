package com.lawer.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: DateUtil
 * @Description:日期操作工具类
 */
public class DateUtil {
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    /**
     * @Title: getCurrentTimestamp
     * @Description: 获取Timestamp类型的当前时间
     * @return
     * @author nagang
     * 创建时间： 2019年3月7日 上午11:29:43
     */
    public static Timestamp getCurrentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * @Title: dateAdd
     * @Description: 对日期添加天数
     * @param date
     * @param increament
     * @return
     * @author nagang
     * 创建时间： 2019年3月7日 上午11:30:09
     */
    public static Date dateAdd(Date date,int increament){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, increament);
        return c.getTime();
    }

    /**
     * @Title: DateToString
     * @Description:格式换时间为字符串 yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     * @author lp
     * 创建时间： 2019年4月13日 上午11:40:05
     */
    public static String DateToString(Date date) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            strDate = sdf.format(date);
        }
        return strDate;
    }

    /**
     * 字符串转化成日期
     * @param dateString
     * @return
     */
    public static Date StringToDate(String dateString) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (dateString != null) {
            try {
                date = sdf.parse(dateString);
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        }
        return date;
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String getToday(){
        return DateToString(new Date());
    }
    /**
     * 获取当前年
     */
    public static class NowYear {
        public static final int getNowYear() {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            return year;
        }
        public static final int getNowMonth() {
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH);
            return month+1;
        }

        /**
         *
         * <获取当前自然日及前后N年>
         * @param n
         * @see [Classes, Class#method, Class#members]
         */
        public static Integer[] getFromToYearByNowYear(int n) {
            if(n>=0) {
                int nowYear = NowYear.getNowYear();
                int length=n*2+1;
                int sub=n;
                int add=n;
                Integer[] years=new Integer[length];
                for (int i = 0; i < years.length; i++)
                {
                    if(i<n) {
                        years[i]=nowYear+add;
                        add--;
                    }
                    if(i==n) {
                        years[i]=nowYear;
                    }
                    if(i>n) {
                        years[i]=nowYear-sub;
                        sub--;
                    }
                }
                return years;
            }
            return null;
        }
    }

    /**
     * @Title: formatHourMinSecond
     * @Description:格式化时间时分秒
     * @param date
     * @return
     * @author lp
     * 创建时间： 2019年4月13日 上午11:40:05
     */
    public static Date formatHourMinSecond(Date date, int hour, int min, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, second);
        //cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }

    /**
     * 计算两个日期的时间差
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return
     * @throws ParseException
     */
    public static Long daysBetween(Date beginDate,Date endDate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        beginDate=sdf.parse(sdf.format(beginDate));
        endDate=sdf.parse(sdf.format(endDate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long betwiinDays=(time2-time1)/(1000*3600*24);
        return betwiinDays;
    }



}

