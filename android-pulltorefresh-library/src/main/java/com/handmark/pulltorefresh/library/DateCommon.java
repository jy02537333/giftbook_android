package com.handmark.pulltorefresh.library;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	/** 长格式 yyyy-MM-dd hh:mm:ss 12小时 hh:mm:ss 24小时 */
	public static final String dateFormatBase = "yyyy-MM-dd HH:mm:ss";
	/** 短格式 yyyy-MM-dd */
	public static final String dateShortFormat = "yyyy-MM-dd";
	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;
	private static final long ONE_DAY = 86400000L;
	private static final long ONE_WEEK = 604800000L;
	private static final String ONE_SECOND_AGO = "秒前";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";
	private static final String ONE_DAY_AGO = "天前";
	private static final String ONE_MONTH_AGO = "月前";
	private static final String ONE_YEAR_AGO = "年前";

	public static String getCurrentDateStr() {
		java.util.Calendar c = java.util.Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat(
				dateFormatBase);
		return f.format(c.getTime());
	}

	/**
	 * long转换成时间格式
	 * 
	 * @param longStr
	 * @return
	 */
	public static String longConvertDateStr(String longStr) {

		String str = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormatBase);
			long l = Long.parseLong(longStr);
			str = format.format(new Date(l));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return str;
	}

	public static String convertString(String dateStr) {
		if (dateStr == null || dateStr.trim().length() < 5) {
			return dateStr;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		try {
			Date date = sdf.parse(dateStr);
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return dateStr;
		}
	}

	public static Date convertString(String dateStr, String dateFormat) {
		if (dateStr == null) {
			return null;
		}
		SimpleDateFormat sdf = null;
		if (dateFormat != null)
			sdf = new SimpleDateFormat(dateFormat);
		else
			sdf = new SimpleDateFormat(dateFormatBase);
		try {
			Date date = sdf.parse(dateStr);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
			Date date = new Date(System.currentTimeMillis());
			return date;
		}
	}

	/**
	 * 获取当前时间与传入的时间差
	 * 
	 * @return 格式多少多少时间至前 :1天前
	 */
	public static String formatDateBefor(String dateStr) {
		return formatDateBefor(convertString(dateStr, dateFormatBase));
	}
	/**
	 * long转换成时间格式
	 * 
	 * @return
	 */
	public static Date longConvertDate(long longDate) {
		try {
			return new Date(longDate) ;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 下拉刷新时间格式
	 * 
	 */
	public static String pullToRefreshDate(long longDate) {
		String dateStr=longConvertDateStr(longDate+"");
		
		Date date=longConvertDate(longDate);
		long delta = new Date().getTime() - date.getTime();
		if (delta < 1L * ONE_MINUTE) {
			return "刚刚";
		}
		if (delta < 45L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}
		long days = toDays(delta);
		String dayStr="";
		if(days==0)
			dayStr="今日";
		else if(days==1)
			dayStr="昨日";
		else {
			dayStr="很久以前";
		}
		 dateStr=dayStr+dateStr.substring(10);
		return dateStr;
	}
	/**
	 * 获取当前时间与传入的时间差
	 * 
	 * @param date
	 * @return 格式多少多少时间之前 :1天前
	 */
	public static String formatDateBefor(Date date) {
		long delta = new Date().getTime() - date.getTime();
		if (delta < 1L * ONE_MINUTE) {
			long seconds = toSeconds(delta);
			return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
		}
		if (delta < 45L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
		}
		if (delta < 48L * ONE_HOUR) {
			return "昨天";
		}
		if (delta < 30L * ONE_DAY) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
		}
		if (delta < 12L * 4L * ONE_WEEK) {
			long months = toMonths(delta);
			return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
		} else {
			long years = toYears(delta);
			return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
		}
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
