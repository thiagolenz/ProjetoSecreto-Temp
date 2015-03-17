package com.redfire.nutrieduc.notifyintegration.service;

import com.redfire.nutrieduc.notify.EmailBean;

public interface PublishService {
	void publishEmail (EmailBean emailBean);
	void pushMobileNotify (String message);
}
