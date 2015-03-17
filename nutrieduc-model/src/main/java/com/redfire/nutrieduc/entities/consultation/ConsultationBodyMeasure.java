package com.redfire.nutrieduc.entities.consultation;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.redfire.nutrieduc.entities.common.AbstractEntity;

@Entity
public class ConsultationBodyMeasure extends AbstractEntity{
	@SequenceGenerator(name="seq_ConsultationBodyMeasure",
			sequenceName="seq_ConsultationBodyMeasure",
			allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_ConsultationBodyMeasure")
	@Id
	private Long id;
	private BigDecimal weight;
	private BigDecimal height;
	
	private BigDecimal bendingTriceps;
	private BigDecimal bendingBack;
	private BigDecimal bendingBiceps;
	private BigDecimal foldBelt;
	private BigDecimal abdominalTuck;
	private BigDecimal bendingThigh;
	private BigDecimal axillaryFold;
	private BigDecimal pectoralFold;
	private BigDecimal leftArmContracted;
	private BigDecimal rightArmContracted;
	private BigDecimal chestCircumference;
	private BigDecimal weistCircumference;
	private BigDecimal abdomenCircumference;
	private BigDecimal rightThighCircumference;
	private BigDecimal leftThighCircumference;
	private BigDecimal rightCalfCircumference;
	private BigDecimal leftCalfCircumference;
	
	@ManyToOne
	@JoinColumn(name = "front_picture_id")
	private ConsultationUserPicture frontPicture;
	
	@ManyToOne
	@JoinColumn(name = "back_picture_id")
	private ConsultationUserPicture backPicture;
	
	@ManyToOne
	@JoinColumn(name = "left_picture_id")
	private ConsultationUserPicture leftPicture;
	
	@ManyToOne
	@JoinColumn(name = "right_picture_id")
	private ConsultationUserPicture rightPicture;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getHeight() {
		return height;
	}
	public void setHeight(BigDecimal height) {
		this.height = height;
	}
	public BigDecimal getBendingTriceps() {
		return bendingTriceps;
	}
	public void setBendingTriceps(BigDecimal bendingTriceps) {
		this.bendingTriceps = bendingTriceps;
	}
	public BigDecimal getBendingBack() {
		return bendingBack;
	}
	public void setBendingBack(BigDecimal bendingBack) {
		this.bendingBack = bendingBack;
	}
	public BigDecimal getBendingBiceps() {
		return bendingBiceps;
	}
	public void setBendingBiceps(BigDecimal bendingBiceps) {
		this.bendingBiceps = bendingBiceps;
	}
	public BigDecimal getFoldBelt() {
		return foldBelt;
	}
	public void setFoldBelt(BigDecimal foldBelt) {
		this.foldBelt = foldBelt;
	}
	public BigDecimal getAbdominalTuck() {
		return abdominalTuck;
	}
	public void setAbdominalTuck(BigDecimal abdominalTuck) {
		this.abdominalTuck = abdominalTuck;
	}
	public BigDecimal getBendingThigh() {
		return bendingThigh;
	}
	public void setBendingThigh(BigDecimal bendingThigh) {
		this.bendingThigh = bendingThigh;
	}
	public BigDecimal getAxillaryFold() {
		return axillaryFold;
	}
	public void setAxillaryFold(BigDecimal axillaryFold) {
		this.axillaryFold = axillaryFold;
	}
	public BigDecimal getPectoralFold() {
		return pectoralFold;
	}
	public void setPectoralFold(BigDecimal pectoralFold) {
		this.pectoralFold = pectoralFold;
	}
	public BigDecimal getLeftArmContracted() {
		return leftArmContracted;
	}
	public void setLeftArmContracted(BigDecimal leftArmContracted) {
		this.leftArmContracted = leftArmContracted;
	}
	public BigDecimal getRightArmContracted() {
		return rightArmContracted;
	}
	public void setRightArmContracted(BigDecimal rightArmContracted) {
		this.rightArmContracted = rightArmContracted;
	}
	public BigDecimal getChestCircumference() {
		return chestCircumference;
	}
	public void setChestCircumference(BigDecimal chestCircumference) {
		this.chestCircumference = chestCircumference;
	}
	public BigDecimal getWeistCircumference() {
		return weistCircumference;
	}
	public void setWeistCircumference(BigDecimal weistCircumference) {
		this.weistCircumference = weistCircumference;
	}
	public BigDecimal getAbdomenCircumference() {
		return abdomenCircumference;
	}
	public void setAbdomenCircumference(BigDecimal abdomenCircumference) {
		this.abdomenCircumference = abdomenCircumference;
	}
	public BigDecimal getRightThighCircumference() {
		return rightThighCircumference;
	}
	public void setRightThighCircumference(BigDecimal rightThighCircumference) {
		this.rightThighCircumference = rightThighCircumference;
	}
	public BigDecimal getLeftThighCircumference() {
		return leftThighCircumference;
	}
	public void setLeftThighCircumference(BigDecimal leftThighCircumference) {
		this.leftThighCircumference = leftThighCircumference;
	}
	public BigDecimal getRightCalfCircumference() {
		return rightCalfCircumference;
	}
	public void setRightCalfCircumference(BigDecimal rightCalfCircumference) {
		this.rightCalfCircumference = rightCalfCircumference;
	}
	public BigDecimal getLeftCalfCircumference() {
		return leftCalfCircumference;
	}
	public void setLeftCalfCircumference(BigDecimal leftCalfCircumference) {
		this.leftCalfCircumference = leftCalfCircumference;
	}
	public ConsultationUserPicture getFrontPicture() {
		return frontPicture;
	}
	public void setFrontPicture(ConsultationUserPicture frontPicture) {
		this.frontPicture = frontPicture;
	}
	public ConsultationUserPicture getBackPicture() {
		return backPicture;
	}
	public void setBackPicture(ConsultationUserPicture backPicture) {
		this.backPicture = backPicture;
	}
	public ConsultationUserPicture getLeftPicture() {
		return leftPicture;
	}
	public void setLeftPicture(ConsultationUserPicture leftPicture) {
		this.leftPicture = leftPicture;
	}
	public ConsultationUserPicture getRightPicture() {
		return rightPicture;
	}
	public void setRightPicture(ConsultationUserPicture rightPicture) {
		this.rightPicture = rightPicture;
	}

}
