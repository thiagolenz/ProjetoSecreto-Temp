package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.entities.user.UserAvatar;

public interface UserAvatarService {
	UserAvatar findByUserId (Long id);
	void insert (UserAvatar avatar);
	void update (UserAvatar avatar, Long id);
}
