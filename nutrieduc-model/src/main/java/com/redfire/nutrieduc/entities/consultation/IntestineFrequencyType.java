package com.redfire.nutrieduc.entities.consultation;

public enum IntestineFrequencyType {
	REGULAR (0,"intestine.frequency.type.regular"),
	ARRESTED (1, "intestine.frequency.type.arrested");
	
	private Integer value;
	private String description;
	
	private IntestineFrequencyType (Integer value, String description) {
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
