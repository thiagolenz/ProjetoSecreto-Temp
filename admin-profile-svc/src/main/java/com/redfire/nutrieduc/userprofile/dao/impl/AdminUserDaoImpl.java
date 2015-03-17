package com.redfire.nutrieduc.userprofile.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.entities.user.AdminUser;
import com.redfire.nutrieduc.userprofile.dao.AdminUserDao;
/**
 * 
 * @author thiagolenz
 * @since Sep 9, 2014
 *
 */
@Repository
@Transactional
public class AdminUserDaoImpl extends AbstractJpaDAOImpl<AdminUser> implements AdminUserDao {

	private static final String QUERY_LOGIN = "from AdminUser o where o.username = :username and o.password = :password";
	
	@Override
	public AdminUser findByUsernameAndLogin(AdminUser adminUser) {
		TypedQuery<AdminUser> findUserQuery = em.createQuery(QUERY_LOGIN, AdminUser.class);
		
		findUserQuery.setParameter("username", adminUser.getUsername());
		findUserQuery.setParameter("password", adminUser.getPassword());
		
		List<AdminUser> users = findUserQuery.getResultList();
		return users.isEmpty() ? null : users.get(0);
	}

}
