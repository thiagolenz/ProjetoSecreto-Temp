package com.redfire.nutrieduc.userprofile.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.consultation.Consultation;
import com.redfire.nutrieduc.entities.consultation.ConsultationStatus;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.userprofile.dao.ConsultationDAO;

@Repository
public class ConsultationDaoImpl extends AbstractJpaDAOImpl<Consultation> implements ConsultationDAO {

	@Override
	public ServiceCollectionResponse<Consultation> retrieve (ServiceRequest<Consultation> request) {
		User user = request.getEntity().getUserPatient();
		StringBuilder strQuery = new StringBuilder("from Consultation o where o.userPatient = :user ");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("user", user);
		strQuery.append("order by o.createDate DESC");
		TypedQuery<Consultation> query = createQueryAndSetParams(strQuery, parameters, Consultation.class);
		return executeQueryForPagination(query, request);
	}
	
	@Override
	public ServiceCollectionResponse<Consultation> consultationPool(ServiceRequest<Consultation> request) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("status", ConsultationStatus.PENDING);
		return consultationDailyByProfessional(request, parameters);
	}
	
	@Override
	public ServiceCollectionResponse<Consultation> consultationDone(ServiceRequest<Consultation> request) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("status", ConsultationStatus.DIETING);
		return consultationDailyByProfessional(request, parameters);
	}

	private ServiceCollectionResponse<Consultation> consultationDailyByProfessional(
			ServiceRequest<Consultation> request, Map<String, Object> parameters) {
		User userNutri = request.getEntity().getUserNutritionist();
		StringBuilder strQuery = new StringBuilder("from Consultation o where o.userNutritionist = :user and o.consultationStatus = :status ");
		strQuery.append(" and o.consultDate >= :date ");
		parameters.put("user", userNutri);
		parameters.put("status", parameters.get("status"));
		parameters.put("date", request.getEntity().getConsultDate());
		strQuery.append("order by o.createDate ASC");
		TypedQuery<Consultation> query = createQueryAndSetParams(strQuery, parameters, Consultation.class);
		return executeQueryForPagination(query, request);
	}


}
