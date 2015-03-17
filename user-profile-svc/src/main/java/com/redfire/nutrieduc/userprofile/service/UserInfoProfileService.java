package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.entities.user.UserInfoProfile;

public interface UserInfoProfileService {
	void insert (UserInfoProfile infoProfile);
	void update (UserInfoProfile infoProfile, Long id);
	UserInfoProfile getFromUser (Long idUser);
	UserInfoProfile findByDocumentNumber (String documentNumber); 
}
