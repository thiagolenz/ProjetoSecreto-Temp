package com.redfire.nutrieduc.restclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import br.com.redfire.nutrieduc.common.Constants;

/**
 * Class responsible to read the custom headers and append to a Map. 
 * It also append additional headers like Service Gateway Key
 * @author thiagolenz
 * @since 25/08/2014
 */
public class HeaderReader {
	static Properties gatewayTokens;
	private static String FIELD_DEFAULT = "default";
	static {
		gatewayTokens = new Properties();
		try {
			InputStream is = HeaderReader.class.getClassLoader().getResourceAsStream("serviceGatewayTokens.properties");
			if (is != null)
				gatewayTokens.load(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public Map<String, String> readHeaders (HttpServletRequest request) {
		Map<String, String> headers = new HashMap<String, String>();
		addAttributesInHeader(request, headers);
		addRedFireParamsInHeader(request, headers);
		headers.put(Constants.SERVICE_GATEWAY_KEY, gatewayTokens.getProperty(FIELD_DEFAULT));
		headers.put(Constants.APP_CLIENT_KEY, request.getHeader(Constants.APP_CLIENT_KEY));
		return headers;
	}
	
	public void addAttributesInHeader (HttpServletRequest request, Map<String, String> headers  ) {
		List<String> attributes = Collections.list((Enumeration<String>)request.getSession().getAttributeNames());
		for (String attribute : attributes) 
			headers.put(attribute, String.valueOf(request.getSession().getAttribute(attribute)));
	}
	
	public void addRedFireParamsInHeader (HttpServletRequest request, Map<String, String> headers  ) {
		String head = null;
		Enumeration<String> enumHeaders = request.getHeaderNames();
		while ((head = enumHeaders.nextElement()) != null) {
			if (head.startsWith("rf-"))
				headers.put(head, request.getHeader(head));
		}
	}
}
