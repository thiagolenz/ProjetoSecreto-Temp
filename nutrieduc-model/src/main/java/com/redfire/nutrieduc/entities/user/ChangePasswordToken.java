package com.redfire.nutrieduc.entities.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.redfire.nutrieduc.entities.common.AbstractEntity;

@Entity
public class ChangePasswordToken extends AbstractEntity {
	@SequenceGenerator(name="seq_requestchangepassword",
			sequenceName="seq_requestchangepassword",
			allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="seq_requestchangepassword")
	@Id
	private Long id;
	private String token;
	private boolean newRequest;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean isNewRequest() {
		return newRequest;
	}
	
	public void setNewRequest(boolean newRequest) {
		this.newRequest = newRequest;
	}
}
