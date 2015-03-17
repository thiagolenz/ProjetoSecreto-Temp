package com.redfire.nutrieduc.router.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.redfire.nutrieduc.restclient.RestClient;
import com.redfire.nutrieduc.restclient.RestClientException;
import com.redfire.nutrieduc.router.RequestHandler;
import com.redfire.nutrieduc.router.RouterException;
import com.redfire.nutrieduc.router.URLDecoder;

@Ignore
public class RequestHandlerTest {
	@Mock PrintWriter writer;
	@Mock BufferedReader reader;
	@Mock CloseableHttpResponse closeableHttpResponse;
	@Mock URLDecoder urlDecoder;
	@Mock RestClient restClient;
	@Mock StatusLine statusLine;
	static int count = 0;
	
	RequestHandler handlerServlet;
	
	@Before
	public void init () throws RouterException, IOException {
		System.setProperty("app.env", "D");
		MockitoAnnotations.initMocks(this);
		
		handlerServlet = new RequestHandler("aaaaa", writer, "POST", new HashMap<String, String>(), null, null);
		handlerServlet.setRestClient(restClient);
		handlerServlet.setUrlDecoder(urlDecoder);
		
		Mockito.when(closeableHttpResponse.getEntity()).thenReturn(new StringEntity("aaaaa"));
		Mockito.when(closeableHttpResponse.getStatusLine()).thenReturn(statusLine);
		Mockito.when(statusLine.getStatusCode()).thenReturn(200);
		
		Mockito.when(reader.readLine()).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				if (RequestHandlerTest.count == 0) {
					RequestHandlerTest.count++;
					return "aaaaa";
				}
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void grantThatWriteResponseSuccess () throws Exception {
		Mockito.when(restClient.doRequest(Mockito.anyString(),Mockito.anyString(), Mockito.anyString(), Mockito.anyMap())).thenReturn(closeableHttpResponse);
		int service = handlerServlet.service();
		Assert.assertEquals(200, service);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void grantThatWriteResponseError () throws Exception {
		Mockito.when(restClient.doRequest(Mockito.anyString(),Mockito.anyString(), Mockito.anyString(), Mockito.anyMap())).thenThrow(new RestClientException("teste", HttpStatus.SC_BAD_GATEWAY));
		int service = handlerServlet.service();
		Assert.assertEquals(HttpStatus.SC_BAD_GATEWAY, service);
	}
	
}

