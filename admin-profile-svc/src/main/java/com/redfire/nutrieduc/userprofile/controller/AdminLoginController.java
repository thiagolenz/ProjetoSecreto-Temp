package com.redfire.nutrieduc.userprofile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfire.nutrieduc.commonsvc.svc.exception.LoginFailedException;
import com.redfire.nutrieduc.entities.user.AdminUser;
import com.redfire.nutrieduc.userprofile.service.AdminLoginService;

/**
 * 
 * @author thiagolenz
 * @since Sep 9, 2014
 *
 */
@Controller
@RequestMapping(value="/admin/login",produces=MediaType.APPLICATION_JSON_VALUE)
public class AdminLoginController {
	
	@Autowired
	private AdminLoginService adminLoginService;
	
	@RequestMapping(method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AdminUser authenticate (@RequestBody AdminUser adminUser) throws LoginFailedException {
		return adminLoginService.login(adminUser);
	}
}
