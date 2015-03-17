package com.redfire.nutrieduc.entities.consultation;

public enum BloodType {
	A_PLUS (0,"blood.type.a_plus"),
	A_MINUS (1,"blood.type.a_minus"),
	
	B_PLUS (2,"blood.type.b_plus"),
	B_MINUS (3,"blood.type.b_minus"),
	
	AB_PLUS (4,"blood.type.ab_plus"),
	AB_MINUS (5,"blood.type.ab_minus"),
	
	O_PLUS (7,"blood.type.o_plus"),
	O_MINUS (6,"blood.type.o_minus");
	
	private Integer value;
	private String description;
	
	private BloodType(Integer value, String description) {
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
