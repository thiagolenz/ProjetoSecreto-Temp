package com.redfire.nutrieduc.commonsvc.svc.exception;

import java.util.List;

/**
 * 
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 */
public class MandatoryFieldException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8472484386786224854L;
	private String field;
	private List<String> multipleErrors;

	public MandatoryFieldException(String field) {
		super();
		this.field = field;
	}
	
	public MandatoryFieldException(List<String> multipleErrors) {
		super();
		this.multipleErrors = multipleErrors;
	}

	public String getField() {
		return field;
	}
	
	public List<String> getMultipleErrors() {
		return multipleErrors;
	}

}
