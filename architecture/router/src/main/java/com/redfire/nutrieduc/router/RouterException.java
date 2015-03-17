package com.redfire.nutrieduc.router;

/**
 * Exception if and error occurr on routing 
 * @author thiagolenz
 * @since Aug 25, 2014
 */
public class RouterException extends Exception {
	
	private int statusCode;

	private static final long serialVersionUID = 956775206495771886L;

	public RouterException(String message, Throwable cause, Integer statusCode) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public RouterException(String message, Integer statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public RouterException(Throwable cause) {
		super(cause);
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
