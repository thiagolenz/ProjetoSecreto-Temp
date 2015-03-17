package com.redfire.nutrieduc.userprofile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfire.nutrieduc.commonsvc.context.RequestContext;
import com.redfire.nutrieduc.commonsvc.response.Response;
import com.redfire.nutrieduc.entities.mobile.CellPhoneRegister;
import com.redfire.nutrieduc.userprofile.service.CellPhoneRegisterService;


@Controller
@RequestMapping(value="/celphoneRegister",produces=MediaType.APPLICATION_JSON_VALUE)
public class CellphoneRegisterController {
	
	private RequestContext requestContext;
	
	@Autowired
	private CellPhoneRegisterService cellPhoneRegisterService;

	@RequestMapping(value="/", method = RequestMethod.POST)
	@ResponseBody
	public Response register (@RequestBody CellPhoneRegister cellPhoneRegister) {
		cellPhoneRegister.setUser(requestContext.getUser());
		cellPhoneRegisterService.insert(cellPhoneRegister);
		return Response.newSuccessResponse();
	}
}
