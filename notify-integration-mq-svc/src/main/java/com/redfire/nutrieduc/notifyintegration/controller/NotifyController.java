package com.redfire.nutrieduc.notifyintegration.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfire.nutrieduc.commonsvc.response.Response;
import com.redfire.nutrieduc.notify.EmailBean;
import com.redfire.nutrieduc.notifyintegration.service.PublishService;

@Controller
@RequestMapping(value="/notify", produces=MediaType.APPLICATION_JSON_VALUE)
public class NotifyController {

	@Autowired
    private PublishService publishService;

	@RequestMapping(value="/email", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
	public Response sendEmail (@RequestBody EmailBean emailBean) {
		publishService.publishEmail(emailBean);
		return Response.newSuccessResponse();
	}
	
	@RequestMapping(value="/push", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
	public Response push () throws IOException {
		publishService.pushMobileNotify("Hello mobile");
		return Response.newSuccessResponse();
	}
}
