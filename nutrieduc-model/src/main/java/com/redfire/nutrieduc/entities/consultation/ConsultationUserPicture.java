package com.redfire.nutrieduc.entities.consultation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.redfire.nutrieduc.entities.common.AbstractEntity;

@Entity
public class ConsultationUserPicture extends AbstractEntity{
	@SequenceGenerator(name="seq_ConsultationUserPicture",
			sequenceName="seq_ConsultationUserPicture",
			allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="seq_ConsultationUserPicture")
	@Id
	private Long id;
	private String fileName;
	private Long userId;
	private String miniPictureBase64;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getMiniPictureBase64() {
		return miniPictureBase64;
	}
	public void setMiniPictureBase64(String miniPictureBase64) {
		this.miniPictureBase64 = miniPictureBase64;
	}
}
