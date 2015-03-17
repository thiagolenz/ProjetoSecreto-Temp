package com.redfire.nutrieduc.userprofile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfire.nutrieduc.commonsvc.context.RequestContext;
import com.redfire.nutrieduc.commonsvc.svc.controller.ServiceRequestFactory;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.Account;
import com.redfire.nutrieduc.userprofile.service.AccountService;

@Controller
@RequestMapping(value="/account", produces=MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
	
	private RequestContext requestContext;
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private ServiceRequestFactory<Account> serviceFactoryAccount;
	
	@RequestMapping(value="/retrieveNutritionistAccounts", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceCollectionResponse<Account> retrieveNutritionistAccounts (@RequestBody Account account) {
		ServiceRequest<Account> request = serviceFactoryAccount.createServiceRequest(account, requestContext);
		return accountService.retrieveNutritionistAccounts(request);
	}
}
