package date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * jichao
 */
public class DateUtil {
	public static String UN_LIMIT_TIME = "2035-01-01 00:00:00";

	public static String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy年MM月dd日 hh时mm分ss秒 12小时制
	 */
	public static String FORMAT_1="yyyy年MM月dd日 hh时mm分ss秒";//12小时制
	/**
	 * yyyy-MM-dd HH:mm:ss" 12小时制
	 */
	public static String FORMAT_2="yyyy-MM-dd hh:mm:ss";//12小时制
	/**
	 * yyyy-MM-dd HH:mm:ss" 24小时制
	 */
	public static String FORMAT_3="yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyyMMddHHmmss" 24小时制
	 */
	public static String FORMAT_9="yyyyMMddHHmmss";
	/**
	 * yyyyMMddHHmmss" 24小时制
	 */
	public static String FORMAT_11="yyyyMMddHHmmss";
	/**
	 * yyyy-MM-dd
	 */
	public static String FORMAT_4="yyyy-MM-dd";
	/**
	 * yyyy/MM/dd
	 */
	public static String FORMAT_5="yyyy/MM/dd";
	/**
	 * yyyy-MM
	 */
	public static String FORMAT_6="yyyy-MM";
	/**
	 * hh:mm:ss 12小时制
	 */
	public static String FORMAT_7="hh:mm:ss";//12小时制
	/**
	 * HH:mm:ss 24小时制
	 */
	public static String FORMAT_8="HH:mm:ss";//24小时制
	/**
	 * yyyyMMdd
	 */
	public static String FORMAT_10 = "yyyyMMdd";

	/**
     * yyyy年MM月dd日
     */
    public static String FORMAT_12="yyyy年MM月dd日";



	/**
	 * 	/**
	 * MMdd
	 */
	public static String FORMAT_13 = "M月dd日";

	public static String FORMAT_15 = "MM月dd日HH时";


	/**
	 *
	 */
	public static String FORMAT_14 = "M月dd日 HH:mm";


	/**字符串转换为时间类型
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date parseDate(String dateStr, String format) {
		Date date = null;
		DateFormat dateFormat = null;
		try {
			dateFormat = new SimpleDateFormat(format);
			date = dateFormat.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**和当前时间比较大小 (当前时间大于目标时间-1 等于0 小于1)
	 */
	public static Integer compareNowDate(Date compareDate) {
		Integer flag = 0;
		Date now = new Date();
		if(compareDate.getTime() < now.getTime()){
			flag = -1;
		}else if(compareDate.getTime() > now.getTime()){
			flag = 1;
		}else{
			flag = 0;
		}
		return flag;
	}

