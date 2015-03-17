package com.redfire.nutrieduc.commonsvc.svc.io;

import java.io.Serializable;

/**
 * Generic Bean of enum response. Translated description and Enum Value.
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 */
public class EnumSerializableBean implements Serializable{
	private static final long serialVersionUID = -7267381261596488280L;
	private Object enumValue;
	private Integer code;
	private String description;
	
	public EnumSerializableBean() {
	}
	
	public EnumSerializableBean(Object enumValue, Integer code, String description) {
		super();
		this.enumValue = enumValue;
		this.code = code;
		this.description = description;
	}
	
	public Object getEnumValue() {
		return enumValue;
	}
	public void setEnumValue(Object enumValue) {
		this.enumValue = enumValue;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
