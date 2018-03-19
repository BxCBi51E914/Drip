package org.rg.drip.utils;

import java.util.Calendar;

/**
 * Created by TankGq
 * on 2018/3/19.
 */
public class TimeUtil {

	/**
	 * 获得当前时间的字符串
	 *
	 * @return yyyy-MM-dd HH:mm:ss:ms
	 */
	public static String getCurrentTime() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR) + "/"
		       + c.get(Calendar.MONTH) + "/"
		       + c.get(Calendar.DATE) + " "
		       + c.get(Calendar.HOUR_OF_DAY) + ":"
		       + c.get(Calendar.MINUTE) + ":"
		       + c.get(Calendar.SECOND) + ":"
		       + c.get(Calendar.MILLISECOND);
	}
}
