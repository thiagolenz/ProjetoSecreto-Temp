package com.redfire.nutrieduc.restclient;

/**
 * Custom exception of RestClient
 * @author thiagolenz
 * @since 25/08/2014
 */
public class RestClientException extends Exception {

	private static final long serialVersionUID = 5479747318969273178L;
	private int statusCode;

	public RestClientException(String message, Throwable cause, int statusCode) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public RestClientException(String message, Integer statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public RestClientException(Throwable cause) {
		super(cause);
	}

	public int getStatusCode() {
		return statusCode;
	}
}
