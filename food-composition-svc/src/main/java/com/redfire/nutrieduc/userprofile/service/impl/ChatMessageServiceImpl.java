package com.redfire.nutrieduc.userprofile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redfire.nutrieduc.chat.ChatMessage;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.userprofile.dao.ChatMessageDAO;
import com.redfire.nutrieduc.userprofile.service.ChatMessageService;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
	@Autowired
	private ChatMessageDAO chatMessageDAO;

	@Override
	public void insert(ChatMessage chatMessage) {
		chatMessageDAO.insert(chatMessage);
	}

	@Override
	public ServiceCollectionResponse<ChatMessage> allHistory(ServiceRequest<ChatMessage> request) {
		return chatMessageDAO.allHistory(request);
	}

}
