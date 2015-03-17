package com.redfire.nutrieduc.userprofile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.Account;
import com.redfire.nutrieduc.userprofile.dao.AccountDAO;
import com.redfire.nutrieduc.userprofile.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDAO accountDAO;
	
	@Transactional
	public ServiceCollectionResponse<Account> retrieveNutritionistAccounts(ServiceRequest<Account> request) {
		return accountDAO.retrieveNutritionistAccounts(request);
	}

}
