package com.redfire.nutrieduc.userprofile.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.consultation.Consultation;
import com.redfire.nutrieduc.entities.consultation.ConsultationStatus;
import com.redfire.nutrieduc.userprofile.dao.ConsultationBodyMeasureDAO;
import com.redfire.nutrieduc.userprofile.dao.ConsultationClinicalDataDAO;
import com.redfire.nutrieduc.userprofile.dao.ConsultationDAO;
import com.redfire.nutrieduc.userprofile.dao.ConsultationEatingHabitDAO;
import com.redfire.nutrieduc.userprofile.dao.ConsultationLifeHabitDAO;
import com.redfire.nutrieduc.userprofile.service.ConsultationFatCalculation;
import com.redfire.nutrieduc.userprofile.service.ConsultationService;
import com.redfire.nutrieduc.userprofile.service.UserInfoProfileService;

@Service
public class ConsultationServiceImpl implements ConsultationService {
	@Autowired private ConsultationDAO consultationDAO;
	@Autowired private ConsultationBodyMeasureDAO bodyMeasureDAO;
	@Autowired private ConsultationClinicalDataDAO clinicalDataDAO;
	@Autowired private ConsultationEatingHabitDAO eatingHabitDAO;
	@Autowired private ConsultationLifeHabitDAO lifeHabitDAO;	
	
	@Autowired private ConsultationFatCalculation consultationFatCalculation;
	@Autowired private UserInfoProfileService infoProfileService;

	@Transactional
	public ServiceCollectionResponse<Consultation> retrieve(
			ServiceRequest<Consultation> request) {
		return consultationDAO.retrieve(request);
	}

	@Transactional
	public void insert(Consultation consultation) {
		consultation.setConsultationStatus(ConsultationStatus.PENDING);
		consultation.setCreateDate(new Date());
		consultationFatCalculation.calculate(consultation, infoProfileService.getFromUser(consultation.getUserPatient().getId()));
		bodyMeasureDAO.insert(consultation.getBodyMeasure());
		clinicalDataDAO.insert(consultation.getClinicalData());
		eatingHabitDAO.insert(consultation.getEatingHabit());
		lifeHabitDAO.insert(consultation.getLifeHabit());
		consultationDAO.insert(consultation);
	}

	@Transactional
	public void update(Consultation consultation, Long id) {
		bodyMeasureDAO.update(consultation.getBodyMeasure(), consultation.getBodyMeasure().getId());
		clinicalDataDAO.update(consultation.getClinicalData(), consultation.getClinicalData().getId());
		eatingHabitDAO.update(consultation.getEatingHabit(), consultation.getEatingHabit().getId());
		lifeHabitDAO.update(consultation.getLifeHabit(), consultation.getLifeHabit().getId());
		consultationDAO.update(consultation, id);
	}

	@Transactional
	public ServiceCollectionResponse<Consultation> consultationPool (ServiceRequest<Consultation> request) {
		return consultationDAO.consultationPool(request);
	}

	@Transactional
	public ServiceCollectionResponse<Consultation> consultationDone(ServiceRequest<Consultation> request) {
		return consultationDAO.consultationDone(request);
	}
}
