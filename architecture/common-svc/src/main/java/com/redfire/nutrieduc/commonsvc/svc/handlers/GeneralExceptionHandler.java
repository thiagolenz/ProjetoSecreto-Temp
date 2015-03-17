package com.redfire.nutrieduc.commonsvc.svc.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.redfire.nutrieduc.common.ErrorResponseBean;

/**
 * General Exception Handler, Unexpected errors 
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 */
@ControllerAdvice
public class GeneralExceptionHandler {
	
	@ExceptionHandler(value = RuntimeException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponseBean defaultErrorHandler(HttpServletRequest req, RuntimeException e) throws Exception {
		e.printStackTrace();
		ErrorResponseBean errorBean = new ErrorResponseBean();
		errorBean.setErrorCode("0");
		errorBean.setErrorDescription("An internal server error ocurred : " + e.getCause().getMessage());
		return errorBean;
	}
}
