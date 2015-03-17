package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.Account;
import com.redfire.nutrieduc.entities.user.User;

public interface UserService {
	String DUPLICATE_MESSAGE = "user.service.duplicate.record";
	
	User saveUserPatient (User user) throws BusinessException;
	
	User saveNutritionistUser (User user) throws BusinessException;
	
	User updateUser (User user, Long id) throws BusinessException;
	
	void removeUser (Long id) throws BusinessException;
	
	ServiceCollectionResponse<User> retrieveAccountUsers (ServiceRequest<User> request);
	
	User createUserAccount (User user) throws BusinessException;
	
	User createSocialMediaUser (User user, String appClient) throws BusinessException;

	ServiceCollectionResponse<User> retrieveUsers (ServiceRequest<User> request);
	
	ServiceCollectionResponse<User> searchNutritionistsUsers (ServiceRequest<User> request);
	
	User findByIdAndAccount (Long id, Account acount) throws BusinessException;
}
