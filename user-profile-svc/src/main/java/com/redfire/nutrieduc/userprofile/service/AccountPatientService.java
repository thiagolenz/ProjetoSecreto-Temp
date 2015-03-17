package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.Account;
import com.redfire.nutrieduc.entities.account.AccountPatient;
import com.redfire.nutrieduc.entities.user.User;

public interface AccountPatientService {
	String DUPLICATE = "account.patient.service.duplicate.record";
	
	AccountPatient bindUserPatient(User user, Account account) throws BusinessException;
	
	ServiceCollectionResponse<AccountPatient> findPatients (ServiceRequest<AccountPatient> request);
	
	ServiceCollectionResponse<User> searchPatientsUsers (ServiceRequest<User> request);
	
	void removeAccountPatient (Long id) throws BusinessException;
	
	AccountPatient getByUser (Long id);
	
	ServiceCollectionResponse<User> searchOfficeUsers (ServiceRequest<User> request);
	
	void update (Long id, AccountPatient accountPatient);
}
