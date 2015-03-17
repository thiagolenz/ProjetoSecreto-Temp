package com.redfire.nutrieduc.entities.consultation;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.redfire.nutrieduc.entities.common.AbstractEntity;

@Entity
public class ConsultationLifeHabit extends AbstractEntity{
	@SequenceGenerator(name="seq_ConsultationLifeHabit",
			sequenceName="seq_ConsultationLifeHabit",
			allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="seq_ConsultationLifeHabit")
	@Id
	private Long id;
	private Boolean doFisicalActivities;
	private String fisicalActivitiesFrequency;
	private String trainingTime;
	private BigDecimal nightHoursSleeping;
	private Boolean smoke;
	private Boolean drink;
	
	@Enumerated(EnumType.STRING)
	private AnxietyType anxietyType;
	
	private BigDecimal litersOfWaterPerDay;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getDoFisicalActivities() {
		return doFisicalActivities;
	}
	public void setDoFisicalActivities(Boolean doFisicalActivities) {
		this.doFisicalActivities = doFisicalActivities;
	}
	public String getFisicalActivitiesFrequency() {
		return fisicalActivitiesFrequency;
	}
	public void setFisicalActivitiesFrequency(String fisicalActivitiesFrequency) {
		this.fisicalActivitiesFrequency = fisicalActivitiesFrequency;
	}
	public String getTrainingTime() {
		return trainingTime;
	}
	public void setTrainingTime(String trainingTime) {
		this.trainingTime = trainingTime;
	}
	public BigDecimal getNightHoursSleeping() {
		return nightHoursSleeping;
	}
	public void setNightHoursSleeping(BigDecimal nightHoursSleeping) {
		this.nightHoursSleeping = nightHoursSleeping;
	}
	public Boolean getSmoke() {
		return smoke;
	}
	public void setSmoke(Boolean smoke) {
		this.smoke = smoke;
	}
	public Boolean getDrink() {
		return drink;
	}
	public void setDrink(Boolean drink) {
		this.drink = drink;
	}
	public AnxietyType getAnxietyType() {
		return anxietyType;
	}
	public void setAnxietyType(AnxietyType anxietyType) {
		this.anxietyType = anxietyType;
	}
	public BigDecimal getLitersOfWaterPerDay() {
		return litersOfWaterPerDay;
	}
	public void setLitersOfWaterPerDay(BigDecimal litersOfWaterPerDay) {
		this.litersOfWaterPerDay = litersOfWaterPerDay;
	}
	
}
