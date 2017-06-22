package com.zycus.scriptExecutor.exception;

/**
 * May occur when the version in the upgrade script file is not specified or not
 * in the proper format.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class InvalidVersionDetailsInDBException extends Exception {

	private static final long serialVersionUID = 3977526309262739608L;

	public InvalidVersionDetailsInDBException() {
		super();
	}

	public InvalidVersionDetailsInDBException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidVersionDetailsInDBException(String message) {
		super(message);
	}

	public InvalidVersionDetailsInDBException(Throwable cause) {
		super(cause);
	}
}
