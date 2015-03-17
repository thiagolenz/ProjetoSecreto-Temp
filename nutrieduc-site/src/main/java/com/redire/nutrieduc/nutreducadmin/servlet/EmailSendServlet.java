package com.redire.nutrieduc.nutreducadmin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redfire.nutrieduc.notify.EmailBean;
import com.redfire.nutrieduc.restclient.BeanJsonConverter;
import com.redfire.nutrieduc.restclient.ContentReader;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGrid.Response;
import com.sendgrid.SendGridException;

@WebServlet("/sendEmail")
public class EmailSendServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SendGrid sendgrid = new SendGrid("thiagolenz", "redfire#8990");
		EmailBean bean = getEmailBean(req);
		Email email = new Email();
		email.addTo(new String [] {"contato@nutrieduc.com"});
		email.addToName(new String [] {"Nutrieduc"});
		email.setFrom(bean.getFromEmail());
		email.setText(bean.getContent());
		email.setSubject("[SITE] " + bean.getSubject());
		email.setFromName(bean.getFromName());
		
		try {
			Response send = sendgrid.send(email);
			System.out.println("Email sended "+ send.getCode() + " " + send.getMessage());
		} catch (SendGridException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private EmailBean getEmailBean (HttpServletRequest request) throws IOException {
		ContentReader reader = new ContentReader();
		BeanJsonConverter converter = new BeanJsonConverter();
		EmailBean bean = (EmailBean) converter.convertToObject(reader.getJSONContent(request.getReader()), EmailBean.class);
		return bean;
	}
}
