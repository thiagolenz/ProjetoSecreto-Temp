package com.redfire.nutrieduc.entities.user;

public enum GenderType {
	FEMALE (0,"gender.type.female"),
	MALE (1, "gender.type.male");
	
	private Integer value;
	private String description;
	
	private GenderType(Integer value, String description) {
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
