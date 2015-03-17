package com.redfire.nutrieduc.appsession.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.redfire.nutrieduc.entities.user.User;

/**
 * Class that do the login of Single User 
 * @author thiagolenz
 * @since Aug 25, 2014
 *
 */
@WebServlet("/userlogin/*")
public class UserLoginServlet extends LoginServlet {

	private static final long serialVersionUID = -3072938472632687519L;

	@Override
	public String decodeLoginURL(HttpServletRequest request) {
		return decoder.decodeURL("/userProfile/user/login");
	}

	@Override
	public UserLoginStorageBean getSessionEntity(HttpEntity entity) throws ParseException, IOException {
		String result = EntityUtils.toString(entity);
		User bean = (User) beanStringConverter.convertToObject(result, User.class);
		return new UserLoginStorageBean(bean, false);
	}

}
