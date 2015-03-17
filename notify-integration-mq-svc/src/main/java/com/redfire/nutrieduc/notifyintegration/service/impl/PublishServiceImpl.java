package com.redfire.nutrieduc.notifyintegration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.redfire.nutrieduc.notify.EmailBean;
import com.redfire.nutrieduc.notifyintegration.service.PublishService;
import com.redfire.nutrieduc.restclient.BeanJsonConverter;

@Service
public class PublishServiceImpl implements PublishService {
	
	private BeanJsonConverter beanJsonConverter = new BeanJsonConverter();

    @Autowired
    private MessageChannel emailChannel;
    
    @Autowired
    private MessageChannel pushMobileChannel;

	@Override
	public void publishEmail(EmailBean emailBean) {
		String content = beanJsonConverter.convertToString(emailBean);
		emailChannel.send( MessageBuilder.withPayload(content).build());
	}

	@Override
	public void pushMobileNotify(String message) {
		pushMobileChannel.send( MessageBuilder.withPayload(message).build());
	}
}