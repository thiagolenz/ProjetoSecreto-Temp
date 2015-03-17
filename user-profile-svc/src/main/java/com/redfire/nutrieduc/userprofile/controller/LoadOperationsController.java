package com.redfire.nutrieduc.userprofile.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfire.nutrieduc.commonsvc.response.OperationServiceResponse;
import com.redfire.nutrieduc.commonsvc.rest.LoadRestOperation;

@Controller
@RequestMapping(value="/module/operations",produces=MediaType.APPLICATION_JSON_VALUE)
public class LoadOperationsController {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<OperationServiceResponse> loadOperations () throws IOException, ClassNotFoundException {
		Reflections reflections = new Reflections("com.redfire");
		Set<Class<?>> annotated = 
				reflections.getTypesAnnotatedWith(Controller.class);
		return new LoadRestOperation().loadOperations(annotated);
	}
}
