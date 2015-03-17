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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.redfire.nutrieduc.entities.account.Account;
import com.redfire.nutrieduc.entities.common.AbstractEntity;

@Entity
@Table(name="users")
public class User extends AbstractEntity {
	@SequenceGenerator(name="seq_users",
			sequenceName="seq_users",
			allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="seq_users")
	@Id
	private Long id;
	
	@NotNull @Size(min=5, max=200)
	private String name;
	
	@Email @NotNull @NotEmpty
	private String email;
	
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	private String lang;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	@Transient
	private String accessToken;
	
	@Transient
	private SocialMediaProvider socialMediaProvider;
	
	@Transient
	private boolean createFromNutritionist;
	
	@Transient
	private String letter;
	
	private boolean removed;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String login) {
		this.email = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getLang() {
		return lang;
	}
	
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public UserType getUserType() {
		return userType;
	}
	
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public SocialMediaProvider getSocialMediaProvider() {
		return socialMediaProvider;
	}
	
	public void setSocialMediaProvider(SocialMediaProvider socialMediaProvider) {
		this.socialMediaProvider = socialMediaProvider;
	}
	
	public boolean isCreateFromNutritionist() {
		return createFromNutritionist;
	}
	
	public void setCreateFromNutritionist(boolean createFromNutritionist) {
		this.createFromNutritionist = createFromNutritionist;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
	
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
}
