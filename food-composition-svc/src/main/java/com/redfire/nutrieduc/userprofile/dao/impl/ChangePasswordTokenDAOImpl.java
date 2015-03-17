package com.redfire.nutrieduc.userprofile.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.entities.user.ChangePasswordToken;
import com.redfire.nutrieduc.userprofile.dao.ChangePasswordTokenDAO;

@Repository
public class ChangePasswordTokenDAOImpl extends
		AbstractJpaDAOImpl<ChangePasswordToken> implements
		ChangePasswordTokenDAO {
	
	private static final String QUERY = "from ChangePasswordToken o where o.token = :token and newRequest is :newRequest";
	
	@Override
	public ChangePasswordToken findByToken(String token, boolean isNewRequest) {
		TypedQuery<ChangePasswordToken> findTokenQuery = em.createQuery(QUERY, ChangePasswordToken.class);
		
		findTokenQuery.setParameter("token", token);
		findTokenQuery.setParameter("newRequest", isNewRequest);
		
		List<ChangePasswordToken> tokens = findTokenQuery.getResultList();
		return tokens.isEmpty() ? null : tokens.get(0);
	}

}
