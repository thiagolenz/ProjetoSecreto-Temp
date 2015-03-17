package com.redfire.nutrieduc.userprofile.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.Account;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.userprofile.dao.UserDAO;

@Repository
public class UserDAOImpl extends AbstractJpaDAOImpl<User> implements UserDAO {
	private static String QUERY_FIND_DUPLICATE = "from User o where o.email = :email and o.removed is false ";
	private static final String QUERY_LOGIN = "from User o where o.email = :email and o.password = :password and o.removed is false ";
	private static final String QUERY_FIND_BY_ACCOUNT = "from User o where o.account = :account and o.removed is false ";
	private static final String QUERY_FIND_USERS = "from User o where lower(o.name) like :name or lower(o.email) like :name and o.removed is false order by lower(o.name) ";
	private static String QUERY_SEARCH_PATIENTS_USERS = "select u from AccountPatient ap inner join ap.patientUser u " +
			" where ap.account = :account and " +
			" (lower(u.name) like :name or lower(u.email) like :name) and o.removed is false";

	@Override
	public User findDuplicate(User user) {
		StringBuilder builder = new StringBuilder(QUERY_FIND_DUPLICATE);
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("email", user.getEmail());
		addIdWhereClause(user, builder, parameters);
		
		return findDuplicate(builder, parameters, User.class);
	}
	
	@Override
	public User findByUsernameAndLogin(User adminUser) {
		TypedQuery<User> findUserQuery = em.createQuery(QUERY_LOGIN, User.class);
		
		findUserQuery.setParameter("email", adminUser.getEmail());
		findUserQuery.setParameter("password", adminUser.getPassword());
		
		List<User> users = findUserQuery.getResultList();
		return users.isEmpty() ? null : users.get(0);
	}
	
	@Override
	public ServiceCollectionResponse<User> findAllByAccount (ServiceRequest<User> request) {
		Account account = em.find(Account.class, request.getAccount().getId());
		User user = request.getEntity();
		StringBuilder strQuery = new StringBuilder(QUERY_FIND_BY_ACCOUNT);
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		addUserTypeWhereClause(user, strQuery, parameters);
		addNameWhereClause(request, user, strQuery, parameters);
		
		parameters.put("account", account);
		addOrderBy(strQuery);
		TypedQuery<User> query = createQueryAndSetParams(strQuery, parameters, User.class);
		return executeQueryForPagination(query, request);
	}

	private void addNameWhereClause(ServiceRequest<User> request, User user,
			StringBuilder strQuery, Map<String, Object> parameters) {
		if (!Strings.isNullOrEmpty(user.getName())) {
			strQuery.append(" and (lower(o.name) like :name or lower(o.email) like :name )");
			parameters.put("name", Joiner.on("").join("%", request.getEntity().getName().toLowerCase(),"%"));
		}
	}

	private void addUserTypeWhereClause(User user, StringBuilder strQuery,
			Map<String, Object> parameters) {
		if (user.getUserType() != null) {
			strQuery.append(" and o.userType = :userType");
			parameters.put("userType", user.getUserType());
		}
	}

	@Override
	public ServiceCollectionResponse<User> searchUsers(ServiceRequest<User> request) {
		TypedQuery<User> query = em.createQuery(QUERY_FIND_USERS, User.class);
		query.setParameter("name", Joiner.on("").join("%", request.getEntity().getName().toLowerCase(),"%"));
		return executeQueryForPagination(query, request);
	}
	
	@Override
	public ServiceCollectionResponse<User> searchPatientsUsers(ServiceRequest<User> request) {
		TypedQuery<User> query = em.createQuery(QUERY_SEARCH_PATIENTS_USERS, User.class);
		query.setParameter("account", request.getAccount());
		query.setParameter("name", Joiner.on("").join("%", request.getEntity().getName().toLowerCase(),"%"));
		return executeQueryForPagination(query, request);
	}
	
	private void addOrderBy (StringBuilder builder ) {
		builder.append("order by lower(o.name)");
	}
	
}
