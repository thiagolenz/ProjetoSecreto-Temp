package com.redfire.nutrieduc.userprofile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfire.nutrieduc.entities.user.UserInfoProfile;
import com.redfire.nutrieduc.userprofile.dao.UserInfoProfileDAO;
import com.redfire.nutrieduc.userprofile.service.UserInfoProfileService;

@Service
public class UserInfoProfileServiceImpl implements UserInfoProfileService {
	@Autowired
	private UserInfoProfileDAO infoProfileDAO;
	
	@Transactional
	public void insert(UserInfoProfile infoProfile) {
		infoProfileDAO.insert(infoProfile);
	}

	@Transactional
	public void update(UserInfoProfile infoProfile, Long id) {
		infoProfileDAO.update(infoProfile, id);
	}

	@Transactional
	public UserInfoProfile getFromUser(Long idUser) {
		return infoProfileDAO.findByUserId(idUser);
	}
	
	@Transactional
	public UserInfoProfile findByDocumentNumber(String documentNumber) {
		return infoProfileDAO.findByDocumentNumber(documentNumber);
	}

}
