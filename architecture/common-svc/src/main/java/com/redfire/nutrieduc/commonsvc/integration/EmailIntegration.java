package com.redfire.nutrieduc.commonsvc.integration;

import com.redfire.nutrieduc.notify.EmailBean;
import com.redfire.nutrieduc.restclient.RestClientException;

public interface EmailIntegration {
	void sendEmail (EmailBean emailBean) throws RestClientException;
}
