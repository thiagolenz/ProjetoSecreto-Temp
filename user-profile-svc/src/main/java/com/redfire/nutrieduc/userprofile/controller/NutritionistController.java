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
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.userprofile.service.UserService;

@Controller
@RequestMapping(value="/nutritionist", produces=MediaType.APPLICATION_JSON_VALUE)
public class NutritionistController {
	private RequestContext requestContext;
	
	@Autowired
	private ServiceRequestFactory<User> requestFactoryUser;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/retrieveAccountNutritionists", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceCollectionResponse<User> retrieveAccountNutritionists (@RequestBody User user) {
		ServiceRequest<User> request = requestFactoryUser.createServiceRequest(user, requestContext);
		return userService.searchNutritionistsUsers(request);
	}
}
