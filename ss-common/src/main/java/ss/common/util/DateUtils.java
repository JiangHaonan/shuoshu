package ss.common.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author mutou
 * @date 2017年7月14日
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	/** 格式 ：yyyyMMdd */
	public static final String DATEFORMAT_STR_1= "yyyyMMdd";
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, DATEFORMAT_STR_1);
	}
	
	/**
	 * 获取当前时间-时间戳
	 * 
	 * @return
	 */
	public static int getNowTime() {
		return Integer.parseInt(StringUtils.isNull(System.currentTimeMillis() / 1000));
	}
	
	/**
	 * 获得当前日期
	 * @return
	 */
	public static Date getNow() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();
		return currDate;
	}
}
