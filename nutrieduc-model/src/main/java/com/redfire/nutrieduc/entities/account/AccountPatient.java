package com.redfire.nutrieduc.entities.account;

import java.io.Serializable;

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
public class AccountPatient extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = -2962454612388685325L;
	@SequenceGenerator(name="seq_accountpatient",
			sequenceName="seq_accountpatient",
			allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="seq_accountpatient")
	@Id
	private Long id;
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User patientUser;
	
	private String avatarBase64;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public User getPatientUser() {
		return patientUser;
	}
	public void setPatientUser(User patientUser) {
		this.patientUser = patientUser;
	}
	public String getAvatarBase64() {
		return avatarBase64;
	}
	public void setAvatarBase64(String avatarBase64) {
		this.avatarBase64 = avatarBase64;
	}
}
