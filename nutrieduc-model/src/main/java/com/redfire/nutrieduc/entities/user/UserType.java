package com.redfire.nutrieduc.entities.user;

public enum UserType {
	NUTRITIONIST (0,"user.type.nutritionist"),
	PATIENT (1, "user.type.patient"),
	EMPLOYEE (1, "user.type.employee");
	
	private Integer value;
	private String description;
	
	private UserType(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}
}
