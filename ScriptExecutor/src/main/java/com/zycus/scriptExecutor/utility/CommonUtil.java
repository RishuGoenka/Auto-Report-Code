package com.zycus.scriptExecutor.utility;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * 
 * @author reetam.nath
 *
 */
public class CommonUtil {

	public static boolean isContainNullParameter(String... parameters) {
		if (parameters == null) {
			return true;
		} else {
			for (String parameter : parameters)
				if (parameter == null || "".equalsIgnoreCase(parameter.trim()))
					return true;
		}
		return false;
	}

	public static String getDateString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
}
