package com.redfire.nutrieduc.userprofile.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.entities.user.SocialMediaProvider;
import com.redfire.nutrieduc.entities.user.UserSocialMediaLogin;
import com.redfire.nutrieduc.userprofile.dao.UserSocialMediaLoginDAO;

@Repository
public class UserSocialMediaLoginDAOImpl extends
		AbstractJpaDAOImpl<UserSocialMediaLogin> implements
		UserSocialMediaLoginDAO {

	private static final String QUERY_LOGIN = "from UserSocialMediaLogin o " +
			" where o.accessToken = :accessToken and o.socialMediaProvider = :provider";
	
	private static final String QUERY_FIND = "from UserSocialMediaLogin o " +
			" where o.user.login = :login and o.socialMediaProvider = :provider";
	
	private static String QUERY_FIND_DUPLICATE = "from UserSocialMediaLogin o where o.user = :user " +
			" and o.appClientToken = :appClientToken and o.socialMediaProvider =:provider";
	
	private static String QUERY_FIND_EXISTENT_BY_LOGIN = "from UserSocialMediaLogin o where o.user.login = :login " +
			" and o.appClientToken = :appClientToken and o.socialMediaProvider =:provider";

	@Override
	public UserSocialMediaLogin findByTokenAndSocialMedia(String accessToken,
			SocialMediaProvider mediaProvider) {
		TypedQuery<UserSocialMediaLogin> findUserQuery = em.createQuery(QUERY_LOGIN, UserSocialMediaLogin.class);
		
		findUserQuery.setParameter("accessToken", accessToken);
		findUserQuery.setParameter("provider", mediaProvider);
		
		List<UserSocialMediaLogin> users = findUserQuery.getResultList();
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public UserSocialMediaLogin findByLoginAndSocialMedia(String login,
			SocialMediaProvider mediaProvider) {
		TypedQuery<UserSocialMediaLogin> findUserQuery = em.createQuery(QUERY_FIND, UserSocialMediaLogin.class);
		
		findUserQuery.setParameter("login", login);
		findUserQuery.setParameter("provider", mediaProvider);
		
		List<UserSocialMediaLogin> users = findUserQuery.getResultList();
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public UserSocialMediaLogin findDuplicate(UserSocialMediaLogin mediaLogin) {
		StringBuilder builder = new StringBuilder(QUERY_FIND_DUPLICATE);
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("user", mediaLogin.getUser());
		parameters.put("appClientToken", mediaLogin.getAppClientToken());
		parameters.put("provider", mediaLogin.getSocialMediaProvider());
		
		return findDuplicate(builder, parameters, UserSocialMediaLogin.class);
	}
	
	@Override
	public UserSocialMediaLogin findExistentByLogin(UserSocialMediaLogin mediaLogin) {
		StringBuilder builder = new StringBuilder(QUERY_FIND_EXISTENT_BY_LOGIN);
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("login", mediaLogin.getUser().getEmail());
		parameters.put("appClientToken", mediaLogin.getAppClientToken());
		parameters.put("provider", mediaLogin.getSocialMediaProvider());
		
		return findDuplicate(builder, parameters, UserSocialMediaLogin.class);
	}

}
