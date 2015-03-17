package com.redfire.nutrieduc.notifyintegration.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.redfire.nutrieduc.notify.EmailBean;
import com.redfire.nutrieduc.notifyintegration.service.EmailSender;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGrid.Response;
import com.sendgrid.SendGridException;

@Service
public class EmailSenderImpl implements EmailSender {
	@Value("${sendgrid_username}" )
	private String userName;
	
	@Value("${sendgrid_password}" )
	private String password;
	
	@Value("${email_from}" )
	private String from;

	@Override
	public void sendEmail(EmailBean emailBean) {

		SendGrid sendgrid = new SendGrid(userName, password);
		
		Email email = new Email();
		email.addTo(getTos(emailBean.getToEmails()));
		email.addToName(emailBean.getToName());
		email.setFrom(from);
		email.setHtml(emailBean.getContent());
		email.setSubject(emailBean.getSubject());
		email.setFromName("NutriEduc");
		
		try {
			Response send = sendgrid.send(email);
			System.out.println("Email sended "+ send.getCode() + " " + send.getMessage());
		} catch (SendGridException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String [] getTos (List<String> toEmails) {
		return toEmails.toArray(new String[toEmails.size()]);
	}

}
