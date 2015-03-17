package com.redfire.nutrieduc.userprofile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfire.nutrieduc.commonsvc.response.Response;
import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.entities.user.ChangePasswordToken;
import com.redfire.nutrieduc.userprofile.service.UserPasswordService;

@Controller
@RequestMapping(value="/changePassword", produces=MediaType.APPLICATION_JSON_VALUE)
public class ChangePasswordController {
	
	@Autowired
	private UserPasswordService passwordService;
	
	@RequestMapping(value="/startChangeRequest", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ChangePasswordToken startChangeRequest (@RequestBody ChangePasswordToken changePasswordToken) throws BusinessException {
		return passwordService.processRequestNew(changePasswordToken.getToken());
	}
	
	@RequestMapping(value="/finalizeChangeRequest", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response finalizeChangeRequest (@RequestBody ChangePasswordToken changePasswordToken) throws BusinessException {
		passwordService.processFinalChange(changePasswordToken.getToken(), changePasswordToken.getUser().getPassword());
		return Response.newSuccessResponse();
	}
}
