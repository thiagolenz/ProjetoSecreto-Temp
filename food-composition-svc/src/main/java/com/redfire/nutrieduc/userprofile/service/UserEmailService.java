package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.entities.user.ChangePasswordToken;

public interface UserEmailService {
	void sendChangePasswordRequest (ChangePasswordToken changePasswordToken);
}
