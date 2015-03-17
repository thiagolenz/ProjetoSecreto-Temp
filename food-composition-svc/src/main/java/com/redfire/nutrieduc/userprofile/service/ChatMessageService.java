package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.chat.ChatMessage;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;

public interface ChatMessageService {
	void insert(ChatMessage chatMessage);
	ServiceCollectionResponse<ChatMessage> allHistory (ServiceRequest<ChatMessage> message);
}
