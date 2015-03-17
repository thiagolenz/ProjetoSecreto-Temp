package com.redfire.nutrieduc.entities.consultation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.redfire.nutrieduc.entities.common.AbstractEntity;

@Entity
public class ConsultationEatingHabit extends AbstractEntity{
	@SequenceGenerator(name="seq_ConsultationEatingHabit",
			sequenceName="seq_ConsultationEatingHabit",
			allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="seq_ConsultationEatingHabit")
	@Id
	private Long id;
	private String energyFoods;
	private String mostHungryTime;
	private String hungryTimeFood;
	private String notLikeFood;
	private String notRemovableFoods;
	private String mostDificultyDietThing;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEnergyFoods() {
		return energyFoods;
	}
	public void setEnergyFoods(String energyFoods) {
		this.energyFoods = energyFoods;
	}
	public String getMostHungryTime() {
		return mostHungryTime;
	}
	public void setMostHungryTime(String mostHungryTime) {
		this.mostHungryTime = mostHungryTime;
	}
	public String getHungryTimeFood() {
		return hungryTimeFood;
	}
	public void setHungryTimeFood(String hungryTimeFood) {
		this.hungryTimeFood = hungryTimeFood;
	}
	public String getNotLikeFood() {
		return notLikeFood;
	}
	public void setNotLikeFood(String notLikeFood) {
		this.notLikeFood = notLikeFood;
	}
	public String getNotRemovableFoods() {
		return notRemovableFoods;
	}
	public void setNotRemovableFoods(String notRemovableFoods) {
		this.notRemovableFoods = notRemovableFoods;
	}
	public String getMostDificultyDietThing() {
		return mostDificultyDietThing;
	}
	public void setMostDificultyDietThing(String mostDificultyDietThing) {
		this.mostDificultyDietThing = mostDificultyDietThing;
	}
}
