package utils;


import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DateTimeHelper {

  private static Logger logger = LoggerFactory.getLogger(DateTimeHelper.class);

  private static String DAY_FORMAT = "yyyy-MM-dd";
  private static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  private static String DT_INT_FORMAT = "yyyyMMdd";
  private static String DT_LONG_FORMAT = "yyyyMMddHHmmss";
  private static int MAX_DATE_INTERVAL = 3 * 365;

  private static DateTimeFormatter dayFormater = DateTimeFormat.forPattern(DAY_FORMAT);
  private static DateTimeFormatter dayToIntFormater = DateTimeFormat.forPattern(DT_INT_FORMAT);
  private static DateTimeFormatter timeFormater = DateTimeFormat.forPattern(TIME_FORMAT);
  private static DateTimeFormatter dtLongFormater = DateTimeFormat.forPattern(DT_LONG_FORMAT);

  private static final Random rand = new Random(System.currentTimeMillis());

  public enum TimeFormatter {
    DAY {
      @Override
      public String formatInt2Str(int time) {
        String src = String.valueOf(time);
        return src.substring(0, 4) + "-" + src.substring(4, 6) + "-" + src.substring(6);
      }

      @Override
      public int formatDate2Int(Date time) {
        return Integer.parseInt(new DateTime(time).toString("yyyyMMdd"));
      }

    },
    MONTH {
      @Override
      public String formatInt2Str(int time) {
        String src = String.valueOf(time);
        return src.substring(0, 4) + "-" + src.substring(4, 6);
      }

      @Override
      public int formatDate2Int(Date time) {
        return Integer.parseInt(new DateTime(time).toString("yyyyMM"));
      }

    };

    public abstract String formatInt2Str(int time);

    public abstract int formatDate2Int(Date time);

    public int formatStr2Int(String time) {
      return Integer.parseInt(time.replaceAll("-", ""));
    }
  }

  public static Date nextYear(Date curDate) {
    DateTime dateTime = new DateTime(curDate);
    dateTime = dateTime.plusYears(1);
    return dateTime.toDate();
  }

  public static Date nextMonth(Date curDate) {
    DateTime dateTime = new DateTime(curDate);
    dateTime = dateTime.plusMonths(1);
    return dateTime.toDate();
  }

  public static Date nextDate(Date curDate) {
    return getSomeDate(curDate, 1);
  }

  public static Date prevWeek(Date curDate) {
    DateTime dateTime = new DateTime();
    dateTime = dateTime.plusWeeks(-1);
    return dateTime.toDate();
  }

  public static Date prevDate(Date curDate) {
    return getSomeDate(curDate, -1);
  }
  
  public static Date prevDays(Date curDate, int days) {
    return getSomeDate(curDate, -1 * days);
  }

  public static Date getSomeDate(Date curDate, Integer days) {
    DateTime dateTime = new DateTime(curDate);
    dateTime = dateTime.plusDays(days);
    return dateTime.toDate();
  }

  public static Date getSomeHour(Date curDate, Integer hours) {
    DateTime dateTime = new DateTime(curDate);
    dateTime = dateTime.plusHours(hours);
    return dateTime.toDate();
  }

  public static Date getSomeMonth(Date curDate, Integer months) {
    DateTime dateTime = new DateTime(curDate);
    dateTime = dateTime.plusMonths(months);
    return dateTime.toDate();
  }

  public static Date monthFirstDate(Date curDate) {
    DateTime dateTime = new DateTime(curDate);
    dateTime = dateTime.dayOfMonth().withMinimumValue();
    return dateTime.toDate();
  }

  public static Date monthFinalDate(Date curDate) {
    DateTime dateTime = new DateTime(curDate);
    dateTime = dateTime.dayOfMonth().withMaximumValue();
    return dateTime.toDate();
  }

  public static String timeFormatDateToString(Date date) {
    if (date == null) {
      logger.warn("date is null");
      return null;
    }
    return new DateTime(date).toString(TIME_FORMAT);
  }

  public static String dayFormatDateToString(Date date) {
    if (date == null) {
      logger.warn("date is null!");
      return null;
    }
    return new DateTime(date).toString(DAY_FORMAT);
  }

  public static String dtLongFormatDateToString(Date date) {
    if (date == null) {
      logger.warn("date is null!");
      return null;
    }
    return new DateTime(date).toString(DT_LONG_FORMAT);
  }

  public static Date timeFormatStringToDate(String str) {
    return timeFormater.parseDateTime(str).toDate();
  }

  public static Date dayFormatStringToDate(String str) {
    return dayFormater.parseDateTime(str).toDate();
  }

  /**
   * 将日期字符串转为int形式，eg. 2015-05-05 -> 20150505
   * 
   * @param datestr
   * @return created by xuzhw on 2015年6月15日 下午5:42:20
   */
  public static int dayFormatStringToInt(String datestr) {
    DateTime dt = dayFormater.parseDateTime(datestr);
    String yyyyMMdd = dt.toString(dayToIntFormater);
    return Integer.valueOf(yyyyMMdd);
  }
  
  /**
   * 将日期字符串转为int形式，eg. 2015-05-05 -> 20150505
   * 
   * @param datestr
   * @param AddDay 增加天数
   * @return int
   * created by Tonglei on 2015年11月5日 下午7:55:27
   */
  public static int dayFormatStringToInt(String datestr, int AddDay) {
    if(AddDay == 0) {
      return dayFormatStringToInt(datestr);
    }
    DateTime dt = dayFormater.parseDateTime(datestr);
    dt = dt.plusDays(AddDay);
    String yyyyMMdd = dt.toString(dayToIntFormater);
    return Integer.valueOf(yyyyMMdd);
  }

  /**
   * 将int形式的日期转为日期字符串，eg. 20150505 -> 2015-05-05
   * 
   * @param dateint
   * @return created by xuzhw on 2015年6月15日 下午5:42:55
   */
  public static String dayFormatIntToString(int dateint) {
    String yyyyMMdd = String.valueOf(dateint);
    DateTime dt = dayToIntFormater.parseDateTime(yyyyMMdd);
    return dayFormater.print(dt);
  }

  /**
   * 将int形式的日期转为时间戳字符串，eg. 20150505 -> 2015-05-05 00:00:00
   * 
   * @param dateint
   * @return created by xuzhw on 2015年6月15日 下午5:42:55
   */
  public static String timestampFormatIntToString(int dateint) {
    String yyyyMMdd = String.valueOf(dateint);
    DateTime dt = dayToIntFormater.parseDateTime(yyyyMMdd);
    return timeFormater.print(dt);
  }

  public static long timeFormatIntToString(String dateStr) {
    DateTime dt = timeFormater.parseDateTime(dateStr);
    String yyyyMMddHHmmss = dt.toString();
    return Long.valueOf(yyyyMMddHHmmss);
  }


  /**
   * 将Date类型日期转为int形式
   * 
   * @param date
   * @return created by Luwenqing on 2015年6月16日 下午3:39:26
   */
  public static int dayFormatDateToInt(Date date) {
    String dateStr = dayFormatDateToString(date);
    return dayFormatStringToInt(dateStr);
  }


  /**
   * 将int类型转为Date形式
   * 
   * @param dateint
   * @return created by Luwenqing on 2015年6月16日 下午3:42:20
   */
  public static Date dayFormatIntToDate(int dateint) {
    String dateStr = dayFormatIntToString(dateint);
    return DateTimeHelper.dayFormatStringToDate(dateStr);
  }


  public static Date dtLongFormatStringToDate(String str) {
    return dtLongFormater.parseDateTime(str).toDate();
  }

  // 传入自定义格式日期类型，返回jdk Date
  public static Date fromStr(String date, String format) {
    DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
    return fmt.parseDateTime(date).toDate();
  }

  public static Date dayFormat(Date date) {
    String dayStr = dayFormatDateToString(date);
    return dayFormatStringToDate(dayStr);
  }

  public static DateTime withTimeEndAtDay(DateTime dateTime) {
    return dateTime.withTime(23, 59, 59, 999);
  }

  public static Date withTimeAtEndOfDay(Date date) {
    return new DateTime(date).withTime(23, 59, 59, 999).toDate();
  }

  public static Date withTimeAtStartfDay(Date date) {
    return new DateTime(date).withTime(0, 0, 0, 0).toDate();
  }

  public static Date withTimeAtEndOfWeek(Date date) {
    DateTime dateTime = new DateTime(date);
    dateTime = dateTime.withDayOfWeek(DateTimeConstants.SUNDAY);
    return withTimeAtEndOfDay(dateTime.toDate());
  }

  public static final boolean isEndOfWeek(Date date) {
    DateTime dateTime = new DateTime(date);
    return dateTime.dayOfWeek().get() == DateTimeConstants.SUNDAY;
  }


  public static final boolean isEndOfMonth(Date date) {
    DateTime dateTime = new DateTime(date);
    int lastDay = dateTime.dayOfMonth().getMaximumValue();
    return lastDay == dateTime.dayOfMonth().get();
  }

  public static int getDateRange(Date d1, Date d2) {
    DateTime dateTime1 = new DateTime(d1);
    DateTime dateTime2 = new DateTime(d2);
    Period p = new Period(dateTime1, dateTime2, PeriodType.days());
    return p.getDays();
  }

  public static int getDateRange(int d1, int d2) {
    Date date1 = dayFormatIntToDate(d1);
    Date date2 = dayFormatIntToDate(d2);
    return getDateRange(date1, date2);
  }

  public static Integer[] getDateInterval(int d1, int d2) {
    Date startDate = dayFormatIntToDate(d1);
    Date endDate = dayFormatIntToDate(d2);
    String[] dateStrs = getDateInterval(startDate, endDate);
    List<Integer> dates = new ArrayList<Integer>();
    for (String dateStr : dateStrs) {
      Integer date = dayFormatStringToInt(dateStr);
      dates.add(date);
    }
    return dates.toArray(new Integer[dates.size()]);
  }

  public static String[] getDateInterval(Date startDate, Date endDate) {
    DateTime sDate = new DateTime(startDate).withTimeAtStartOfDay();
    DateTime eDate = withTimeEndAtDay(new DateTime(endDate));

    if (eDate.isBefore(sDate)) {
      logger.error("end date MUST AFTER than start date, startDate: {}, endDate: {}", startDate,
          endDate);
      return null;
    }
    if (Days.daysBetween(sDate, eDate).getDays() > MAX_DATE_INTERVAL) {
      logger.error("date range to large. MUST LESS than {}", MAX_DATE_INTERVAL);
      return null;
    }

    List<String> result = new LinkedList<>();
    while (sDate.isBefore(eDate)) {
      result.add(sDate.toString(DAY_FORMAT));
      sDate = sDate.plusDays(1);
    }
    String[] returnValue = new String[result.size()];
    result.toArray(returnValue);
    return returnValue;
  }


  public static String[] getReverseDateInterval(Date startDate, Date endDate) {
    DateTime sDate = new DateTime(startDate).withTimeAtStartOfDay();
    DateTime eDate = withTimeEndAtDay(new DateTime(endDate));

    if (eDate.isBefore(sDate)) {
      logger.error("end date MUST AFTER than start date, startDate: {}, endDate: {}", startDate,
          endDate);
      return null;
    }
    if (Days.daysBetween(sDate, eDate).getDays() > MAX_DATE_INTERVAL) {
      logger.error("date range to large. MUST LESS than {}", MAX_DATE_INTERVAL);
      return null;
    }

    List<String> result = new LinkedList<>();
    while (sDate.isBefore(eDate)) {
      result.add(sDate.toString(DAY_FORMAT));
      sDate = sDate.plusDays(1);
    }
    Collections.reverse(result);
    String[] returnValue = new String[result.size()];
    result.toArray(returnValue);
    return returnValue;
  }

  /**
   * 根据int形式的日期起止，获取日期范围
   * 
   * @param startDate
   * @param endDate
   * @return created by xuzhw on 2015年6月17日 下午3:06:08
   */
  public static String[] getIntFormatDateRange(int startDate, int endDate) {
    String start = String.valueOf(startDate);
    String end = String.valueOf(endDate);
    DateTime sdt = dayToIntFormater.parseDateTime(start);
    DateTime edt = dayToIntFormater.parseDateTime(end);

    if (edt.isBefore(sdt)) {
      logger.error("end date MUST AFTER than start date, startDate: {}, endDate: {}", startDate,
          endDate);
      return new String[0];
    }
    if (Days.daysBetween(sdt, edt).getDays() > MAX_DATE_INTERVAL) {
      logger.error("date range to large. MUST LESS than {}", MAX_DATE_INTERVAL);
      return new String[0];
    }

    List<String> result = Lists.newArrayList();
    while (sdt.isBefore(edt) || sdt.isEqual(edt)) {
      result.add(sdt.toString(DT_INT_FORMAT));
      sdt = sdt.plusDays(1);
    }
    String[] returnValue = new String[result.size()];
    return result.toArray(returnValue);
  }

  /**
   * 返回系统当前时间(精确到毫秒), 附件三位随机数作为一个唯一的订单编号
   * 
   * @return 以yyyyMMddHHmmss为格式的当前系统时间
   */
  public static String getOrderNum() {
    Date date = new Date();
    DateFormat df = new SimpleDateFormat(DT_LONG_FORMAT);
    return df.format(date) + String.format("%03d", rand.nextInt(1000))
        + (new StringBuilder(System.nanoTime() + "")).reverse().substring(0, 4);
  }
  
  public static Date[] getQtrDateRange(int year, int qtr) {
    int endMonth = qtr * 3 - 1;
    int startMondth = endMonth - 2;
    
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, startMondth);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    Date fromDate = cal.getTime();
    
    cal.set(Calendar.MONTH, endMonth);
    cal.add(Calendar.MONTH, 1);
    cal.add(Calendar.DAY_OF_MONTH, -1);
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    Date toDate = cal.getTime();
    
    return new Date[] { fromDate, toDate };
  }
  
  public static String[] getStringFormatQtrDateRange(int year, int qtr) {
    Date[] qtrDateRange = getQtrDateRange(year, qtr);
    
    return new String[] {
        dayFormatDateToString(qtrDateRange[0]),
        dayFormatDateToString(qtrDateRange[1])
    };
  }
  
  public static int[] getIntFormatQtrDateRange(int year, int qtr) {
    Date[] qtrDateRange = getQtrDateRange(year, qtr);
    
    return new int[] {
        dayFormatDateToInt(qtrDateRange[0]),
        dayFormatDateToInt(qtrDateRange[1])
    };
  }


  public static Date timeToDate(String str) {
    SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
    try {
      return sdf.parse(str);
    } catch (ParseException e) {
      logger.error("format date error "+str,e);
    }
    return null;
  }

  public static Date dayToDate(String str) {
    SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT);
    try {
      return sdf.parse(str);
    } catch (ParseException e) {
      logger.error("format date error "+str,e);
    }
    return null;
  }

  public static int getHour(Date date) {
    DateTime dateTime = new DateTime(date);
    return dateTime.getHourOfDay();
  }

}
