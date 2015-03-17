package com.redfire.nutrieduc.entities.appointment;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.redfire.nutrieduc.entities.common.AbstractEntity;
import com.redfire.nutrieduc.entities.user.User;

@Entity
public class Appointment extends AbstractEntity {
	@SequenceGenerator(name="seq_appointment",
			sequenceName="seq_appointment",
			allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="seq_appointment")
	@Id
	private Long id;
	@ManyToOne
	@JoinColumn(name = "nutritionist_user_id")
	private User nutritionistUser;
	@ManyToOne
	@JoinColumn(name = "patient_user_id")
	private User patientUser;
	private Date startDate;
	private Date finishDate;
	private String description;
	private String observation;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getNutritionistUser() {
		return nutritionistUser;
	}
	public void setNutritionistUser(User nutritionistUser) {
		this.nutritionistUser = nutritionistUser;
	}
	public User getPatientUser() {
		return patientUser;
	}
	public void setPatientUser(User patientUser) {
		this.patientUser = patientUser;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
}
