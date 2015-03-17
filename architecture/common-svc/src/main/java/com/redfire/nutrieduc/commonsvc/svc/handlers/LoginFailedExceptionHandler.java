package com.redfire.nutrieduc.commonsvc.svc.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.redfire.nutrieduc.common.ErrorResponseBean;

import com.redfire.nutrieduc.commonsvc.svc.exception.LoginFailedException;

/**
 * Handler of Login Exception 
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 */
@ControllerAdvice
public class LoginFailedExceptionHandler {
	@ExceptionHandler(value = LoginFailedException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ErrorResponseBean defaultErrorHandler(HttpServletRequest req, LoginFailedException e) throws Exception {
		ErrorResponseBean errorBean = new ErrorResponseBean();
		errorBean.setErrorCode("01");
		errorBean.setErrorDescription(e.getMessage());
		return errorBean;
	}
}
