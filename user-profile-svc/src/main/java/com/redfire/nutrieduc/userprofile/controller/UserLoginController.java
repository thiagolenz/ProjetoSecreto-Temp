package com.redfire.nutrieduc.userprofile.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.redfire.nutrieduc.common.Constants;

import com.redfire.nutrieduc.commonsvc.svc.exception.LoginFailedException;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.userprofile.service.UserLoginService;

@Controller
@RequestMapping(value="/user/login",produces=MediaType.APPLICATION_JSON_VALUE)
public class UserLoginController {
	public static final String SOCIAL_MEDIA_HEADER = "rf-socialmediaprovider";
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@RequestMapping(method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User authenticate (@RequestBody User user) throws LoginFailedException {
		if (user.getSocialMediaProvider() != null){
			String appToken = httpServletRequest.getHeader(Constants.APP_CLIENT_KEY);
			return userLoginService.socialMediaLogin(user, appToken);
		}
		return userLoginService.login(user);
	}
	
	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}
	
	public void setUserLoginService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}
}
