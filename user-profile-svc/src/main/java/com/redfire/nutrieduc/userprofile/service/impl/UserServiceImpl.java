package com.redfire.nutrieduc.userprofile.service.impl;


import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfire.nutrieduc.commonsvc.secret.PasswordDigest;
import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.Account;
import com.redfire.nutrieduc.entities.account.AccountType;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.entities.user.UserType;
import com.redfire.nutrieduc.userprofile.dao.AccountDAO;
import com.redfire.nutrieduc.userprofile.dao.UserDAO;
import com.redfire.nutrieduc.userprofile.service.UserPasswordService;
import com.redfire.nutrieduc.userprofile.service.UserService;
import com.redfire.nutrieduc.userprofile.service.UserSocialMediaLoginService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private UserDAO userDAO;
	
	@Autowired 
	private PasswordDigest passwordDigest;
	
	@Autowired
	private UserSocialMediaLoginService userSocialMediaLoginService;
	
	@Autowired 
	private UserPasswordService userPasswordService;

	@Transactional
	public User saveUserPatient (User user) throws BusinessException {
		validateDuplicate(user);
		user.setUserType(UserType.PATIENT);
		if (user.isCreateFromNutritionist())
			user.setPassword(UUID.randomUUID().toString());
		createAndInsertAccount(user, AccountType.PATIENT);
		encryptAndInsertUser(user);	
		if (user.isCreateFromNutritionist())
			userPasswordService.createChangePasswordToken(user);
		return user;
	}
	
	@Transactional
	public User createSocialMediaUser(User user, String appClient) throws BusinessException {
		validateDuplicate(user);
		createAndInsertAccount(user, AccountType.PATIENT);
		encryptAndInsertUser(user);	
		userSocialMediaLoginService.create(user, appClient);
		return user;
	}

	@Transactional
	public User saveNutritionistUser(User user) throws BusinessException {
		validateDuplicate(user);
		user.setUserType(UserType.NUTRITIONIST);
		createAndInsertAccount(user, AccountType.NUTRITIONIST);
		encryptAndInsertUser(user);
		return user;
	}
	
	@Transactional
	public User updateUser(User user, Long id) throws BusinessException {
		validateDuplicate(user);
		userDAO.update(user, id);
		return user;
	}
	
	@Transactional
	public User createUserAccount (User user) throws BusinessException {
		validateDuplicate(user);
		encryptAndInsertUser(user);
		return user;
	}
	
	private void encryptAndInsertUser(User user) {
		user.setPassword(passwordDigest.encrypt(user.getPassword()));
		userDAO.insert(user);
	}

	private void createAndInsertAccount(User user, AccountType accountType) {
		Account account = new Account();
		account.setCompanyName(user.getName());
		account.setRegisterDate(new Date());
		account.setStatus("ACTIVE");
		account.setAccountType(accountType);
		accountDAO.insert(account);
		user.setAccount(account);
	}
	

	private void validateDuplicate(User user) throws BusinessException {
		if (userDAO.findDuplicate(user) != null) 
			throw new BusinessException(DUPLICATE_MESSAGE);
	}

	@Transactional
	public ServiceCollectionResponse<User> retrieveAccountUsers(ServiceRequest<User> request) {
		return userDAO.findAllByAccount(request);
	}
	
	@Transactional
	public ServiceCollectionResponse<User> retrieveUsers(ServiceRequest<User> request) {
		return userDAO.searchUsers(request);
	}

	@Transactional
	public ServiceCollectionResponse<User> searchNutritionistsUsers(ServiceRequest<User> request) {
		request.getEntity().setUserType(UserType.NUTRITIONIST);
		return userDAO.findAllByAccount(request);
	}

	@Transactional
	public void removeUser (Long id) throws BusinessException {
		User user = userDAO.findById(User.class, id);
		user.setRemoved(true);
		userDAO.update(user, id);
	}
	
	@Override
	public User findByIdAndAccount (Long id, Account acount) throws BusinessException{
		return userDAO.findById(User.class, id);
	}
}
