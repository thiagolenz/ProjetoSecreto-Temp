package com.redfire.nutrieduc.commonsvc.svc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.redfire.nutrieduc.commonsvc.svc.exception.MandatoryFieldException;

/**
 * This class is responsible to verifiy if the validation did by spring has errors. 
 * If has then it throws a mandatory Fields exception
 * @author thiagolenz
 * @since Sep 4, 2014
 */
@Component
public class DefaultBeanValidator {
	public void validateMandatoryFields (BindingResult result) throws MandatoryFieldException {
		if (result.hasErrors()) {
			List<String> multipleErrors = new ArrayList<>();
			for (ObjectError objectError : result.getAllErrors()) {
				StringBuilder builderMessage = new StringBuilder();
				if (objectError.getCodes().length > 0)
					builderMessage.append("Field: "+ objectError.getCodes()[0]);
				builderMessage.append("; Error: "+ objectError.getDefaultMessage());
				multipleErrors.add(builderMessage.toString());
			}
			throw new MandatoryFieldException(multipleErrors);
		}
	}
}
