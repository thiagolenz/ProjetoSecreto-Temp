package com.redfire.nutrieduc.nutrieducweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/"+ServletConstants.CREATEACCOUNT)
public class CreateAccountViewServlet extends HttpServlet {
	
	private final String createPage = "modules/accountPatient/templates/createAccount.jsp";
	/**
	 * 
	 */
	private static final long serialVersionUID = -9000206810802484855L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		if (request.getParameter("redirect") != null)
			request.getSession().removeAttribute("error");
		request.getRequestDispatcher(createPage).include(request, resp); 
	}
}
