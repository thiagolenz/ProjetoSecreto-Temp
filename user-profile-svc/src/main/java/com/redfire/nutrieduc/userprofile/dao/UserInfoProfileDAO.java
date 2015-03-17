package com.redfire.nutrieduc.userprofile.dao;

import com.redfire.nutrieduc.commonsvc.svc.dao.GenericDAO;
import com.redfire.nutrieduc.entities.user.UserInfoProfile;

public interface UserInfoProfileDAO extends GenericDAO<UserInfoProfile> {
	UserInfoProfile findByUserId (Long id);
	UserInfoProfile findByDocumentNumber (String documentNumber); 
}
