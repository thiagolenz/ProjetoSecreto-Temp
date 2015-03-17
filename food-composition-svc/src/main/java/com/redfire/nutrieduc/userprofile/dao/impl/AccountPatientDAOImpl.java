package com.redfire.nutrieduc.userprofile.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.Account;
import com.redfire.nutrieduc.entities.account.AccountPatient;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.userprofile.dao.AccountPatientDAO;

@Repository
public class AccountPatientDAOImpl extends AbstractJpaDAOImpl<AccountPatient> implements AccountPatientDAO {
	private static String QUERY_FIND_DUPLICATE = "from AccountPatient o where o.account = :account and o.patientUser = :user";
	private static String QUERY_FIND_PATIENTS = "from AccountPatient o where o.account = :account";

	@Override
	public AccountPatient findDuplicate(AccountPatient accountPatient) {
		StringBuilder builder = new StringBuilder(QUERY_FIND_DUPLICATE);
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("account", accountPatient.getAccount());
		parameters.put("user", accountPatient.getPatientUser());
		
		return findDuplicate(builder, parameters, AccountPatient.class);
	}

	@Override
	public ServiceCollectionResponse<AccountPatient> findPatients(ServiceRequest<AccountPatient> request) {
		Account account = em.find(Account.class, request.getAccount().getId());
		TypedQuery<AccountPatient> query = em.createQuery(QUERY_FIND_PATIENTS, AccountPatient.class);
		query.setParameter("account", account);
		return executeQueryForPagination(query, request);	
	}

	@Override
	public AccountPatient getByUser(User user) {
		TypedQuery<AccountPatient> query = em.createQuery("from AccountPatient o where o.patientUser.id = :user", AccountPatient.class);
		query.setParameter("user", user.getId());
		List<AccountPatient> result = query.getResultList();

		if (!result.isEmpty())
			return result.get(0);
		return null;
	}
}
