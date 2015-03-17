package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.commonsvc.svc.exception.LoginFailedException;
import com.redfire.nutrieduc.entities.user.User;

public interface UserLoginService {
	User login (User user) throws LoginFailedException;
	User socialMediaLogin (User user, String appToken) throws LoginFailedException;
}
