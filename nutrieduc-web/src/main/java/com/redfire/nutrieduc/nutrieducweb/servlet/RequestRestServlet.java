package com.redfire.nutrieduc.nutrieducweb.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import br.com.redfire.nutrieduc.common.Constants;

import com.redfire.nutrieduc.restclient.ContentReader;
import com.redfire.nutrieduc.restclient.RestClientException;

@WebServlet("/rest/*")
public class RequestRestServlet extends DefaultRestServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3997088636186079977L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		ContentReader contentReader = new ContentReader();
		response.setContentType(Constants.APPLICATION_JSON);
		int httpStatus = 0;

		try {
			String urlAppMangm = getURLDecoded(req);
			Map<String, String> readHeaders = headerReader.readHeaders(req);
			addAditionalHeaders(readHeaders, req);
			CloseableHttpResponse httpResponse = restClient.doRequest(contentReader.getJSONContent(req.getReader()), 
					urlAppMangm, 
					req.getMethod(), 
					readHeaders);
			httpStatus = httpResponse.getStatusLine().getStatusCode();
			response.getWriter().write(EntityUtils.toString(httpResponse.getEntity()));
		} catch (RestClientException e) {
			httpStatus = e.getStatusCode();
			response.getWriter().write(e.getMessage());
		}		
		
		response.setStatus(httpStatus);
	}
}
