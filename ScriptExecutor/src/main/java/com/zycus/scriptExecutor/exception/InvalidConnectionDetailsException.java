package com.zycus.scriptExecutor.exception;

/**
 * May Occur when connection details provided are incorrect or not in the proper
 * format and unable to create connection to database.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class InvalidConnectionDetailsException extends Exception {

	private static final long serialVersionUID = 8675748561226513142L;

	public InvalidConnectionDetailsException() {
		super();
	}

	public InvalidConnectionDetailsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidConnectionDetailsException(String message) {
		super(message);
	}

	public InvalidConnectionDetailsException(Throwable cause) {
		super(cause);
	}
}
