package com.redfire.nutrieduc.userprofile.dao;

import com.redfire.nutrieduc.commonsvc.svc.dao.GenericDAO;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.appointment.Appointment;

public interface AppointmentDAO extends GenericDAO<Appointment> {
	ServiceCollectionResponse<Appointment> search (ServiceRequest<Appointment> request);
	boolean findConflict (Appointment appointment);
}
