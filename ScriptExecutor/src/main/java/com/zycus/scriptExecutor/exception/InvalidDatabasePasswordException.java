package com.zycus.scriptExecutor.exception;

/**
 * May Occur when connection details provided are incorrect or not in the proper
 * format and unable to create connection to database.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class InvalidDatabasePasswordException extends Exception {

	private static final long serialVersionUID = 8675748561226513142L;

	public InvalidDatabasePasswordException() {
		super();
	}

	public InvalidDatabasePasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidDatabasePasswordException(String message) {
		super(message);
	}

	public InvalidDatabasePasswordException(Throwable cause) {
		super(cause);
	}
}
