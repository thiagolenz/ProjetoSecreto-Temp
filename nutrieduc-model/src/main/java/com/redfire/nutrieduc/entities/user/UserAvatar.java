package com.redfire.nutrieduc.entities.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.redfire.nutrieduc.entities.common.AbstractEntity;

@Entity
public class UserAvatar extends AbstractEntity {
	@SequenceGenerator(name="seq_user_avatar",
			sequenceName="seq_user_avatar",
			allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="seq_user_avatar")
	@Id
	private Long id;
	private Long userId;
	private String avatarBase64;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAvatarBase64() {
		return avatarBase64;
	}
	public void setAvatarBase64(String avatarBase64) {
		this.avatarBase64 = avatarBase64;
	}
}
