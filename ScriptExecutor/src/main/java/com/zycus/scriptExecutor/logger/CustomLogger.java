package com.zycus.scriptExecutor.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zycus.scriptExecutor.ScriptExcutor;

/**
 * Simple Custom Logger to print the error messages on the console rather than a
 * log file. Has the same function names as any common logger for ease of
 * functionality. Also uses apache common logging to create log files in the
 * specified properties file of LOGGER
 * 
 * @author harshvardhan.dudeja
 *
 */
public class CustomLogger {

	private static final Log LOGGER = LogFactory.getLog(ScriptExcutor.class);
	private static CustomLogger customLogger = null;

	private CustomLogger() {
	}

	public static CustomLogger getInstance() {
		if (customLogger == null)
			customLogger = new CustomLogger();
		return customLogger;
	}

	public void info(String message) {
		LOGGER.info(message);
		System.out.println(message);
	}

	public void error(String message, Exception e) {
		LOGGER.error(message, e);
		System.out.println(message);
	}

	public void error(String message) {
		LOGGER.error(message);
		System.out.println(message);
	}

	public void debug(String message, Exception e) {
		LOGGER.debug(message, e);
		System.out.println(message);
	}

	public void fatal(String message, Exception e) {
		LOGGER.fatal(message, e);
		System.out.println(message);
	}

}
