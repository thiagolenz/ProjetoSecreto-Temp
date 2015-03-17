package com.redfire.nutrieduc.commonsvc.svc.exception;

public class ServiceGatewayNotAuthorizedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2535305036946360772L;

	public ServiceGatewayNotAuthorizedException() {
		super("Service gateway not authorized");
	}
}
