package com.redfire.nutrieduc.userprofile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.commonsvc.svc.exception.MandatoryFieldException;
import com.redfire.nutrieduc.entities.user.UserInfoProfile;
import com.redfire.nutrieduc.userprofile.service.UserInfoProfileService;

@Controller
@RequestMapping(value="/userInfoProfile",produces=MediaType.APPLICATION_JSON_VALUE)
public class UserInfoProfileController {
	@Autowired
	private UserInfoProfileService infoProfileService;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ResponseBody
	public UserInfoProfile getByUserId (@PathVariable("id") Long id) {
		UserInfoProfile infoProfile = infoProfileService.getFromUser(id);
		if (infoProfile == null)
			infoProfile = new UserInfoProfile();
		return infoProfile;
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserInfoProfile save(@RequestBody UserInfoProfile infoProfile) {
		infoProfileService.insert(infoProfile);
		return infoProfile;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserInfoProfile update(@RequestBody UserInfoProfile infoProfile, @PathVariable("id") Long id) {
		infoProfileService.update(infoProfile, id);
		return infoProfile;
	}
	
	@RequestMapping(value="/getByDocumentNumber", method = RequestMethod.POST)
	@ResponseBody
	public UserInfoProfile updateUser(@RequestBody UserInfoProfile userInfoProfile) throws MandatoryFieldException, BusinessException {
		UserInfoProfile user = infoProfileService.findByDocumentNumber(userInfoProfile.getDocumentNumber());
		if (user == null)
			user = new UserInfoProfile();
		return user;
	}
}
