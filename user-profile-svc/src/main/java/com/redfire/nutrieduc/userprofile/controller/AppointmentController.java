package com.redfire.nutrieduc.userprofile.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfire.nutrieduc.commonsvc.context.RequestContext;
import com.redfire.nutrieduc.commonsvc.response.Response;
import com.redfire.nutrieduc.commonsvc.svc.controller.DefaultBeanValidator;
import com.redfire.nutrieduc.commonsvc.svc.controller.ServiceRequestFactory;
import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.commonsvc.svc.exception.MandatoryFieldException;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.appointment.Appointment;
import com.redfire.nutrieduc.userprofile.service.AppointmentService;

@Controller
@RequestMapping(value="/appointment", produces=MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {
	private RequestContext requestContext;
	
	@Inject
	private DefaultBeanValidator beanValidator;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Inject
	private ServiceRequestFactory<Appointment> serviceFactory;
	
	@RequestMapping(value="/", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Appointment createNew (@RequestBody @Valid Appointment appointment, BindingResult result) throws MandatoryFieldException, BusinessException {
		beanValidator.validateMandatoryFields(result);
		appointmentService.insert(appointment);
		return appointment;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Appointment update (@RequestBody @Valid Appointment appointment, @PathVariable("id") Long id, BindingResult result) throws MandatoryFieldException, BusinessException {
		beanValidator.validateMandatoryFields(result);
		appointmentService.update(appointment, id);
		return appointment;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response update (@PathVariable("id") Long id) throws MandatoryFieldException {
		appointmentService.remove(id);
		return Response.newSuccessResponse();
	}
	
	@RequestMapping(value="/search", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceCollectionResponse<Appointment> retrieveUsersByAccount (@RequestBody Appointment appointment) {
		ServiceRequest<Appointment> request = serviceFactory.createServiceRequest(appointment, requestContext);
		return appointmentService.search(request);
	}
}
