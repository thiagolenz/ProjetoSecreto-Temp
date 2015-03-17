package com.redfire.nutrieduc.checkenv.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/status")
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 5978689776904836588L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().write("app.env="+ System.getProperty("app.env"));
	}
}
