package com.redfire.nutrieduc.userprofile.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.appointment.Appointment;
import com.redfire.nutrieduc.userprofile.dao.AppointmentDAO;

@Repository
public class AppointmentDAOImpl extends AbstractJpaDAOImpl<Appointment>
		implements AppointmentDAO {
	
	private static final String QUERY_FIND_CONFLICT = "select * from Appointment o where " +
			"(o.startDate >= :startDate and o.finishDate > :finishDate and o.startDate <= :finishDate) or " +
			"(o.startDate <= :startDate and o.finishDate > :finishDate) or " +
			"(o.startDate <= :startDate and o.finishDate < :finishDate and o.finishDate > :startDate) " +
			" and o.nutritionist_user_id = :nutritionist ";
	
	private static final String QUERY_SEARCH = "from Appointment o where o.nutritionistUser = :nutritionist and " +
			" o.startDate >= :startDate and o.finishDate <= :finishDate ";

	public ServiceCollectionResponse<Appointment> search(ServiceRequest<Appointment> request) {
		StringBuilder strQuery = new StringBuilder(QUERY_SEARCH);
		Appointment appointment = request.getEntity();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nutritionist", appointment.getNutritionistUser());
		parameters.put("startDate", appointment.getStartDate());
		parameters.put("finishDate", appointment.getFinishDate());
		
		addPatientWhereClause(strQuery, appointment, parameters);
	
		strQuery.append(" order by o.startDate");
		TypedQuery<Appointment> query = createQueryAndSetParams(strQuery, parameters, Appointment.class);
		return executeQueryForPagination(query, request);
	}

	private void addPatientWhereClause(StringBuilder strQuery, Appointment appointment, Map<String, Object> parameters) {
		if (appointment.getPatientUser() != null) {
			strQuery.append(" and o.patientUser = :patient");
			parameters.put("patient", appointment.getPatientUser());
		}
	}

	@Override
	public boolean findConflict(Appointment appointment) {
		StringBuilder builder = new StringBuilder(QUERY_FIND_CONFLICT);
		if (appointment.getId() != null)
			builder.append( " and o.id != :id");
		Query query = em.createNativeQuery(builder.toString());
		query.setParameter("startDate", appointment.getStartDate());
		query.setParameter("finishDate", appointment.getFinishDate());
		query.setParameter("nutritionist", appointment.getNutritionistUser().getId());
		if (appointment.getId() != null)
			query.setParameter("id", appointment.getId());
		query.setMaxResults(1);
		return !query.getResultList().isEmpty();
	}

}
