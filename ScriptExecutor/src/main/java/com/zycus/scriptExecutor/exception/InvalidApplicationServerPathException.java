package com.zycus.scriptExecutor.exception;

/**
 * May Occur when connection details provided are incorrect or not in the proper
 * format and unable to create connection to database.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class InvalidApplicationServerPathException extends Exception {

	private static final long serialVersionUID = 8675748561226513142L;

	public InvalidApplicationServerPathException() {
		super();
	}

	public InvalidApplicationServerPathException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidApplicationServerPathException(String message) {
		super(message);
	}

	public InvalidApplicationServerPathException(Throwable cause) {
		super(cause);
	}
}
