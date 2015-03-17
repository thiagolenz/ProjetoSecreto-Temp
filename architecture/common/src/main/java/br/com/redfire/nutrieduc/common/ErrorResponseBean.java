package br.com.redfire.nutrieduc.common;

import java.io.Serializable;
import java.util.List;

/**
 * Bean of error Response
 * @author thiagolenz
 * @since Aug 25, 2014
 */
public class ErrorResponseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1520695598275490038L;
	private String errorCode;
	private String errorDescription;
	private List<String> details;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public List<String> getDetails() {
		return details;
	}
	public void setDetails(List<String> details) {
		this.details = details;
	}
}
