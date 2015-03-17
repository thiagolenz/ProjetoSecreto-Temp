package com.redfire.nutrieduc.commonsvc.integration;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.redfire.nutrieduc.common.Constants;

@Component
public class IntegrationUtil {
	
	@Autowired
	private HttpServletRequest httpServletRequest;

	public Map<String, String> readHeaders () {
		Map<String, String> headers = new HashMap<>();
		headers.put(Constants.SERVICE_GATEWAY_KEY, httpServletRequest.getHeader(Constants.SERVICE_GATEWAY_KEY));
		headers.put(Constants.USER_ID, httpServletRequest.getHeader(Constants.USER_ID));

		Enumeration<String> enumHeaders = httpServletRequest.getHeaderNames();
		while (enumHeaders.hasMoreElements()) {
			String header = enumHeaders.nextElement();
			if (header.startsWith("rf-"))
				headers.put(header, httpServletRequest.getHeader(header));
		}
		return headers;
	}
}
