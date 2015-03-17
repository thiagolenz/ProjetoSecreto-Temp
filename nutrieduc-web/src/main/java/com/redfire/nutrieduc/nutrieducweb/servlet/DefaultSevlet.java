package com.redfire.nutrieduc.nutrieducweb.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.redfire.nutrieduc.restclient.BeanJsonConverter;
import com.redfire.nutrieduc.restclient.HeaderReader;
import com.redfire.nutrieduc.restclient.RestClient;

import br.com.redfire.nutrieduc.common.Constants;

public abstract class DefaultSevlet extends HttpServlet{

	private static final long serialVersionUID = 8240614539888777305L;
	protected HeaderReader headerReader = new HeaderReader();
	protected BeanJsonConverter jsonConverter = new BeanJsonConverter();
	protected RestClient restClient = new RestClient();
	
	
	public String getURLDecoded (String restUrl) throws IOException {
		String baseURL = ContextPropertyReader.getValue(Constants.SERVICE_GATEWAY_URL);
		return baseURL+restUrl;
	}

	public Map<String, String> readAndDefineHeaders (HttpServletRequest req) throws IOException {
		Map<String, String> headers = headerReader.readHeaders(req);
		addAditionalHeaders(headers);
		return headers;
	}

	public void addAditionalHeaders (Map<String, String> headers) throws IOException {
		headers.put(Constants.APP_CLIENT_KEY, ContextPropertyReader.getValue(Constants.APP_CLIENT_KEY));
		headers.put(Constants.PUBLIC_REQUEST_KEY, "4bf07af8-75c5-476e-b9c9-abef487232bd" );
	}
}
