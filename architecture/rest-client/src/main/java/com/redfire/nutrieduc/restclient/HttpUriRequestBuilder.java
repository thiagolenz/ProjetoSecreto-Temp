package com.redfire.nutrieduc.restclient;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import br.com.redfire.nutrieduc.common.Constants;

/**
 * Class responsible to build the request URI.
 * @author thiagolenz
 * @since 25/08/2014
 */
public class HttpUriRequestBuilder {
	
	/**
	 * This is the main method of the class. Receive the request params and build the {@link HttpUriRequest}
	 * @param url HTTPS/HTTP url 
	 * @param method accepts like POST
	 * @param content body content 
	 * @param headers Map of headers
	 * @return {@link HttpUriRequest} object
	 * @throws RestClientException when: Some unexpected error occur; Http Method not found.
	 */
	
	public HttpUriRequest createHttpUriRequestUpload (String url, String method, File file, Map<String, String> headers) throws RestClientException {
		HttpUriRequest uriRequest = null;
		try {
			if (HttpPost.METHOD_NAME.equals(method)) 
				uriRequest = createHttpPostMultipart(url, file);
		} catch (UnsupportedEncodingException e) {
			throw new RestClientException("Encoding error : "+ e.getMessage(), HttpStatus.SC_BAD_REQUEST);
		}

		validateUriAndAddHeaders(method, headers, uriRequest);

		return uriRequest;
	}

	/**
	 * This is the main method of the class. Receive the request params and build the {@link HttpUriRequest}
	 * @param url HTTPS/HTTP url 
	 * @param method accepts like POST, GET, PUT or DELETE
	 * @param content body content 
	 * @param headers Map of headers
	 * @return {@link HttpUriRequest} object
	 * @throws RestClientException when: Some unexpected error occur; Http Method not found.
	 */
	public HttpUriRequest createHttpUriRequest (String url, String method, String content, Map<String, String> headers) throws RestClientException {
		HttpUriRequest uriRequest = null;
		try {
			if (HttpPost.METHOD_NAME.equals(method)) 
				uriRequest = createHttpPost(url, content);
			else if (HttpGet.METHOD_NAME.equals(method)) 
				uriRequest = createHttpGet(url);
			else if (HttpDelete.METHOD_NAME.equals(method))
				uriRequest = createHttpDelete(url);
			else if (HttpPut.METHOD_NAME.equals(method)) 
				uriRequest = createHttpPut(url, content);
		} catch (UnsupportedEncodingException e) {
			throw new RestClientException("Encoding error : "+ e.getMessage(), HttpStatus.SC_BAD_REQUEST);
		}

		validateUriAndAddHeaders(method, headers, uriRequest);

		return uriRequest;
	}

	private void validateUriAndAddHeaders(String method,
			Map<String, String> headers, HttpUriRequest uriRequest)
			throws RestClientException {
		if (uriRequest == null)
			throw new RestClientException("Method not found : "+ method, HttpStatus.SC_METHOD_NOT_ALLOWED);

		addHeaders(uriRequest, headers);
	}

	public void addHeaders (HttpUriRequest request, Map<String, String> headers) {
		for (String key : headers.keySet()) {
			Header [] headersArray = request.getHeaders(key);
			if (headersArray == null || headersArray.length == 0)
				request.addHeader(key, headers.get(key));
		}
	}

	private HttpPost createHttpPost (String url, String content) throws UnsupportedEncodingException {
		HttpPost post = new HttpPost(url);
		post.setEntity(configureStringEntity(content));
		return post;
	}

	private HttpGet createHttpGet (String url) {
		return new HttpGet(url);
	}

	private HttpDelete createHttpDelete (String url){
		return new HttpDelete(url);
	}

	private HttpPut createHttpPut (String url, String content) throws UnsupportedEncodingException {
		HttpPut put = new HttpPut(url);
		put.setEntity(configureStringEntity(content));
		return put;
	}

	private StringEntity configureStringEntity(String content)
			throws UnsupportedEncodingException {
		StringEntity entity = new StringEntity(content, "UTF-8");
		entity.setContentType(Constants.APPLICATION_JSON + "; charset=UTF-8");
		return entity;
	}
	
	private HttpPost createHttpPostMultipart (String url, File file) throws UnsupportedEncodingException {
		HttpPost post = new HttpPost(url);

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		StringBody comment = new StringBody("A binary file of some kind", ContentType.DEFAULT_BINARY);
		FileBody cbFile = new FileBody(file);
		builder.addPart("file", cbFile);
		builder.addPart("coment", comment);

		HttpEntity entity = builder.build();
		post.setEntity(entity);
		return post;
	}
}
