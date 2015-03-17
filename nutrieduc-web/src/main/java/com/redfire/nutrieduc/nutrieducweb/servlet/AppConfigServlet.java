package com.redfire.nutrieduc.nutrieducweb.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.redfire.nutrieduc.common.Constants;

import com.redfire.nutrieduc.nutrieducweb.servlet.beans.AppConfigBean;
import com.redfire.nutrieduc.restclient.BeanJsonConverter;

@WebServlet("/appConfig")
public class AppConfigServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2913092631719500022L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Properties prop = new Properties();
		String propertyName ="configurations_"+ System.getProperty("app.env")+ ".properties";
		prop.load(AppConfigServlet.class.getClassLoader().getResourceAsStream(propertyName));
		
		AppConfigBean appConfigBean = new AppConfigBean();
		appConfigBean.setChatUrl(prop.getProperty("chat.url"));
		
		BeanJsonConverter beanJsonConverter = new BeanJsonConverter();
		resp.getWriter().write(beanJsonConverter.convertToString(appConfigBean));
		resp.setContentType(Constants.APPLICATION_JSON);
	}
}
