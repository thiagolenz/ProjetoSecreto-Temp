package com.redfire.nutrieduc.userprofile.dao;

import com.redfire.nutrieduc.commonsvc.svc.dao.GenericDAO;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.AccountPatient;
import com.redfire.nutrieduc.entities.user.User;

public interface AccountPatientDAO extends GenericDAO<AccountPatient> {
	AccountPatient findDuplicate (AccountPatient accountPatient);
	ServiceCollectionResponse<AccountPatient> findPatients (ServiceRequest<AccountPatient> request);
	AccountPatient getByUser (User user);
}
