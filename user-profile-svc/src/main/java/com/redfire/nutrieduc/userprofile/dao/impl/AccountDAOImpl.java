package com.redfire.nutrieduc.userprofile.dao.impl;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.Account;
import com.redfire.nutrieduc.entities.account.AccountType;
import com.redfire.nutrieduc.userprofile.dao.AccountDAO;

@Repository
public class AccountDAOImpl extends AbstractJpaDAOImpl<Account> implements AccountDAO {
	private static final String QUERY_NUTRI = "from Account o where o.accountType = :accountType";
	
	@Override
	public ServiceCollectionResponse<Account> retrieveNutritionistAccounts(ServiceRequest<Account> request) {
		TypedQuery<Account> query = em.createQuery(QUERY_NUTRI, Account.class);
		query.setParameter("accountType", AccountType.NUTRITIONIST);
		return executeQueryForPagination(query, request);
	}

}
