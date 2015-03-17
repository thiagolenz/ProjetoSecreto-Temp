package com.redfire.nutrieduc.userprofile.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfire.nutrieduc.chat.ChatMessage;
import com.redfire.nutrieduc.commonsvc.context.RequestContext;
import com.redfire.nutrieduc.commonsvc.response.Response;
import com.redfire.nutrieduc.commonsvc.svc.controller.ServiceRequestFactory;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.entities.account.AccountPatient;
import com.redfire.nutrieduc.entities.account.AccountType;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.response.ChatResponse;
import com.redfire.nutrieduc.userprofile.service.AccountPatientService;
import com.redfire.nutrieduc.userprofile.service.ChatMessageService;
import com.redfire.nutrieduc.userprofile.service.UserService;

@Controller
@RequestMapping(value="/chat", produces=MediaType.APPLICATION_JSON_VALUE)
public class ChatController {
	private RequestContext requestContext;
	
	@Autowired
	private UserService userService;
	@Autowired
	private ServiceRequestFactory<User> serviceFactoryUser;
	
	@Autowired
	private AccountPatientService accountPatientService;
	
	@Autowired
	private ServiceRequestFactory<AccountPatient> serviceRequestFactoryPatient;
	
	@Autowired
	private ServiceRequestFactory<ChatMessage> serviceRequestFactoryChatMessage;
	
	
	@Autowired
	private ChatMessageService chatMessageService;

	@RequestMapping(value="/getUserGroupList", method = RequestMethod.GET)
	@ResponseBody
	public ChatResponse getChatList () {
		AccountType accountType = requestContext.getAccount().getAccountType();
		if (accountType == AccountType.NUTRITIONIST)
			return loadUsersFromNutriAccount();
		else 
			return loadUsersFromPatientAccount();
	}
	
	public ChatResponse loadUsersFromNutriAccount () {
		ChatResponse response = new ChatResponse();
		
		loadOfficeUsers(response);
		loadPatients(response);
		
		return response;
	}

	private void loadOfficeUsers(ChatResponse response) {
		ServiceRequest<User> requestUser = serviceFactoryUser.createServiceRequest(new User(), requestContext);
		response.getGroupUsers().put("Consultório", userService.retrieveAccountUsers(requestUser).getDataList());
	}

	private void loadPatients(ChatResponse response) {
		ServiceRequest<AccountPatient> requestPatients = serviceRequestFactoryPatient.createServiceRequest(new AccountPatient(), requestContext);
		List<AccountPatient> patients = accountPatientService.findPatients(requestPatients).getDataList();
		List<User> users = new ArrayList<>();
		for (AccountPatient patient : patients) {
			users.add(patient.getPatientUser());
		}
		response.getGroupUsers().put("Pacientes", users);
	}
	
	public ChatResponse loadUsersFromPatientAccount () {
		ChatResponse response = new ChatResponse();
		ServiceRequest<User> requestUser = serviceFactoryUser.createServiceRequest(requestContext.getUser(), requestContext);
		response.getGroupUsers().put("Consultório", accountPatientService.searchOfficeUsers(requestUser).getDataList());
		return response;
	}
	
	@RequestMapping(value="/saveMessage", method = RequestMethod.POST,  consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response saveMessage (@RequestBody ChatMessage chatMessage) {
		chatMessageService.insert(chatMessage);
		return Response.newSuccessResponse();
	}
	
	@RequestMapping(value="/allHistory", method = RequestMethod.POST,  consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceCollectionResponse<ChatMessage> allHistory (@RequestBody ChatMessage chatMessage) {
		String login = requestContext.getUser().getEmail();
		chatMessage.setUserA(login);
		ServiceRequest<ChatMessage> request = serviceRequestFactoryChatMessage.createServiceRequest(chatMessage, requestContext);
		return chatMessageService.allHistory(request);
	}
}
