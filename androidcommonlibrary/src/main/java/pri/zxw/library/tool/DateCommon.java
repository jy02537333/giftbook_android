package pri.zxw.library.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @className 时间处理方法
 * @author 张相伟
 * @function 类功能
 * @createDate 2015-2-7
 * @version 1
 * @upadteMemter 2015-2-7
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public class DateCommon {
	/** 长格式 yyyy.MM.dd  */
	public static final String YYYY_P_MM_P_DD = "yyyy.MM.dd";

	/** 日期格式：yyyy-MM-dd HH:mm:ss **/
	public static final String MM_DD_HH_MM_SS="MM-dd HH:mm:ss";
	/** 日期格式：yyyy-MM-dd HH:mm:ss **/
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/** 日期格式：yyyy-MM-dd HH:mm:ss **/
	public static final String MM_DD_HH_MM="MM-dd HH:mm";

	/** 日期格式：yyyy-MM-dd HH:mm **/
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	/** 日期格式：yyyy-MM-dd **/
	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	/** 日期格式：HH:mm:ss **/
	public static final String HH_MM_SS = "HH:mm:ss";

	/** 日期格式：HH:mm **/
	public static final String HH_MM = "HH:mm";

	private final static long minute = 60 * 1000;// 1分钟
	private final static long hour = 60 * minute;// 1小时
	private final static long day = 24 * hour;// 1天
	private final static long month = 31 * day;// 月
	private final static long year = 12 * month;// 年


	public static String getCurrentDateStr() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat(
				YYYY_MM_DD_HH_MM_SS);
		return f.format(c.getTime());
	}
	/**
	 * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
	 * @param date
	 * @return
	 */
	public final static String formatFriendly(Date date) {
		if (date == null) {
			return null;
		}
		long diff = new Date().getTime() - date.getTime();
		long r = 0;
		if (diff > year) {
			r = (diff / year);
			return r + "年前";
		}
		if (diff > month) {
			r = (diff / month);
			return r + "个月前";
		}
		if (diff > day) {
			r = (diff / day);
			return r + "天前";
		}
		if (diff > hour) {
			r = (diff / hour);
			return r + "个小时前";
		}
		if (diff > minute) {
			r = (diff / minute);
			return r + "分钟前";
		}
		return "刚刚";
	}

	/**
	 * 把date字符串转换为时间格式，当date为空时或不是时间字符串时返回空值
	 * @作者 田应平
	 * @创建时间 2016年10月23日 下午2:03:54
	 * @QQ号码 444141300
	 * @主页 http://www.yinlz.com
	 */
	public final static Date toDate(String date){
		if (ToolsString.isEmptyForObj(date))return null;
		SimpleDateFormat sdf = null ;
		try {
			switch (date.length()) {
				case 19:
					sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.CHINA);
					break;
				case 16:
					sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM,Locale.CHINA);
					break;
				case 10:
					sdf = new SimpleDateFormat(YYYY_MM_DD,Locale.CHINA);
					break;
				case 8:
					sdf = new SimpleDateFormat(HH_MM_SS,Locale.CHINA);
					break;
				default:
					break;
			}
			return sdf.parse(date);
		} catch (ParseException e){
			return null;
		}
	}

	/**
	 * 将日期以yyyy-MM-dd HH:mm:ss格式化
	 *
	 * @param dateL 日期
	 * @return
	 */
	public final static String formatDateTime(long dateL) {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS,Locale.CHINA);
		Date date = new Date(dateL);
		return sdf.format(date);
	}

	/**
	 * 将日期以yyyy-MM-dd HH:mm:ss格式化
	 * @param dateL 日期
	 * @return
	 */
	public final static String formatDateTime(long dateL, String formater) {
		SimpleDateFormat sdf = new SimpleDateFormat(formater,Locale.CHINA);
		return sdf.format(new Date(dateL));
	}

	/**
	 * 将日期以yyyy-MM-dd HH:mm:ss格式化
	 * @return
	 */
	public final static String formatDateTime(Date date, String formater) {
		SimpleDateFormat sdf = new SimpleDateFormat(formater,Locale.CHINA);
		return sdf.format(date);
	}

	/**
	 * 将日期字符串转成日期
	 * @param strDate 字符串日期
	 * @return java.util.date日期类型
	 */
	public final static Date parseDate(String strDate) {
		DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS,Locale.CHINA);
		Date returnDate = null;
		try {
			returnDate = dateFormat.parse(strDate);
		} catch (ParseException e) {
		}
		return returnDate;

	}

	/**
	 * 获取系统当前日期
	 * @return
	 */
	public final static Date gainCurrentDate() {
		return new Date();
	}

	/**
	 * 验证日期是否比当前日期早
	 * @param target1 比较时间1
	 * @param target2 比较时间2
	 * @return true 则代表target1比target2晚或等于target2，否则比target2早
	 */
	public final static boolean compareDate(Date target1, Date target2) {
		boolean flag = false;
		try {
			String target1DateTime = formatDateTime(target1, YYYY_MM_DD_HH_MM_SS);
			String target2DateTime = formatDateTime(target2, YYYY_MM_DD_HH_MM_SS);
			if (target1DateTime.compareTo(target2DateTime) <= 0) {
				flag = true;
			}
		} catch (Exception e1) {
		}
		return flag;
	}

	/**
	 * 对日期进行增加操作
	 * @param target 需要进行运算的日期
	 * @param hour 小时
	 * @return
	 */
	public final static Date addDateTime(Date target, double hour) {
		if (null == target || hour < 0) {
			return target;
		}
		return new Date(target.getTime() + (long) (hour * 60 * 60 * 1000));
	}

	/**
	 * 对日期进行相减操作
	 * @param target  需要进行运算的日期
	 * @param hour 小时
	 * @return
	 */
	public final static Date subDateTime(Date target, double hour) {
		if (null == target || hour < 0) {
			return target;
		}
		return new Date(target.getTime() - (long) (hour * 60 * 60 * 1000));
	}
	/**
	 * 时间格式字符转换Calendar格式时间
	 * @param dateStr
	 * @return
	 */
	public final static Calendar strToCalendar(String dateStr)
	{

		SimpleDateFormat sdf= new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS,Locale.CHINA);

		Date date=null;
		Calendar calendar = Calendar.getInstance();
		try {
			date = sdf.parse(dateStr);
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}
	/**
	 * Calendar格式时间 转换时间格式字符  yyyy/MM/DD
	 * @param calendar
	 * @return
	 */
	public final static  String calendarToStr_yyyy_X_MM_X_DD(Calendar calendar)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd",Locale.CHINA);
		String dateStr = sdf.format(calendar.getTime());
		return dateStr;
	}
	/**
	 * yyyy-MM-DD 转换时间格式字符  yyyy/MM/DD
	 * @param dateStr
	 * @return
	 */
	public final static  String yyyy_MM_ddToStr_yyyy_X_MM_X_dd(String dateStr)
	{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd",Locale.CHINA);
			Date date= parseDate(dateStr);
			if(date==null)
				return "";
			return sdf.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * yyyy-MM-DD 转换时间格式字符  MM月DD日
	 * @param dateStr
	 * @return
	 */
	public final static  String yyyy_MM_ddToStr_MM_dd(String dateStr)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		Date date= parseDate(dateStr);
		if(date==null)
			return "";
		return sdf.format(date);
	}

	/**
	 * yyyy-MM-DD 转换时间格式字符  MM月DD日
	 * @return
	 */
	public final static  String dateToYYYY_P_MM_P_dd(Long dateL)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_P_MM_P_DD);
		if(dateL==null)
			return "";
		Date date=new Date(dateL);
		return sdf.format(date);
	}
	/**
	 * 获取当前时间作文件名称
	 * @return
	 */
	public final static  String getDateFileName()
	{

			StringBuffer sBuffer = new StringBuffer();
			Calendar cal = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minute = cal.get(Calendar.MINUTE);
			int second = cal.get(Calendar.SECOND);
			int mm = cal.get(Calendar.MILLISECOND);

			sBuffer.append(cal.get(Calendar.YEAR)).append(cal.get(Calendar.MONTH)+1).append(cal.get(Calendar.DAY_OF_MONTH))
					.append(hour).append(minute).append(second).append(mm);
			return sBuffer.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	private static long toDays(long date) {
		return toHours(date) / 24L;
	}

	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}

	private static long toYears(long date) {
		return toMonths(date) / 365L;
	}

}
