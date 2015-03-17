package com.redfire.nutrieduc.nutrieducweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/"+ServletConstants.CHANGEPASSWORD)
public class ChangePasswordPageServlet extends HttpServlet{
	private static final long serialVersionUID = -4920120240371789637L;
	private final String changePasswordPage = "modules/login/template/changePasswordPage.jsp";
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		request.getRequestDispatcher(changePasswordPage).include(request, resp); 
	}
}