	/**
	 * 将时间转换为指定格式的字符串
	 * @param
	 * @param format
	 * @return
	 */
	public static String parseString(Object obj, String format) {
		if(obj == null) return "";
		Date date = null;
		if(obj instanceof Date) {
			date = (Date)obj;
		} else {
			return obj.toString();
		}
		return parseString(date, format);
	}
	/**
	 * 将时间转换为指定格式的字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String parseString(Date date, String format) {
		String dateStr = null;
		DateFormat dateFormat = null;
		if(date == null) return "";
		if (format == null || format=="") {
			format =FORMAT_3;
		}
		dateFormat = new SimpleDateFormat(format);
		dateStr = dateFormat.format(date);
		return dateStr;
	}

	/**获取指定时间的年份
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**获取指定时间的月份
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定时间的日期
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**获取指定时间的小时
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**获取指定时间的分钟
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**获取指定时间的秒
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**获取指定时间的毫秒值
	 * @param date
	 * @return
	 */
	public static long getMillis(Date date) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**获取指定时间多少天以后的日期
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDate(Date date, int day) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	/**获取指定时间多少小时以后的日期
	 * @return
	 */
	public static Date addDateHour(Date date, int hour) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		long millis = getMillis(date) + ((long) hour)  * 3600 * 1000;
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	/**获取指定时间多少分钟以后的日期
	 * @return
	 */
	public static Date addDateMin(Date date, int minutes) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		long millis = getMillis(date) + ((long) minutes)  * 60 * 1000;
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	/**获取指定时间多少分钟以后的日期
	 * @return
	 */
	public static Date addDateMin(Date date, long minutes) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		long millis = getMillis(date) + (minutes)  * 60 * 1000;
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}


	/**获取指定时间多少天以前的日期
     * @return
     */
    public static Date beforeDate(Date date, int day) {
        Calendar calendar = null;
        calendar = Calendar.getInstance();
        long millis = getMillis(date) - ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

	/**判断时间是在一段时间之内
	 *
	 */
	public static boolean dateBetweenRange(Date oriDate , Date startDate, Date endDate) {
		boolean isCom = false;
		if(oriDate.getTime() >= startDate.getTime() && oriDate.getTime() <= endDate.getTime()){
			isCom = true;
		}
		return isCom;
	}


	/**
	 * 获取两个时间的间隔天数
	 * @return
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) (((date.getTime() - date1.getTime()) / (24 * 3600 * 1000)));
	}



	/**
	 * 获取两个时间的间隔小时数
	 * @return
	 */
	public static int diffHour(Date date, Date date1) {
		long diff = date.getTime() - date1.getTime();
		int time = 3600 * 1000; // 	一个小时有多少秒
		return (int) (diff / time);
	}

	/**
	 * 获取两个时间的间隔分钟数
	 * @return
	 */
	public static int diffMin(Date date, Date date1) {
		long diff = date.getTime() - date1.getTime();
		int time = 60 * 1000; // 	一个小时有多少分
		return (int) (diff / time);
	}

	public static void main(String[] args) {
		Date now = new Date();
		Date now2 = DateUtil.addDateHour(now,2);
		System.out.println(DateUtil.diffMin(now2,now));

		System.out.println(DateUtil.parseString(now,FORMAT_15));
	}

	/**
	 * 获取两个时间的间隔秒数
	 * @return
	 */
	public static int diffSecond(Date date, Date date1) {
		long diff = date.getTime() - date1.getTime();
		int time = 1000;
		return (int) (diff / time);
	}

	/**获取某月的第一天
	 * @param strdate (yyyy-MM)
	 * @return yyyy-MM-dd
	 */
	public static String getMonthBegin(String strdate) {
		Date date = null;
		date = parseDate(strdate, DateUtil.FORMAT_6);
		return parseString(date, DateUtil.FORMAT_6) + "-01";
	}

	/**获取某月的最后一天
	 * @param strdate (yyyy-MM)
	 * @return yyyy-MM-dd
	 */
	public static String getMonthEnd(String strdate) {
		Calendar calendar = null;
		Date date = null;
		date = parseDate(getMonthBegin(strdate), DateUtil.FORMAT_6);
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return parseString(calendar.getTime(), DateUtil.FORMAT_3);
	}

	/**
	 * 获取当前时间的时间戳,从最后位置开始截取对应以供形成订单号，和订单明细的主键
	 * @return
	 */
	public static long getTimeSeconds(int index){
		long time_seconds =0l;
		Date date = new Date();
		long long_time=date.getTime();
		int len = (long_time+"").length();
		time_seconds = Long.parseLong(String.valueOf(long_time).substring(len-index, len));
		return time_seconds;
	}

	/**
	 * 取当前时间
	 * @return
	 */
	public static Date getDatetime() {
		Calendar c = Calendar.getInstance();//获得系统当前日期
		return c.getTime();
	}
	/**
	 * 从前到后依次排序
	 * @param dates
	 * @return
	 */
	public static Date[] buffSort(Date[] dates) {
        Date temp;
        for(int i=0;i<dates.length;i++){//趟数
            for(int j=0;j<dates.length-i-1;j++){//比较次数
                if (dates[j].after(dates[j + 1])) {
                    temp = dates[j];
                    dates[j] = dates[j + 1];
                    dates[j + 1] = temp;
                }
            }
        }
        return dates;
    }

	/**
	 * 格式化string类型的时间
	 * yyyy年MM月dd日
	 * @return string
	 */
	public static String formatStringDate(String date){
		String formatDate = "";
		String[] dateStrings = date.substring(0, 10).split("\\-");
		formatDate = dateStrings[0]+"年"+dateStrings[1]+"月"+dateStrings[2]+"日";
		return formatDate;
	}
	/**
	 * 日期格式化：yyyy-MM-dd HH:mm:ss
	 */
	public static String dateLFormat(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DEFAULT);
		String str = format.format(date);
		return str;
	}
}
