package com.redfire.nutrieduc.appsession.test;

import java.io.PrintWriter;
import java.util.UUID;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.redfire.nutrieduc.common.Constants;

import com.redfire.nutrieduc.appsession.filters.SessionFilter;
import com.redfire.nutrieduc.appsession.servlets.UserLoginStorageBean;
import com.redfire.nutrieduc.appsession.storage.UserLocalStorage;
import com.redfire.nutrieduc.appsession.test.mock.FilterChainMock;
import com.redfire.nutrieduc.appsession.test.mock.HttpServletRequestMock;
import com.redfire.nutrieduc.appsession.test.mock.HttpServletResponseMock;
import com.redfire.nutrieduc.entities.user.User;

public class SessionFilterTest {
	HttpServletRequestMock request = new HttpServletRequestMock();
	HttpServletResponseMock response = new HttpServletResponseMock();
	FilterChainMock  filterChain = new FilterChainMock();
	@Mock PrintWriter writer;
	SessionFilter filter = new  SessionFilter();
	
	@Before
	public void init () {
		MockitoAnnotations.initMocks(this);
		response.setPrintWriter(writer);
	}
	
	@Test
	public void grantThatReturnCorrectCodeWhenAppIsNoRecognized () throws Exception {
		filter.doFilter(request, response, filterChain);
		Assert.assertEquals(HttpStatus.SC_NOT_ACCEPTABLE, response.getStatus());
	}
	
	@Test
	public void grantThatReturnCorrectCodeWhenUserIsNotLoggedIn () throws Exception {
		request.addHeader(Constants.APP_CLIENT_KEY, "92bb4731-f7c7-4708-aaff-34fdd6db215a");
		request.addHeader(Constants.USER_ATTRIBUTE, UUID.randomUUID().toString());
		filter.doFilter(request, response, filterChain);
		Assert.assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatus());
	}
	
	@Test
	public void grantThatReturnCorrectCodeWhenHasNoPublicKey () throws Exception {
		request.addHeader(Constants.APP_CLIENT_KEY, "92bb4731-f7c7-4708-aaff-34fdd6db215a");
		request.addHeader(Constants.PUBLIC_REQUEST_KEY, UUID.randomUUID().toString());
		filter.doFilter(request, response, filterChain);
		Assert.assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatus());
	}
	
	@Test
	public void grantThatDoChain () throws Exception {
		request.addHeader(Constants.APP_CLIENT_KEY, "92bb4731-f7c7-4708-aaff-34fdd6db215a");
		request.addHeader(Constants.USER_ATTRIBUTE, simulateLogin());
		filter.doFilter(request, response, filterChain);
		Assert.assertTrue(filterChain.isChained());
	}
	
	public String simulateLogin () {
		String sessionToken = UUID.randomUUID().toString();
		UserLocalStorage.put(sessionToken, new UserLoginStorageBean(new User(), false));
		return sessionToken;
	}
}
