package com.redfire.nutrieduc.userprofile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redfire.nutrieduc.commonsvc.secret.PasswordDigest;
import com.redfire.nutrieduc.commonsvc.svc.exception.LoginFailedException;
import com.redfire.nutrieduc.entities.user.AdminUser;
import com.redfire.nutrieduc.userprofile.dao.AdminUserDao;
import com.redfire.nutrieduc.userprofile.service.AdminLoginService;
/**
 * 
 * @author thiagolenz
 * @since Sep 9, 2014
 *
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {
	@Autowired
	private AdminUserDao adminDao;
	
	@Autowired
	private PasswordDigest passwordDigest;

	@Override
	public AdminUser login(AdminUser adminUser) throws LoginFailedException {
		verifyFilledUsernameAndPassword(adminUser);
		adminUser.setPassword(passwordDigest.encrypt(adminUser.getPassword()));
		adminUser = adminDao.findByUsernameAndLogin(adminUser);
		if (adminUser == null)
			throw new LoginFailedException();
		return adminUser;
	}
	
	public void verifyFilledUsernameAndPassword (AdminUser adminUser) throws LoginFailedException {
		if (adminUser == null || adminUser.getUsername() == null || adminUser.getPassword() == null)
			throw new LoginFailedException();
	}

}
