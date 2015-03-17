package com.redfire.nutrieduc.userprofile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfire.nutrieduc.commonsvc.context.RequestContext;
import com.redfire.nutrieduc.commonsvc.response.Response;
import com.redfire.nutrieduc.commonsvc.svc.controller.ServiceRequestFactory;
import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.commonsvc.svc.exception.MandatoryFieldException;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.AccountPatient;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.userprofile.service.AccountPatientService;


@Controller
@RequestMapping(value="/accountPatient", produces=MediaType.APPLICATION_JSON_VALUE)
public class AccountPatientController {
	public static String USER = "account.patient.user";
	private RequestContext requestContext;
	
	@Autowired
	private AccountPatientService accountPatientService;
	
	@Autowired
	private ServiceRequestFactory<AccountPatient> requestFactory;
	
	@Autowired
	private ServiceRequestFactory<User> requestFactoryUser;
	
	@RequestMapping(value="/bindUserPatient", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AccountPatient bindUserPatient (@RequestBody User user) throws BusinessException, MandatoryFieldException {
		if (user == null || user.getId() == null)
			throw new MandatoryFieldException(USER);
		return accountPatientService.bindUserPatient(user, requestContext.getUser().getAccount());
	}
	
	@RequestMapping(value="/retrieveAccountPatients", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceCollectionResponse<AccountPatient> retrieveAccountPatients (@RequestBody User user) {
		AccountPatient account = new AccountPatient();
		account.setPatientUser(user);
		ServiceRequest<AccountPatient> request = requestFactory.createServiceRequest(account, requestContext);
		return accountPatientService.findPatients(request);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response removePatientUser (@PathVariable ("id") Long id) throws BusinessException {
		accountPatientService.removeAccountPatient(id);
		return Response.newSuccessResponse();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ResponseBody
	public AccountPatient getPatientUser (@PathVariable ("id") Long id) throws BusinessException {
		AccountPatient result = accountPatientService.getByUser(id);
		if (result == null)
			return new AccountPatient();
		return result;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update (@PathVariable ("id") Long id, @RequestBody AccountPatient accountPatient) throws BusinessException {
		accountPatientService.update(id, accountPatient);
		return Response.newSuccessResponse();
	}
	
	@RequestMapping(value="/retrieveUsers", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceCollectionResponse<User> retrieveUsers (@RequestBody User user) {
		ServiceRequest<User> request = requestFactoryUser.createServiceRequest(user, requestContext);
		return accountPatientService.searchPatientsUsers(request);
	}
}
