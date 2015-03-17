package com.redfire.nutrieduc.userprofile.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.entities.user.UserAvatar;
import com.redfire.nutrieduc.userprofile.dao.UserAvatarDAO;

@Repository
public class UserAvatarDaoImpl extends AbstractJpaDAOImpl<UserAvatar> implements UserAvatarDAO {

	@Override
	public UserAvatar findByUserId(Long id) {
		TypedQuery<UserAvatar> query = em.createQuery("from UserAvatar o where o.userId = :userId", UserAvatar.class);
		query.setParameter("userId", id);
		List<UserAvatar> result = query.getResultList();
		if (result.isEmpty())
			return null;
		return result.get(0);
	}

}
