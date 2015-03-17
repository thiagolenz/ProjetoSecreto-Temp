package com.redfire.nutrieduc.userprofile.dao;

import com.redfire.nutrieduc.commonsvc.svc.dao.GenericDAO;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.consultation.Consultation;

public interface ConsultationDAO extends GenericDAO<Consultation> {
	 ServiceCollectionResponse<Consultation> retrieve(ServiceRequest<Consultation> request);
	 ServiceCollectionResponse<Consultation> consultationPool (ServiceRequest<Consultation> request);
	 ServiceCollectionResponse<Consultation> consultationDone (ServiceRequest<Consultation> request);
}
