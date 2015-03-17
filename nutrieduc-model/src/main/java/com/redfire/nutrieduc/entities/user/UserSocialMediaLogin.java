package com.redfire.nutrieduc.entities.user;

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

@Entity
public class UserSocialMediaLogin extends AbstractEntity {
	@SequenceGenerator(name="seq_usersocialmedialogin",
			sequenceName="seq_usersocialmedialogin",
			allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="seq_usersocialmedialogin")
	@Id
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Enumerated(EnumType.STRING)
	private SocialMediaProvider socialMediaProvider;
	
	private String appClientToken;
	
	private String accessToken;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public SocialMediaProvider getSocialMediaProvider() {
		return socialMediaProvider;
	}
	public void setSocialMediaProvider(SocialMediaProvider socialMediaProvider) {
		this.socialMediaProvider = socialMediaProvider;
	}
	public String getAppClientToken() {
		return appClientToken;
	}
	public void setAppClientToken(String appClientToken) {
		this.appClientToken = appClientToken;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
