package com.redfire.nutrieduc.userprofile.dao;

import com.redfire.nutrieduc.commonsvc.svc.dao.GenericDAO;
import com.redfire.nutrieduc.entities.user.AdminUser;
/**
 * 
 * @author thiagolenz
 * @since Sep 9, 2014
 *
 */
public interface AdminUserDao extends GenericDAO<AdminUser>{
	AdminUser findByUsernameAndLogin (AdminUser adminUser);
}
