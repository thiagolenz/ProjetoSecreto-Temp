package com.redfire.nutrieduc.userprofile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.Account;
import com.redfire.nutrieduc.entities.account.AccountPatient;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.userprofile.dao.AccountPatientDAO;
import com.redfire.nutrieduc.userprofile.dao.UserDAO;
import com.redfire.nutrieduc.userprofile.service.AccountPatientService;

@Service
public class AccountPatientServiceImpl implements AccountPatientService {
	@Autowired
	private AccountPatientDAO accountPatientDAO;
	
	@Autowired
	private UserDAO userDao;

	@Transactional
	public AccountPatient bindUserPatient(User user, Account account) throws BusinessException {
		AccountPatient accountPatient = new AccountPatient();
		accountPatient.setAccount(account);
		accountPatient.setPatientUser(user);
		validateDuplicate(accountPatient);
		accountPatientDAO.insert(accountPatient);
		return accountPatient;
	}
	
	private void validateDuplicate (AccountPatient accountPatient) throws BusinessException {
		if (accountPatientDAO.findDuplicate(accountPatient) != null)
			throw new BusinessException(DUPLICATE);
	}

	@Transactional
	public ServiceCollectionResponse<AccountPatient> findPatients(ServiceRequest<AccountPatient> request) {
		return accountPatientDAO.findPatients(request);
	}
	
	@Transactional
	public void removeAccountPatient(Long id) throws BusinessException {
		accountPatientDAO.remove(accountPatientDAO.findById(AccountPatient.class, id));
	}

	@Transactional
	public ServiceCollectionResponse<User> searchPatientsUsers(ServiceRequest<User> request) {
		return userDao.searchPatientsUsers(request);
	}

	@Transactional
	public ServiceCollectionResponse<User> searchOfficeUsers(ServiceRequest<User> request) {
		AccountPatient accountPatient = accountPatientDAO.getByUser(request.getEntity());
		if (accountPatient != null) {
			ServiceRequest<User> requestAccount = new ServiceRequest<>();
			requestAccount.setAccount(accountPatient.getAccount());
			requestAccount.setEntity(new User());
			return userDao.findAllByAccount(requestAccount);
		}
		return new ServiceCollectionResponse<>();
	}

}
