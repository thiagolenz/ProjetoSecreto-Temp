package com.redfire.nutrieduc.commonsvc.response;

public class OperationServiceResponse {
	private String operation;
	private String method;
	private String classController;
	private String classRequest;
	private String classResponse;
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getClassRequest() {
		return classRequest;
	}
	public void setClassRequest(String classRequest) {
		this.classRequest = classRequest;
	}
	public String getClassResponse() {
		return classResponse;
	}
	public void setClassResponse(String classResponse) {
		this.classResponse = classResponse;
	}
	
	public String getClassController() {
		return classController;
	}
	public void setClassController(String classController) {
		this.classController = classController;
	}
}
