package com.redfire.nutrieduc.nutrieducweb.servlet.menu;

import java.util.ArrayList;
import java.util.List;

import com.redfire.nutrieduc.entities.user.UserType;

public class MenuItem {
	private String menuLabel;
	private String moduleId;
	private String type;
	private List<MenuItem> subMenus;
	private List<UserType> userTypes;
	
	public String getMenuLabel() {
		return menuLabel;
	}
	public void setMenuLabel(String menuLabel) {
		this.menuLabel = menuLabel;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<MenuItem> getSubMenus() {
		if (subMenus == null)
			subMenus = new ArrayList<>();
		return subMenus;
	}
	public void setSubMenus(List<MenuItem> subMenus) {
		this.subMenus = subMenus;
	}
	public List<UserType> getUserTypes() {
		if (userTypes == null)
			userTypes = new ArrayList<>();
		return userTypes;
	}
	public void setUserTypes(List<UserType> userTypes) {
		this.userTypes = userTypes;
	}
}	
