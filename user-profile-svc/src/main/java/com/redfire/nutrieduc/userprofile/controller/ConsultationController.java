package com.redfire.nutrieduc.userprofile.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfire.nutrieduc.commonsvc.context.RequestContext;
import com.redfire.nutrieduc.commonsvc.svc.controller.ServiceRequestFactory;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.consultation.Consultation;
import com.redfire.nutrieduc.userprofile.service.ConsultationService;

@Controller
@RequestMapping(value="/consultation", produces=MediaType.APPLICATION_JSON_VALUE)
public class ConsultationController {
	private RequestContext requestContext;
	@Autowired
	private ConsultationService consultationService;

	@Autowired
	private ServiceRequestFactory<Consultation> requestFactory;

	@RequestMapping(value="/retrieve", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceCollectionResponse<Consultation> retrieve (@RequestBody Consultation consultation) {
		ServiceRequest<Consultation> request = requestFactory.createServiceRequest(consultation, requestContext);
		return consultationService.retrieve(request);
	}

	@RequestMapping(value="/", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Consultation create (@RequestBody Consultation consultation) {
		consultationService.insert(consultation);
		return consultation;
	}

	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Consultation update (@RequestBody Consultation consultation, @PathVariable ("id") Long id) {
		consultationService.update(consultation, id);
		return consultation;
	}

	@RequestMapping(value="/retrieveMyConsultation", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceCollectionResponse<Consultation> retrieveMyConsultation (@RequestBody Consultation consultation) {
		consultation.setUserPatient(requestContext.getUser());
		ServiceRequest<Consultation> request = requestFactory.createServiceRequest(consultation, requestContext);
		return consultationService.retrieve(request);
	}

	@RequestMapping(value="/retrieveConsultationPoool", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceCollectionResponse<Consultation> retrieveConsultationPoool (@RequestBody Consultation consultation) {
		consultation.setUserNutritionist(requestContext.getUser());
		defineDate(consultation);
		ServiceRequest<Consultation> request = requestFactory.createServiceRequest(consultation, requestContext);
		return consultationService.consultationPool(request);
	}

	@RequestMapping(value="/retrieveConsultationDone", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceCollectionResponse<Consultation> retrieveConsultationDone (@RequestBody Consultation consultation) {
		consultation.setUserNutritionist(requestContext.getUser());
		defineDate(consultation);
		ServiceRequest<Consultation> request = requestFactory.createServiceRequest(consultation, requestContext);
		return consultationService.consultationDone(request);
	}

	private void defineDate(Consultation consultation) {
		if (consultation.getConsultDate() == null) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			consultation.setConsultDate(cal.getTime());
		}
	}
}
