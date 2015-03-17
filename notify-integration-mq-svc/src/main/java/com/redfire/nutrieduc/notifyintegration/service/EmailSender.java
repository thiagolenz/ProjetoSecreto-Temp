package com.redfire.nutrieduc.notifyintegration.service;

import com.redfire.nutrieduc.notify.EmailBean;

public interface EmailSender {
	void sendEmail (EmailBean emailBean);
}
