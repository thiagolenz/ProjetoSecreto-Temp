package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.commonsvc.svc.exception.LoginFailedException;
import com.redfire.nutrieduc.entities.user.AdminUser;
/**
 * 
 * @author thiagolenz
 * @since Sep 9, 2014
 *
 */
public interface AdminLoginService {
	AdminUser login(AdminUser adminUser) throws LoginFailedException;
}
