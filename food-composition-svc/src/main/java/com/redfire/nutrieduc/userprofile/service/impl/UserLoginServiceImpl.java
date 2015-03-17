package com.redfire.nutrieduc.userprofile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.redfire.nutrieduc.commonsvc.secret.PasswordDigest;
import com.redfire.nutrieduc.commonsvc.svc.exception.LoginFailedException;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.entities.user.UserSocialMediaLogin;
import com.redfire.nutrieduc.userprofile.dao.UserDAO;
import com.redfire.nutrieduc.userprofile.dao.UserSocialMediaLoginDAO;
import com.redfire.nutrieduc.userprofile.service.UserLoginService;

@Service
public class UserLoginServiceImpl implements UserLoginService {
	@Autowired
	private UserDAO userProfileDao;
	
	@Autowired
	private PasswordDigest passwordDigest;
	
	@Autowired
	private UserSocialMediaLoginDAO socialMediaLoginDAO;

	@Transactional
	public User login (User user) throws LoginFailedException {
		verifyFilledUsernameAndPassword(user);
		user.setPassword(passwordDigest.encrypt(user.getPassword()));
		user = userProfileDao.findByUsernameAndLogin(user);
		if (user == null)
			throw new LoginFailedException();
		return user;
	}
	
	public void verifyFilledUsernameAndPassword (User user) throws LoginFailedException {
		if (user == null || user.getEmail() == null || user.getPassword() == null)
			throw new LoginFailedException();
	}

	@Transactional
	public User socialMediaLogin (User user, String appToken) throws LoginFailedException {
		validateSocialMediaLogin(user, appToken);
		UserSocialMediaLogin userMedia = socialMediaLoginDAO.findByTokenAndSocialMedia(user.getAccessToken(), user.getSocialMediaProvider());
		if (userMedia == null)
			throw new LoginFailedException();
		return userMedia.getUser();	
	}

	private void validateSocialMediaLogin(User user, String appToken) throws LoginFailedException {
		if (Strings.isNullOrEmpty(user.getAccessToken()) 
				|| user.getSocialMediaProvider() == null 
				|| Strings.isNullOrEmpty(appToken))
			throw new LoginFailedException();
	}

}
