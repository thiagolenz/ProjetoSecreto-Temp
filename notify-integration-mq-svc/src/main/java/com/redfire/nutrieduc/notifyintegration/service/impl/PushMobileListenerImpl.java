package com.redfire.nutrieduc.notifyintegration.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.redfire.nutrieduc.notify.MobileNotifyBean;
import com.redfire.nutrieduc.restclient.BeanJsonConverter;

@Service
public class PushMobileListenerImpl{

	private static final String GOOGLE_SERVER_KEY = "AIzaSyBOV1p4_eCqLqDTdS_rtha9yCTGCsTAO88";

	private BeanJsonConverter beanJsonConverter = new BeanJsonConverter();

	public void processMessage( String message ) throws IOException{
		System.out.println( "MessageListener::::::Received message: " + message );
		MobileNotifyBean bean = (MobileNotifyBean) beanJsonConverter.convertToObject(message, MobileNotifyBean.class);
		send(bean);
	}
	public void send(MobileNotifyBean bean) throws IOException {
		Sender sender = new Sender(GOOGLE_SERVER_KEY);
		Message message = new Message.Builder()
										.timeToLive(2419200)
										.delayWhileIdle(false)
										.addData(bean.getMessageType().toString(), bean.getMessage()).build();

		for (String regId : bean.getRegIdList()) {
			Result result = sender.send(message, regId, 1);
			System.out.println(result);
		}
	}
}