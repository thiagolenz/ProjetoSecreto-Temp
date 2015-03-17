package com.redfire.nutrieduc.entities.food;


public class NutritionalFood {
	private Long code;
	private String name;
	
	private FoodPreparationType foodPreparationType;
	
	private Integer reference;
	private String referenceDescription;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FoodPreparationType getFoodPreparationType() {
		return foodPreparationType;
	}

	public void setFoodPreparationType(FoodPreparationType foodPreparationType) {
		this.foodPreparationType = foodPreparationType;
	}

	public Integer getReference() {
		return reference;
	}

	public void setReference(Integer reference) {
		this.reference = reference;
	}

	public String getReferenceDescription() {
		return referenceDescription;
	}

	public void setReferenceDescription(String referenceDescription) {
		this.referenceDescription = referenceDescription;
	}
	
}
