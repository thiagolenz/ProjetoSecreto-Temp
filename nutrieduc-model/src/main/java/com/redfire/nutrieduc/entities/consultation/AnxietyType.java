package com.redfire.nutrieduc.entities.consultation;

public enum AnxietyType {
	NO (0,"anxiety.type.no"),
	NO_ALREADY_DID_TREATEMENT (1,"anxiety.type.no.already.did"),
	YES_NO_TREATMENT (0,"anxiety.type.yes.no.treatment"),
	YES_IN_TREATMENT (0,"anxiety.type.yes.in.treatment");
	
	private Integer value;
	private String description;
	
	private AnxietyType(Integer value, String description) {
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
