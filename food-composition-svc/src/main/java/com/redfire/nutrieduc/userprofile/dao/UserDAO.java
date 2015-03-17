package com.redfire.nutrieduc.userprofile.dao;

import com.redfire.nutrieduc.commonsvc.svc.dao.GenericDAO;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.user.User;

public interface UserDAO extends GenericDAO<User> {
	User findDuplicate (User user);
	User findByUsernameAndLogin (User user);
	ServiceCollectionResponse<User> findAllByAccount (ServiceRequest<User> request);
	ServiceCollectionResponse<User> searchUsers (ServiceRequest<User> request);
	ServiceCollectionResponse<User> searchPatientsUsers (ServiceRequest<User> request);
}
