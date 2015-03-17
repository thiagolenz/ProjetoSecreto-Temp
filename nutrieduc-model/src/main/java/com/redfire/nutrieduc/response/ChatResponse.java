package com.redfire.nutrieduc.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redfire.nutrieduc.entities.user.User;

public class ChatResponse {
	private Map<String, List<User>> groupUsers;
	
	public Map<String, List<User>> getGroupUsers() {
		if (groupUsers == null)
			groupUsers = new HashMap<String, List<User>>();
		return groupUsers;
	}
	
	public void setGroupUsers(Map<String, List<User>> groupUsers) {
		this.groupUsers = groupUsers;
	}
}
