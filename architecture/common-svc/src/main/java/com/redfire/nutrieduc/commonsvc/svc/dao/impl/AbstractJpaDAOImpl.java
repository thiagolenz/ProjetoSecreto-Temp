package com.redfire.nutrieduc.commonsvc.svc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.redfire.nutrieduc.commonsvc.svc.dao.GenericDAO;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.common.AbstractEntity;
/**
 * Abstract implementation of JPA DAO
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 * @param <T>
 */
public abstract class AbstractJpaDAOImpl <T extends AbstractEntity> implements GenericDAO<T> {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void insert(T obj) {
		em.persist(obj);
	}

	@Override
	public void remove(T obj) {
		em.remove(obj);
	}

	@Override
	public void update(T obj, Long id) {
		AbstractEntity ae = (AbstractEntity)obj;
		ae.setId(id);
		em.merge(ae);
	}

	@Override
	public T findById(Class<T>clazz, Long id) {
		return em.find(clazz, id);
	}

	/**
	 * Receive a {@link TypedQuery} and execute it with OFFSET and LIMIT SQL 
	 * @param query 
	 * @param request
	 * @param registerCount
	 * @return Object {@link ServiceCollectionResponse} 
	 */
	protected ServiceCollectionResponse<T> executeQueryForPagination (TypedQuery<T> query, ServiceRequest<T> request) {
		int registerCount = count(query);
		List<T> results = query.setFirstResult(request.getOffset())
				.setMaxResults(request.getRecordsRange())
				.getResultList();
		return createResponse(registerCount, results);
	}

	private ServiceCollectionResponse<T> createResponse(int registerCount, List<T> results) {
		ServiceCollectionResponse<T> response = new ServiceCollectionResponse<>();
		response.addAll(results);
		response.setTotalRecordsCount(registerCount);
		return response;
	}
	
	private int count (TypedQuery<T> query) {
		return query.getResultList().size();
	}
	
	protected T findDuplicate (StringBuilder builder, Map<String, Object> parameters, Class<T> clazz) {
		TypedQuery<T> query = createQueryAndSetParams(builder, parameters, clazz);
		query.setMaxResults(1);
		
		List<T> list = query.getResultList();
		if (list.isEmpty())
			return null;
		return list.get(0);
	}

	protected TypedQuery<T> createQueryAndSetParams(StringBuilder builder, Map<String, Object> parameters, Class<T> clazz) {
		TypedQuery<T> query = em.createQuery(builder.toString(), clazz);
		for (String param : parameters.keySet())
			query.setParameter(param, parameters.get(param));
		return query;
	}
	
	protected void addIdWhereClause(AbstractEntity entity,
			StringBuilder strQuery, Map<String, Object> parameters) {
		if (entity.getId() != null) {
			strQuery.append(" and o.id <> :id");
			parameters.put("id", entity.getId());
		}
	}
}
