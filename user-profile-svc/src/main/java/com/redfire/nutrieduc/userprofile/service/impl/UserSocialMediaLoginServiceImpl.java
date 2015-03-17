package com.redfire.nutrieduc.userprofile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.entities.user.UserSocialMediaLogin;
import com.redfire.nutrieduc.userprofile.dao.UserSocialMediaLoginDAO;
import com.redfire.nutrieduc.userprofile.service.UserSocialMediaLoginService;

@Service
public class UserSocialMediaLoginServiceImpl implements UserSocialMediaLoginService {

	@Autowired
	private UserSocialMediaLoginDAO mediaLoginDAO;
	
	@Transactional
	public UserSocialMediaLogin find(User user) {
		return mediaLoginDAO.findByLoginAndSocialMedia(user.getEmail(), user.getSocialMediaProvider());
	}

	@Transactional
	public UserSocialMediaLogin create(User user, String appClient) throws BusinessException {
		UserSocialMediaLogin mediaLogin = new UserSocialMediaLogin();
		mediaLogin.setAccessToken(user.getAccessToken());
		mediaLogin.setSocialMediaProvider(user.getSocialMediaProvider());
		mediaLogin.setUser(user);
		mediaLogin.setAppClientToken(appClient);
		if (mediaLoginDAO.findDuplicate(mediaLogin) != null)
			throw new BusinessException(DUPLICATE_MESSAGE);
		mediaLoginDAO.insert(mediaLogin);
		return mediaLogin;
	}

	@Transactional
	public UserSocialMediaLogin findExistent(UserSocialMediaLogin socialMediaLogin) {
		return mediaLoginDAO.findExistentByLogin(socialMediaLogin);
	}

}
