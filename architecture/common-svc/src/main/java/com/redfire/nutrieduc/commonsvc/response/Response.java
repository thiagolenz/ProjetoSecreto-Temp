package com.redfire.nutrieduc.commonsvc.response;

/**
 * Generic success response
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 */
public class Response {
	private String message;
	
	public static Response newSuccessResponse() {
		return new Response("success");
	}
	
	public static Response newBooleanResponse(boolean response) {
		return new Response(""+response);
	}
	
	public static Response notFoundResponse() {
		return new Response("Record not found");
	}
	
	private Response(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
