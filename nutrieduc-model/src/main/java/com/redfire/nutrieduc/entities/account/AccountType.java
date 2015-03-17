package com.redfire.nutrieduc.entities.account;

public enum AccountType {
	NUTRITIONIST (0,"account.type.nutritionist"),
	PATIENT (1, "account.type.patient");
	
	private Integer value;
	private String description;
	
	private AccountType(Integer value, String description) {
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
