package com.redfire.nutrieduc.userprofile.dao;

import com.redfire.nutrieduc.chat.ChatMessage;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;

public interface ChatMessageDAO {
	void insert (ChatMessage chatMessage);
	ServiceCollectionResponse<ChatMessage> allHistory (ServiceRequest<ChatMessage> message);
}
