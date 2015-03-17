package com.redfire.nutrieduc.router;

/**
 * Bean of Failure
 * @author thiagolenz
 * @since Aug 25, 2014
 *
 */
public class FailureBean {
	private String description;
	
	public FailureBean(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}	
