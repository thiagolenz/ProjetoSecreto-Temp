package com.redfire.nutrieduc.entities.consultation;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.redfire.nutrieduc.entities.common.AbstractEntity;
import com.redfire.nutrieduc.entities.user.User;

@Entity
public class Consultation extends AbstractEntity {
	@SequenceGenerator(name="seq_Consultation",
			sequenceName="seq_Consultation",
			allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="seq_Consultation")
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_patient_id")
	private User userPatient;
	
	@ManyToOne
	@JoinColumn(name = "user_nutritionist_id")
	private User userNutritionist;
	
	private Date consultDate;
	private String howKnewMyWork;
	private String objective;
	
	@ManyToOne
	@JoinColumn(name = "clinical_data_id")
	private ConsultationClinicalData clinicalData;
	
	@ManyToOne
	@JoinColumn(name = "life_habit_id")
	private ConsultationLifeHabit lifeHabit;
	
	@ManyToOne
	@JoinColumn(name = "eating_habit_id")
	private ConsultationEatingHabit eatingHabit;
	
	@ManyToOne
	@JoinColumn(name = "body_measure_id")
	private ConsultationBodyMeasure bodyMeasure;
	
	@Enumerated(EnumType.STRING)
	private ConsultationStatus consultationStatus;
	
	private Date createDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getConsultDate() {
		return consultDate;
	}
	public void setConsultDate(Date consultDate) {
		this.consultDate = consultDate;
	}
	public String getHowKnewMyWork() {
		return howKnewMyWork;
	}
	public void setHowKnewMyWork(String howKnewMyWork) {
		this.howKnewMyWork = howKnewMyWork;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public ConsultationClinicalData getClinicalData() {
		return clinicalData;
	}
	public void setClinicalData(ConsultationClinicalData clinicalData) {
		this.clinicalData = clinicalData;
	}
	public ConsultationLifeHabit getLifeHabit() {
		return lifeHabit;
	}
	public void setLifeHabit(ConsultationLifeHabit lifeHabit) {
		this.lifeHabit = lifeHabit;
	}
	public ConsultationEatingHabit getEatingHabit() {
		return eatingHabit;
	}
	public void setEatingHabit(ConsultationEatingHabit eatingHabit) {
		this.eatingHabit = eatingHabit;
	}
	public ConsultationBodyMeasure getBodyMeasure() {
		return bodyMeasure;
	}
	public void setBodyMeasure(ConsultationBodyMeasure bodyMeasure) {
		this.bodyMeasure = bodyMeasure;
	}
	public User getUserPatient() {
		return userPatient;
	}
	public void setUserPatient(User userPatient) {
		this.userPatient = userPatient;
	}
	public User getUserNutritionist() {
		return userNutritionist;
	}
	public void setUserNutritionist(User userNutritionist) {
		this.userNutritionist = userNutritionist;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public ConsultationStatus getConsultationStatus() {
		return consultationStatus;
	}
	
	public void setConsultationStatus(ConsultationStatus consultationStatus) {
		this.consultationStatus = consultationStatus;
	}
}
