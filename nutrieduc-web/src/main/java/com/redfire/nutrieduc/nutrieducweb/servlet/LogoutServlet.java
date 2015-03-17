package com.redfire.nutrieduc.nutrieducweb.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/"+ServletConstants.LOGOUT)
public class LogoutServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1686003412365884478L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Enumeration<String> attributeNames = request.getAttributeNames();
		while (attributeNames.hasMoreElements())
			request.getSession().setAttribute(attributeNames.nextElement(), null);
		request.getSession().invalidate();
		response.sendRedirect(ServletConstants.LOGIN);
	}
}
	