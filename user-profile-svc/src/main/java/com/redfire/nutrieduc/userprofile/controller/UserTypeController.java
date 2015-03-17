package com.redfire.nutrieduc.userprofile.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfire.nutrieduc.commonsvc.i18n.MessageBundleService;
import com.redfire.nutrieduc.commonsvc.svc.io.EnumSerializableBean;
import com.redfire.nutrieduc.entities.user.UserType;

@Controller
@RequestMapping(value="/userType",produces=MediaType.APPLICATION_JSON_VALUE)

public class UserTypeController {
	@Autowired
	private MessageBundleService messageBundleService;

	@RequestMapping(value = "/retrieveTypes", method = RequestMethod.GET)
	public @ResponseBody List<EnumSerializableBean> retrieveStatus () throws IOException {
		List<EnumSerializableBean> list = new ArrayList<>();
		for (UserType type : UserType.values()) {
			list.add(new EnumSerializableBean(type, 
											  type.getValue(), 
											  messageBundleService.translate(type.getDescription())));
		}
		return list;
	}
}
