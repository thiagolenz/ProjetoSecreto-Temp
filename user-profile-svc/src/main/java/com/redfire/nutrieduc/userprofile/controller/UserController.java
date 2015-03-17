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
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.entities.user.UserType;
import com.redfire.nutrieduc.userprofile.service.UserService;

@Controller
@RequestMapping(value="/user", produces=MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	@Inject
	private ServiceRequestFactory<User> serviceFactory;
	private RequestContext requestContext;
		
	@Inject
	private DefaultBeanValidator beanValidator; 
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/createPatientUser", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User createPatientUser (@RequestBody @Valid User user, BindingResult result) throws BusinessException, MandatoryFieldException {
		beanValidator.validateMandatoryFields(result);
		if (user.getPassword() == null)
			user.setPassword(System.currentTimeMillis() + "");
		userService.saveUserPatient(user);
		return user;
	}
	
	@RequestMapping(value="/createNutritionistUser", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User createNutritionistUser (@RequestBody @Valid User user, BindingResult result) throws MandatoryFieldException, BusinessException {
		beanValidator.validateMandatoryFields(result);
		userService.saveNutritionistUser(user);
		return user;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User updateUser(@PathVariable("id") Long userId, @Valid @RequestBody User user, BindingResult result) throws MandatoryFieldException, BusinessException {
		beanValidator.validateMandatoryFields(result);
		user.setAccount(requestContext.getAccount());
		userService.updateUser(user, userId);
		return user;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response deleteUser (@PathVariable("id") Long userId) throws MandatoryFieldException, BusinessException {
		userService.removeUser(userId);
		return Response.newSuccessResponse();
	}
	

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User findById (@PathVariable("id") Long userId) throws MandatoryFieldException, BusinessException {
		return userService.findByIdAndAccount(userId, requestContext.getAccount());
	}
	
	@RequestMapping(value="/createUserAccount", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User createUserAccount (@RequestBody @Valid User user, BindingResult result) throws BusinessException, MandatoryFieldException {
		beanValidator.validateMandatoryFields(result);
		if (user.getUserType() == null)
			user.setUserType(UserType.EMPLOYEE);
		user.setAccount(requestContext.getAccount());
		userService.createUserAccount(user);
		return user;
	}
	
	@RequestMapping(value="/retrieveUsersByAccount", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceCollectionResponse<User> retrieveUsersByAccount (@RequestBody User userFilter) {
		ServiceRequest<User> request = serviceFactory.createServiceRequest(userFilter, requestContext);
		return userService.retrieveAccountUsers(request);
	}
	
	@RequestMapping(value="/retrieveUsers", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceCollectionResponse<User> retrieveUsers (@RequestBody User userFilter) {
		ServiceRequest<User> request = serviceFactory.createServiceRequest(userFilter, requestContext);
		return userService.retrieveUsers(request);
	}	

}
