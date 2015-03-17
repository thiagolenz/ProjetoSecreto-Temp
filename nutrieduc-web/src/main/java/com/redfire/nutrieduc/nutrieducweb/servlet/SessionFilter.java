package com.redfire.nutrieduc.nutrieducweb.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.redfire.nutrieduc.common.Constants;

@WebFilter("/*")
public class SessionFilter implements Filter {
	private final String INDEX_HTML = "/index.html";
	private final String HOME = "/";

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String path = request.getServletPath() ;

		System.out.println("path = "+ path);
		if (path.equals(HOME) || path.equals(INDEX_HTML))
			if (request.getSession().getAttribute(Constants.USER_ATTRIBUTE) == null) 
				((HttpServletResponse)resp).sendRedirect("login");

		customCache((HttpServletResponse) resp, path);
		chain.doFilter(request, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

	@Override
	public void destroy() {}
	
	private void customCache (HttpServletResponse res, String path) {
		if (path.contains("controller") || path.contains("modules") || path.contains("components")) {
			res.setHeader("Pragma", "No-cache");
			res.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
			res.setDateHeader("Expires", 1);
		}
	}

}
