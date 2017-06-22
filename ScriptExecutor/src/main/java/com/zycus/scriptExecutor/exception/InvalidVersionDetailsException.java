package com.zycus.scriptExecutor.exception;

/**
 * May occur when the version in the upgrade script file is not specified or not
 * in the proper format.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class InvalidVersionDetailsException extends Exception {

	private static final long serialVersionUID = 3977526309262739608L;

	public InvalidVersionDetailsException() {
		super();
	}

	public InvalidVersionDetailsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidVersionDetailsException(String message) {
		super(message);
	}

	public InvalidVersionDetailsException(Throwable cause) {
		super(cause);
	}
}
