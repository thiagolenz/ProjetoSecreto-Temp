package com.redfire.nutrieduc.userprofile.dao;

import com.redfire.nutrieduc.commonsvc.svc.dao.GenericDAO;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.Account;

public interface AccountDAO extends GenericDAO<Account> {
	ServiceCollectionResponse<Account> retrieveNutritionistAccounts(ServiceRequest<Account> request) ;
}
