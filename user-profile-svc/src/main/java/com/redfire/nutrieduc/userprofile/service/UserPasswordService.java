package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.entities.user.ChangePasswordToken;
import com.redfire.nutrieduc.entities.user.User;

public interface UserPasswordService {
	String REQUEST_TOKEN_NOT_FOUND = "user.password.change.request.token.not.found";
	ChangePasswordToken createChangePasswordToken (User user);
	ChangePasswordToken processRequestNew (String token) throws BusinessException;
	void processFinalChange (String token, String password) throws BusinessException;
}
