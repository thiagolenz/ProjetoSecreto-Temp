package com.redfire.nutrieduc.appsession.servlets;

public class UserLoginStorageBean {
	private Object bean;
	private boolean isAdmin;
	
	public UserLoginStorageBean(Object bean, boolean isAdmin) {
		super();
		this.bean = bean;
		this.isAdmin = isAdmin;
	}
	public Object getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = bean;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
