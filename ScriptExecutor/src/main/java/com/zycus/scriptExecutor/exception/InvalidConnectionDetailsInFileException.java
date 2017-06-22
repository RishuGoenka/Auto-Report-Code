package com.zycus.scriptExecutor.exception;

/**
 * May Occur when connection details provided in properties file is not present
 * or in incorrect format.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class InvalidConnectionDetailsInFileException extends Exception {

	private static final long serialVersionUID = -4794915706180522154L;

	public InvalidConnectionDetailsInFileException() {
		super();
	}

	public InvalidConnectionDetailsInFileException(String message,
			Throwable cause) {
		super(message, cause);
	}

	public InvalidConnectionDetailsInFileException(String message) {
		super(message);
	}

	public InvalidConnectionDetailsInFileException(Throwable cause) {
		super(cause);
	}
}
