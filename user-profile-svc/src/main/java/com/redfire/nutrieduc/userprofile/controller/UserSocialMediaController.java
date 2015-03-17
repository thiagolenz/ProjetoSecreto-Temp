package com.redfire.nutrieduc.userprofile.controller;

import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.redfire.nutrieduc.common.Constants;

import com.google.common.base.Strings;
import com.redfire.nutrieduc.commonsvc.response.Response;
import com.redfire.nutrieduc.commonsvc.svc.controller.DefaultBeanValidator;
import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.commonsvc.svc.exception.MandatoryFieldException;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.entities.user.UserSocialMediaLogin;
import com.redfire.nutrieduc.userprofile.service.UserService;
import com.redfire.nutrieduc.userprofile.service.UserSocialMediaLoginService;

@Controller
@RequestMapping(value="/userSocialMedia", produces=MediaType.APPLICATION_JSON_VALUE)
public class UserSocialMediaController {
	
	public static final String SOCIAL_MEDIA = "user.socialmediaprovider";
	public static final String ACCESS_TOKEN = "user.accesstoken";
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Inject
	private DefaultBeanValidator beanValidator; 
	
	@Autowired
	private UserSocialMediaLoginService socialMediaLoginService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/createUser", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User createSocialMediaUser (@RequestBody @Valid User user, BindingResult result) throws BusinessException, MandatoryFieldException {
		beanValidator.validateMandatoryFields(result);
		String appToken = getAppToken();
		UserSocialMediaLogin socialMediaLogin = socialMediaLoginService.find(user);
		if (socialMediaLogin != null) {
			User existUser = copyFrom(user, socialMediaLogin);
			return socialMediaLoginService.create(existUser, appToken).getUser();
		} else {
			validateSocialMediaUser(user);
			user.setPassword(UUID.randomUUID().toString());
			return userService.createSocialMediaUser(user, appToken);
		}
	}

	@RequestMapping(value="/findExistentByLogin", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object findExistentByLogin (@RequestBody UserSocialMediaLogin socialMediaLogin) {
		socialMediaLogin.setAppClientToken(getAppToken());
		UserSocialMediaLogin result = socialMediaLoginService.findExistent(socialMediaLogin);
		if (result != null)
			return result;
		return Response.notFoundResponse();
	}
	
	private String getAppToken() {
		String appToken = httpServletRequest.getHeader(Constants.APP_CLIENT_KEY);
		return appToken;
	}
	

	private User copyFrom(User user, UserSocialMediaLogin socialMediaLogin) {
		User existUser = socialMediaLogin.getUser();
		existUser.setSocialMediaProvider(user.getSocialMediaProvider());
		existUser.setAccessToken(user.getAccessToken());
		return existUser;
	}
	
	private void validateSocialMediaUser (User user) throws MandatoryFieldException {
		if (user.getSocialMediaProvider() == null)
			throw new MandatoryFieldException(SOCIAL_MEDIA);
		if (Strings.isNullOrEmpty(user.getAccessToken()))
			throw new MandatoryFieldException(ACCESS_TOKEN);
	}
}
