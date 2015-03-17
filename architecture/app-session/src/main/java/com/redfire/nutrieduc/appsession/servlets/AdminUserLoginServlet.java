package com.redfire.nutrieduc.appsession.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import com.redfire.nutrieduc.entities.user.AdminUser;

/**
 * Servlet that do the login of Admin user
 * @author thiagolenz
 * @since Aug 25, 2014
 *
 */
@WebServlet("/adminlogin/*")
public class AdminUserLoginServlet extends LoginServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2119891402917214715L;

	@Override
	public String decodeLoginURL(HttpServletRequest request) {
		return decoder.decodeURL("/adminProfile/admin/login");
	}

	@Override
	public UserLoginStorageBean getSessionEntity(HttpEntity entity) throws IOException {
		String result = EntityUtils.toString(entity);
		AdminUser bean =  (AdminUser) beanStringConverter.convertToObject(result, AdminUser.class);
		return new UserLoginStorageBean(bean, true);
	}

}
