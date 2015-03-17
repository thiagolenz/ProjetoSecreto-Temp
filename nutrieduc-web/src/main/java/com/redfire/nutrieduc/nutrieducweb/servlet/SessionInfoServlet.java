package com.redfire.nutrieduc.nutrieducweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.redfire.nutrieduc.common.Constants;

@WebServlet("/sessionInfo")
public class SessionInfoServlet extends HttpServlet {
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType(Constants.APPLICATION_JSON);
		resp.getWriter().write(req.getSession().getAttribute("contextUser").toString());
	}
}
