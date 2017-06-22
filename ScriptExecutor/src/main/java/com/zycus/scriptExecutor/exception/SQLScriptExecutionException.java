package com.zycus.scriptExecutor.exception;

/**
 * May occur when any error occurs while executing the database upgrade script
 * file.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class SQLScriptExecutionException extends Exception {

	private static final long serialVersionUID = -6888795383135514658L;

	public SQLScriptExecutionException() {
		super();
	}

	public SQLScriptExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public SQLScriptExecutionException(String message) {
		super(message);
	}

	public SQLScriptExecutionException(Throwable cause) {
		super(cause);
	}

}
