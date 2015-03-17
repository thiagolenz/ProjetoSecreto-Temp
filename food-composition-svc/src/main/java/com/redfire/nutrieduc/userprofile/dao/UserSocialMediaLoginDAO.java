package com.redfire.nutrieduc.userprofile.dao;

import com.redfire.nutrieduc.commonsvc.svc.dao.GenericDAO;
import com.redfire.nutrieduc.entities.user.SocialMediaProvider;
import com.redfire.nutrieduc.entities.user.UserSocialMediaLogin;

public interface UserSocialMediaLoginDAO extends GenericDAO<UserSocialMediaLogin> {
	UserSocialMediaLogin findByTokenAndSocialMedia (String accessToken, SocialMediaProvider mediaProvider);
	UserSocialMediaLogin findByLoginAndSocialMedia (String email, SocialMediaProvider mediaProvider);
	UserSocialMediaLogin findDuplicate (UserSocialMediaLogin mediaLogin); 
	UserSocialMediaLogin findExistentByLogin(UserSocialMediaLogin mediaLogin);
}
