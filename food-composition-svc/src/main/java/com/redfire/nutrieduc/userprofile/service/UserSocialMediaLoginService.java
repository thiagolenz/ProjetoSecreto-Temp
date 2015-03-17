package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.entities.user.UserSocialMediaLogin;

public interface UserSocialMediaLoginService {
	String DUPLICATE_MESSAGE = "user.socialmedialogin.service.duplicate";
	UserSocialMediaLogin find (User user);
	UserSocialMediaLogin create (User user, String appClient) throws BusinessException;
	UserSocialMediaLogin findExistent (UserSocialMediaLogin socialMediaLogin);
}
