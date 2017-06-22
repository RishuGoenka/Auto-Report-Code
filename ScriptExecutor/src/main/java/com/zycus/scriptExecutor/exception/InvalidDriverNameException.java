package com.zycus.scriptExecutor.exception;

/**
 * May occur when database name is not specified or not in the specified format.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class InvalidDriverNameException extends Exception {

	private static final long serialVersionUID = -151518063697969021L;

	public InvalidDriverNameException() {
		super();
	}

	public InvalidDriverNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidDriverNameException(String message) {
		super(message);
	}

	public InvalidDriverNameException(Throwable cause) {
		super(cause);
	}

}
