package com.redfire.nutrieduc.userprofile.dao;

import com.redfire.nutrieduc.commonsvc.svc.dao.GenericDAO;
import com.redfire.nutrieduc.entities.user.ChangePasswordToken;

public interface ChangePasswordTokenDAO extends GenericDAO<ChangePasswordToken> {
	ChangePasswordToken findByToken (String token, boolean isNewRequest);
}
