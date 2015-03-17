package com.redfire.nutrieduc.userprofile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfire.nutrieduc.entities.user.UserAvatar;
import com.redfire.nutrieduc.userprofile.dao.UserAvatarDAO;
import com.redfire.nutrieduc.userprofile.service.UserAvatarService;

@Service
public class UserAvatarServiceImpl implements UserAvatarService {
	@Autowired
	private UserAvatarDAO avatarDAO;

	@Transactional
	public UserAvatar findByUserId(Long id) {
		return avatarDAO.findByUserId(id);
	}

	@Transactional
	public void insert(UserAvatar avatar) {
		avatarDAO.insert(avatar);
	}

	@Transactional
	public void update(UserAvatar avatar, Long id) {
		avatarDAO.update(avatar, id);
	}

}
