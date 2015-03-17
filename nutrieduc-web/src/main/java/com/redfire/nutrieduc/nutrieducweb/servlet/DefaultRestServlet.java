package com.redfire.nutrieduc.nutrieducweb.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import br.com.redfire.nutrieduc.common.Constants;

public abstract class DefaultRestServlet extends DefaultSevlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4828703082821761835L;

	public void addAditionalHeaders (Map<String, String> headers, HttpServletRequest request) throws IOException {
		headers.put(Constants.APP_CLIENT_KEY, ContextPropertyReader.getValue(Constants.APP_CLIENT_KEY));
		if (headers.containsKey("rf-public-request")) {
			headers.put(Constants.PUBLIC_REQUEST_KEY, "4bf07af8-75c5-476e-b9c9-abef487232bd" );
			headers.remove(Constants.USER_ATTRIBUTE);
		}
		else 
			headers.put(Constants.USER_ATTRIBUTE, (String) request.getSession().getAttribute(Constants.USER_ATTRIBUTE));
		String head = null;
		Enumeration<String> enumHeaders = request.getHeaderNames();
		while ((head = enumHeaders.nextElement()) != null) {
			if (head.startsWith("rf-"))
				headers.put(head, request.getHeader(head));
		}
	}
	
	public String getURLDecoded (HttpServletRequest request) throws IOException {
		String baseURL = ContextPropertyReader.getValue(Constants.SERVICE_GATEWAY_URL)+"/handler"; 
		String restUrl = request.getPathInfo();
		return baseURL+restUrl;
	}
	
}
