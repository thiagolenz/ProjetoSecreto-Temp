package com.redfire.nutrieduc.userprofile.dao;

import com.redfire.nutrieduc.commonsvc.svc.dao.GenericDAO;
import com.redfire.nutrieduc.entities.user.UserAvatar;

public interface UserAvatarDAO extends GenericDAO<UserAvatar> {
	UserAvatar findByUserId (Long id);
}
