package com.redfire.nutrieduc.chat;

import java.io.Serializable;

public class ChatMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3449234858534295197L;
	private String userA;
	private String userB;
	private String sender;
	private String value;
	
	public ChatMessage() {
		// TODO Auto-generated constructor stub
	}
	
	public String getUserA() {
		return userA;
	}
	public void setUserA(String userA) {
		this.userA = userA;
	}
	public String getUserB() {
		return userB;
	}
	public void setUserB(String userB) {
		this.userB = userB;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
