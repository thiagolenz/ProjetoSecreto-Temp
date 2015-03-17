package com.redire.nutrieduc.nutreducadmin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/"+ServletConstants.LOGIN)
public class LoginViewServlet extends HttpServlet {
	
	private final String loginPage = "modules/login/template/login.jsp";
	/**
	 * 
	 */
	private static final long serialVersionUID = -9000206810802484855L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		request.getRequestDispatcher(loginPage).include(request, resp); 
	}
}
