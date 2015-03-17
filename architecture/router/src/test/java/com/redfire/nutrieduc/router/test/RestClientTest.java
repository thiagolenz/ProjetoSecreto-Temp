package com.redfire.nutrieduc.router.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.redfire.nutrieduc.restclient.HttpClientFactory;
import com.redfire.nutrieduc.restclient.RestClient;
import com.redfire.nutrieduc.restclient.RestClientException;

public class RestClientTest {
	
	@Mock
	HttpClientFactory clientFactory;
	
	@Mock
	CloseableHttpClient closeableHttpClient;
	
	@Mock
	CloseableHttpResponse closeableHttpResponse;
	
	RestClient restClient;
	
	
	@Before
	public void init () throws Exception {
		MockitoAnnotations.initMocks(this);
		restClient = new RestClient();
		restClient.setHttpClientFactory(clientFactory);
		Mockito.when(clientFactory.createCloseableHttpClient(Mockito.anyString())).thenReturn(closeableHttpClient);
		Mockito.when(closeableHttpClient.execute((HttpUriRequest)Mockito.any())).thenReturn(closeableHttpResponse);
	}

	@Test
	public void grantThatGoSuccess () throws RestClientException, ClientProtocolException, IOException {
		Mockito.when(closeableHttpClient.execute((HttpUriRequest)Mockito.any())).thenReturn(closeableHttpResponse);
		CloseableHttpResponse doRequest = restClient.doRequest("", "", "GET", new HashMap<String, String>());
		Assert.assertNotNull(doRequest);
	}
	
	@Test(expected=RestClientException.class)
	public void grantThatThrowsOnCrash () throws RestClientException, ClientProtocolException, IOException {
		Mockito.when(closeableHttpClient.execute((HttpUriRequest)Mockito.any())).thenThrow(new IOException());
		restClient.doRequest("", "", "GET", new HashMap<String, String>());
	}
}
