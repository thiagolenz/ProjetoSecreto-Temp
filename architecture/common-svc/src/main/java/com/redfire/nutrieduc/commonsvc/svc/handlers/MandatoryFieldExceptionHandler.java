package com.redfire.nutrieduc.commonsvc.svc.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.redfire.nutrieduc.common.ErrorResponseBean;

import com.redfire.nutrieduc.commonsvc.i18n.MessageBundleService;
import com.redfire.nutrieduc.commonsvc.svc.exception.MandatoryFieldException;

/**
 * Handler of mandatory Field Exception Handler
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 */
@ControllerAdvice
public class MandatoryFieldExceptionHandler {
	private static final String MANDATORY_FIELD = "mandatory.field.message";
	private static final String MANDATORY_FIELD_MANY_ERRORS = "mandatory.field.message.many.errors";
	
	@Autowired
	private MessageBundleService bundleService;

	@ExceptionHandler(value = MandatoryFieldException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	public ErrorResponseBean defaultErrorHandler(HttpServletRequest req, MandatoryFieldException e) throws Exception {
		ErrorResponseBean errorBean = new ErrorResponseBean();
		errorBean.setErrorCode("01");
		if (e.getField() != null) {
			String fieldTranslated = bundleService.translate(e.getField());
			String message = bundleService.translate(MANDATORY_FIELD, new Object[]{fieldTranslated});
			errorBean.setErrorDescription(message);
		} else {
			String message = bundleService.translate(MANDATORY_FIELD_MANY_ERRORS);
			errorBean.setErrorDescription(message);
			errorBean.setDetails(e.getMultipleErrors());
		}
		return errorBean;
	}
}
