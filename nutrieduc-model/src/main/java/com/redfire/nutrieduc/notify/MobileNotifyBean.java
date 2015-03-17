package com.redfire.nutrieduc.notify;

import java.util.ArrayList;
import java.util.List;

public class MobileNotifyBean {
	private List<String> regIdList;
	private NotifyMessageType messageType;
	private String message;
	
	public List<String> getRegIdList() {
		if (regIdList == null)
			regIdList = new ArrayList<>();
		return regIdList;
	}
	public void setRegIdList(List<String> regIdList) {
		this.regIdList = regIdList;
	}
	public NotifyMessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(NotifyMessageType messageType) {
		this.messageType = messageType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
