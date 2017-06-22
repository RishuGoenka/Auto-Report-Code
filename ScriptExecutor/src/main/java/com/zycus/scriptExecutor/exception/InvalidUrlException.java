package com.zycus.scriptExecutor.exception;

/**
 * May occur when Turbatio url specified is incorrect or not in the proper
 * format and doesn't have any content.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class InvalidUrlException extends Exception {

	private static final long serialVersionUID = -6192360116320844344L;

	public InvalidUrlException() {
		super();
	}

	public InvalidUrlException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidUrlException(String message) {
		super(message);
	}

	public InvalidUrlException(Throwable cause) {
		super(cause);
	}

}
