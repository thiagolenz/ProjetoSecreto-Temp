package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.appointment.Appointment;

public interface AppointmentService {
	String CONFLICT_APPOINTMENT = "appointment.service.conflict";
	Appointment insert (Appointment appointment) throws BusinessException;
	Appointment update (Appointment appointment, Long id) throws BusinessException;
	void remove (Long id);
	ServiceCollectionResponse<Appointment> search (ServiceRequest<Appointment> request);
}
