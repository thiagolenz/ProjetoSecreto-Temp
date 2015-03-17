package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.consultation.Consultation;

public interface ConsultationService {
	ServiceCollectionResponse<Consultation> retrieve (ServiceRequest<Consultation> request);
	ServiceCollectionResponse<Consultation> consultationPool (ServiceRequest<Consultation> request);
	ServiceCollectionResponse<Consultation> consultationDone (ServiceRequest<Consultation> request);
	
	void insert (Consultation consultation);
	void update (Consultation consultation, Long id);
}
