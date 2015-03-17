package com.redfire.nutrieduc.userprofile.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfire.nutrieduc.commonsvc.secret.PasswordDigest;
import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.entities.user.ChangePasswordToken;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.userprofile.dao.ChangePasswordTokenDAO;
import com.redfire.nutrieduc.userprofile.dao.UserDAO;
import com.redfire.nutrieduc.userprofile.service.UserEmailService;
import com.redfire.nutrieduc.userprofile.service.UserPasswordService;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {
	@Autowired
	private ChangePasswordTokenDAO tokenDAO;
	
	@Autowired
	private PasswordDigest passwordDigest;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private UserEmailService emailService;

	@Transactional
	public ChangePasswordToken createChangePasswordToken(User user) {
		ChangePasswordToken passwordToken = createNew(user, true);
		tokenDAO.insert(passwordToken);
		emailService.sendChangePasswordRequest(passwordToken);
		return passwordToken;
	}
	
	@Transactional
	public ChangePasswordToken processRequestNew (String token) throws BusinessException {
		ChangePasswordToken passwordTokenOld = tokenDAO.findByToken(token, true);
		if (passwordTokenOld == null)
			throw new BusinessException(REQUEST_TOKEN_NOT_FOUND);
		ChangePasswordToken passwordTokenNew = createNew(passwordTokenOld.getUser(), false);
		tokenDAO.insert(passwordTokenNew);
		tokenDAO.remove(passwordTokenOld);
		return passwordTokenNew;
	}
	
	private ChangePasswordToken createNew(User user, boolean isNewRequest) {
		ChangePasswordToken passwordToken = new ChangePasswordToken();
		passwordToken.setUser(user);
		passwordToken.setNewRequest(isNewRequest);
		passwordToken.setToken(UUID.randomUUID().toString());
		return passwordToken;
	}
	
	@Transactional
	public void processFinalChange(String token, String password) throws BusinessException {
		ChangePasswordToken passwordToken = tokenDAO.findByToken(token, false);
		if (passwordToken == null)
			throw new BusinessException(REQUEST_TOKEN_NOT_FOUND);
		User user = passwordToken.getUser();
		user.setPassword(passwordDigest.encrypt(password));
		userDao.update(user, user.getId());
	}	
}
