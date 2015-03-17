package com.redfire.nutrieduc.router.test;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Test;

import com.redfire.nutrieduc.restclient.HttpUriRequestBuilder;
import com.redfire.nutrieduc.restclient.RestClientException;

public class HttpUriRequestFactoryTest {

	HttpUriRequestBuilder factory;
	
	@Before 
	public void init () {
		System.setProperty("app.env", "D");
		factory = new HttpUriRequestBuilder();
	}
	
	@Test
	public void grantThatCreateAndCallHttpPost () throws RestClientException {
		HttpUriRequest createHttpUriRequest = factory.createHttpUriRequest("", "POST", "", new HashMap<String, String>());
		assertTrue(createHttpUriRequest instanceof HttpPost);
	}

	@Test
	public void grantThatCreateAndCallHttpGet () throws RestClientException {
		HttpUriRequest createHttpUriRequest = factory.createHttpUriRequest("", "GET", "",  new HashMap<String, String>());
		assertTrue(createHttpUriRequest instanceof HttpGet);
	}
	
	@Test
	public void grantThatCreateAndCallHttpPut () throws RestClientException {
		HttpUriRequest createHttpUriRequest = factory.createHttpUriRequest("", "PUT", "",  new HashMap<String, String>());
		assertTrue(createHttpUriRequest instanceof HttpPut);
	}
	
	@Test
	public void grantThatCreateAndCallHttpDelete () throws RestClientException {
		HttpUriRequest createHttpUriRequest = factory.createHttpUriRequest("", "DELETE", "",  new HashMap<String, String>());
		assertTrue(createHttpUriRequest instanceof HttpDelete);
	}
	
	@Test (expected=RestClientException.class)
	public void grantThatWhenDontFindTypeThrows () throws RestClientException {
		factory.createHttpUriRequest("", "DONT_EXISTS", "",  new HashMap<String, String>());
	}
}
