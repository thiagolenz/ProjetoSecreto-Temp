package com.redfire.nutrieduc.notifyintegration.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redfire.nutrieduc.notify.EmailBean;
import com.redfire.nutrieduc.notifyintegration.service.EmailSender;
import com.redfire.nutrieduc.restclient.BeanJsonConverter;

@Service
public class EmailListenerImpl{
	
	@Autowired
	private EmailSender emailSender;
	
	private BeanJsonConverter beanJsonConverter = new BeanJsonConverter();

    public void processMessage( String message ){
        System.out.println( "MessageListener::::::Received message: " + message );
        try {
			EmailBean emailBean = (EmailBean) beanJsonConverter.convertToObject(message, EmailBean.class);
			emailSender.sendEmail(emailBean);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}