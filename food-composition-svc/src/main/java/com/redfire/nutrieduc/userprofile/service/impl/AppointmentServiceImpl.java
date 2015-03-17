package com.redfire.nutrieduc.userprofile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.appointment.Appointment;
import com.redfire.nutrieduc.userprofile.dao.AppointmentDAO;
import com.redfire.nutrieduc.userprofile.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	
	@Autowired
	private AppointmentDAO appointmentDAO;

	@Transactional
	public Appointment insert(Appointment appointment) throws BusinessException {
		validateConflictedAppointment(appointment);
		appointmentDAO.insert(appointment);
		return appointment;
	}

	@Transactional
	public Appointment update(Appointment appointment, Long id) throws BusinessException {
		validateConflictedAppointment(appointment);
		appointmentDAO.update(appointment, id);
		return appointment;
	}
	
	private void validateConflictedAppointment (Appointment appointment) throws BusinessException {
		if (appointmentDAO.findConflict(appointment))
			throw new BusinessException(CONFLICT_APPOINTMENT);
	}

	@Transactional
	public void remove(Long id) {
		appointmentDAO.remove(appointmentDAO.findById(Appointment.class, id));
	}

	@Transactional
	public ServiceCollectionResponse<Appointment> search(ServiceRequest<Appointment> request) {
		return appointmentDAO.search(request);
	}
}
