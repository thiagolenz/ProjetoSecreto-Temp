package com.redfire.nutrieduc.restclient;

import java.io.File;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

/**
* This class receive: URL, content, method and headers and execute the request routing
* @author thiagolenz
* @since 25/08/2014
*/
public class RestClient {

	private HttpUriRequestBuilder requestFactory = new HttpUriRequestBuilder();
	private HttpClientFactory httpClientFactory = new HttpClientFactory();

	/**
	 * Do the request
	 * @param content body of request
	 * @param url REST URL 
	 * @param method POST, GET, PUT or DELETE
	 * @param headers Map of headers
	 * @return {@link CloseableHttpResponse} response object
	 * @throws RestClientException if a error occur on route the request
	 */
	public CloseableHttpResponse doRequest (String content, String url, String method, Map<String, String> headers) throws RestClientException {
		return doRequest(url, method, headers, content, null);
	}
	
	/**
	 * Do the request of upload file
	 * @param file file of request
	 * @param url REST URL 
	 * @param method POST, GET, PUT or DELETE
	 * @param headers Map of headers
	 * @return {@link CloseableHttpResponse} response object
	 * @throws RestClientException if a error occur on route the request
	 */
	public CloseableHttpResponse doRequest (File file, String url, String method, Map<String, String> headers) throws RestClientException {
		return doRequest(url, method, headers, null, file);
	}
	
	private CloseableHttpResponse doRequest (String url, String method, Map<String, String> headers, String content, File file) throws RestClientException {
		try {
			CloseableHttpClient httpclient = httpClientFactory.createCloseableHttpClient(url);
			if (content != null)
				return httpclient.execute(requestFactory.createHttpUriRequest(url, method, content, headers));
			else if (file != null)
				return httpclient.execute(requestFactory.createHttpUriRequestUpload(url, method, file, headers));
			else 
				throw new Exception("Content or file is necessary to post");
		} catch (Exception e) {
			e.printStackTrace();
			String message = "Error on route request [%s, %s] :" +e.getMessage();
			message = String.format(message, url, method);
			throw new RestClientException(message, e, HttpStatus.SC_INTERNAL_SERVER_ERROR);
		} 
	}

	public void setHttpClientFactory(HttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}

}
