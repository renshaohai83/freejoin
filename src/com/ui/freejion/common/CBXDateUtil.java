package com.ui.freejion.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CBXDateUtil {

	public enum FormatType {
		YYYYMMDDHHMMSS_1, // 20130515021109
		YYYYMMDDHHMMSS_2, // 2013-05-15 02:11:09
		YYYYMMDDHHMMSS_3, // 2013年05月15日 02时11分09秒
		HHMMSS_1, // 02:11:09
		MMSS_1, // 11:09
		MMDD_1, // 07/15
		MMDD_2, // 7月15日
		YYYY_MM_DD, // 2013-07-15
		YYYY_MM_DD_2,// 2013年07月15日
		HH_MM_1, // 14:45
	}

	private static final String FORMAT_YYYYMMDDHHMMSS_1 = "yyyyMMddHHmmss";
	private static final String FORMAT_YYYYMMDDHHMMSS_2 = "yyyy-MM-dd HH:mm:ss";
	private static final String FORMAT_YYYYMMDDHHMMSS_3 = "yyyy年MM月dd日 HH时mm分ss秒";
	private static final String FORMAT_HHMMSS_1 = "HH:mm:ss";
	private static final String FORMAT_MMSS_1 = "mm:ss";
	private static final String FORMAT_MMDD_1 = "MM/dd";
	private static final String FORMAT_MMDD_2 = "MM月dd日";
	private static final String FORMAT_YYYY_MM_DD_1 = "yyyy-MM-dd";
	private static final String FORMAT_YYYY_MM_DD_2 = "yyyy年MM月dd日";
	private static final String FORMAT_HHMM = "HH:mm";

	/**
	 * 获取当前时间的millisecond值
	 * 
	 * @return
	 */
	public static long geCurrenttMillisecond() {
		Date date = new Date();
		return date.getTime();
	}

	/**
	 * 将制定格式的时间字符串转换为millisecond
	 * 
	 * @param time
	 * @param formatType
	 * @return
	 */
	public static long getMillisecondTime(String time, FormatType formatType) {
		String pattern = null;
		switch (formatType) {
		case YYYYMMDDHHMMSS_1:
			pattern = FORMAT_YYYYMMDDHHMMSS_1;
			break;
		case YYYYMMDDHHMMSS_2:
			pattern = FORMAT_YYYYMMDDHHMMSS_2;
			break;
		case YYYYMMDDHHMMSS_3:
			pattern = FORMAT_YYYYMMDDHHMMSS_3;
			break;
		case HHMMSS_1:
			pattern = FORMAT_HHMMSS_1;
			break;
		case MMSS_1:
			pattern = FORMAT_MMSS_1;
			break;
		case MMDD_1:
			pattern = FORMAT_MMDD_1;
			break;
		case MMDD_2:
			pattern = FORMAT_MMDD_2;
			break;
		case YYYY_MM_DD:
			pattern = FORMAT_YYYY_MM_DD_1;
			break;
		case YYYY_MM_DD_2:
			pattern = FORMAT_YYYY_MM_DD_2;
			break;
		case HH_MM_1:
			pattern = FORMAT_HHMM;
			break;
		default:
			return 0;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(pattern,
				Locale.SIMPLIFIED_CHINESE);
		Date date = null;
		try {
			date = sdf.parse(time);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 将指定的Date转换为字符串
	 * 
	 * @param date
	 * @param formatType
	 * @return
	 */
	public static String getDateTimeFormatString(Date date,
			FormatType formatType) {
		String pattern = null;
		switch (formatType) {
		case YYYYMMDDHHMMSS_1:
			pattern = FORMAT_YYYYMMDDHHMMSS_1;
			break;
		case YYYYMMDDHHMMSS_2:
			pattern = FORMAT_YYYYMMDDHHMMSS_2;
			break;
		case YYYYMMDDHHMMSS_3:
			pattern = FORMAT_YYYYMMDDHHMMSS_3;
			break;
		case HHMMSS_1:
			pattern = FORMAT_HHMMSS_1;
			break;
		case MMSS_1:
			pattern = FORMAT_MMSS_1;
			break;
		case MMDD_1:
			pattern = FORMAT_MMDD_1;
			break;
		case MMDD_2:
			pattern = FORMAT_MMDD_2;
			break;
		case YYYY_MM_DD:
			pattern = FORMAT_YYYY_MM_DD_1;
			break;
		case YYYY_MM_DD_2:
			pattern = FORMAT_YYYY_MM_DD_2;
			break;
		case HH_MM_1:
			pattern = FORMAT_HHMM;
			break;
		default:
			pattern = FORMAT_YYYYMMDDHHMMSS_1;
			break;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern,
				Locale.SIMPLIFIED_CHINESE);

		return format.format(date);
	}
}
