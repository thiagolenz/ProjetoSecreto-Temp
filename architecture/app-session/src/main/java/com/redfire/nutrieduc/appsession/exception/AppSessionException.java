package com.redfire.nutrieduc.appsession.exception;

/**
 * Exception of AppSession 
 * @author thiagolenz
 * @since Aug 25, 2014
 *
 */
public class AppSessionException extends Exception {
	private static final long serialVersionUID = -6630038982190715516L;
	
	private int status = 0;

	public AppSessionException(String message, Throwable cause, int status) {
		super(message, cause);
		this.status = status;
	}

	public AppSessionException(String message, int status) {
		super(message);
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}

}
