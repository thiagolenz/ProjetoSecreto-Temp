package com.redfire.nutrieduc.entities.consultation;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.redfire.nutrieduc.entities.common.AbstractEntity;

@Entity
public class ConsultationClinicalData extends AbstractEntity{
	@SequenceGenerator(name="seq_ConsultationClinicalData",
			sequenceName="seq_ConsultationClinicalData",
			allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="seq_ConsultationClinicalData")
	@Id
	private Long id;
	private String diseasesInTreatment;
	private String medicationsInUse;
	private Boolean didStomachSurgery;
	
	@Enumerated(EnumType.STRING)
	private IntestineFrequencyType intestineFrequencyType;
	
	private Boolean didTherapy;
	
	@Enumerated(EnumType.STRING)
	private BloodType bloodType;
	
	private String lastTypeDidBloodExam;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDiseasesInTreatment() {
		return diseasesInTreatment;
	}
	public void setDiseasesInTreatment(String diseasesInTreatment) {
		this.diseasesInTreatment = diseasesInTreatment;
	}
	public String getMedicationsInUse() {
		return medicationsInUse;
	}
	public void setMedicationsInUse(String medicationsInUse) {
		this.medicationsInUse = medicationsInUse;
	}
	public Boolean getDidStomachSurgery() {
		return didStomachSurgery;
	}
	public void setDidStomachSurgery(Boolean didStomachSurgery) {
		this.didStomachSurgery = didStomachSurgery;
	}
	public IntestineFrequencyType getIntestineFrequencyType() {
		return intestineFrequencyType;
	}
	public void setIntestineFrequencyType(
			IntestineFrequencyType intestineFrequencyType) {
		this.intestineFrequencyType = intestineFrequencyType;
	}
	public Boolean getDidTherapy() {
		return didTherapy;
	}
	public void setDidTherapy(Boolean didTherapy) {
		this.didTherapy = didTherapy;
	}
	public BloodType getBloodType() {
		return bloodType;
	}
	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}
	public String getLastTypeDidBloodExam() {
		return lastTypeDidBloodExam;
	}
	public void setLastTypeDidBloodExam(String lastTypeDidBloodExam) {
		this.lastTypeDidBloodExam = lastTypeDidBloodExam;
	}
}
