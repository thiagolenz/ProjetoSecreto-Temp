package com.redfire.nutrieduc.userprofile.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.entities.user.UserInfoProfile;
import com.redfire.nutrieduc.userprofile.dao.UserInfoProfileDAO;

@Repository
public class UserInfoProfileDaoImpl extends AbstractJpaDAOImpl<UserInfoProfile>
		implements UserInfoProfileDAO {
	
	private static final String QUERY_DOCUMENT_NUMBER = "from UserInfoProfile o where lower (o.documentNumber) = :documentNumber";

	@Override
	public UserInfoProfile findByUserId(Long id) {
		TypedQuery<UserInfoProfile> query = em.createQuery("from UserInfoProfile o where o.user.id = :userId", UserInfoProfile.class);
		query.setParameter("userId", id);
		List<UserInfoProfile> result = query.getResultList();
		if (result.isEmpty())
			return null;
		return result.get(0);
	}

	@Override
	public UserInfoProfile findByDocumentNumber(String documentNumber) {
		TypedQuery<UserInfoProfile> query = em.createQuery(QUERY_DOCUMENT_NUMBER, UserInfoProfile.class);
		query.setParameter("documentNumber", documentNumber.toLowerCase());
		List<UserInfoProfile> result = query.getResultList();
		if (result.isEmpty())
			return null;
		return result.get(0);
	}

}
