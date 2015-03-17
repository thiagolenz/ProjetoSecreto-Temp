package com.redfire.nutrieduc.commonsvc.svc.controller;

import org.springframework.stereotype.Component;

import com.redfire.nutrieduc.commonsvc.context.RequestContext;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;

@Component
public class ServiceRequestFactory <T> {

	public ServiceRequest<T> createServiceRequest (T entity, RequestContext requestContext) {
		ServiceRequest<T> request = new ServiceRequest<>();
		configureAccount(requestContext, request);
		request.setEntity(entity);
		request.setCurrentPage(requestContext.getCurrentPage());
		request.setRecordsRange(requestContext.getLimit());
		return request;
	}

	private void configureAccount(RequestContext requestContext, ServiceRequest<T> request) {
		if (requestContext.getUser() != null)
			request.setAccount(requestContext.getAccount());
		else 
			request.setAccount(requestContext.getAccount());
	}
}
