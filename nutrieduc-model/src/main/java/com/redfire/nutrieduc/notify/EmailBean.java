package com.redfire.nutrieduc.notify;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmailBean implements Serializable{
	private static final long serialVersionUID = 3646092033806148660L;

	private String subject;
	private List<String> toEmails;
	private String toName;
	private String content;
	private String fromName;
	private String fromEmail;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<String> getToEmails() {
		return toEmails;
	}
	public void setToEmails(List<String> toEmails) {
		this.toEmails = toEmails;
	}
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setToName(String toName) {
		this.toName = toName;
	}
	
	public String getToName() {
		return toName;
	}
	
	public void addToEmail (String email) {
		if (toEmails == null)
			toEmails = new ArrayList<>();
		this.toEmails.add(email);
	}
	public String getFromName() {
		return fromName;
	}
	
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	
	public String getFromEmail() {
		return fromEmail;
	}
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
}
