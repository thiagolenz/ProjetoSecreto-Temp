package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.Account;

public interface AccountService {
	ServiceCollectionResponse<Account> retrieveNutritionistAccounts (ServiceRequest<Account> request); 
}
