package com.redfire.nutrieduc.commonsvc.context;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.redfire.nutrieduc.common.Constants;

import com.redfire.nutrieduc.commonsvc.svc.exception.ServiceGatewayNotAuthorizedException;

@Aspect
public class RequestServiceGatewayValidationAspect {
	private static final String SERVICE_GATEWAY_NUTRIEDUC = "739b7a0a-2b73-455b-87b2-dd511afabde5";
	@Autowired
	HttpServletRequest httpServletRequest;
	
	@Before("bean(*Controller)")
	public void validateServiceGateway(JoinPoint joinPoint) throws IllegalArgumentException, IllegalAccessException, IOException, ServiceGatewayNotAuthorizedException {
		String value = httpServletRequest.getHeader(Constants.SERVICE_GATEWAY_KEY);
		if (value == null || !value.equals(SERVICE_GATEWAY_NUTRIEDUC))
			throw new ServiceGatewayNotAuthorizedException();
	}
}
