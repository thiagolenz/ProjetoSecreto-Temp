package com.redfire.nutrieduc.commonsvc.integration.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.redfire.nutrieduc.commonsvc.integration.EmailIntegration;
import com.redfire.nutrieduc.commonsvc.integration.IntegrationUtil;
import com.redfire.nutrieduc.notify.EmailBean;
import com.redfire.nutrieduc.restclient.BeanJsonConverter;
import com.redfire.nutrieduc.restclient.RestClient;
import com.redfire.nutrieduc.restclient.RestClientException;
import com.redfire.nutrieduc.router.URLDecoder;

@Service
public class EmailIntegrationImpl implements EmailIntegration {
	private static final String URL_EMAIL = "/notifyIntegration/notify/email";
	
	private URLDecoder decoder = new URLDecoder();
	private BeanJsonConverter beanJsonConverter = new BeanJsonConverter();;
	
	@Autowired
	private IntegrationUtil integrationUtil;

	@Override
	public void sendEmail(EmailBean emailBean) throws RestClientException {
		RestClient restClient = new RestClient();
	
		restClient.doRequest(beanJsonConverter.convertToString(emailBean), 
							decoder.decodeURL(URL_EMAIL), 
							RequestMethod.POST.toString(), 
							integrationUtil.readHeaders());
	}
	


}
