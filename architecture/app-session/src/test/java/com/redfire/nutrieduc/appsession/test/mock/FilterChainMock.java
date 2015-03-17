package com.redfire.nutrieduc.appsession.test.mock;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FilterChainMock implements FilterChain {
	private boolean chained = false;
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1)
			throws IOException, ServletException {
		chained = true;
	}
	
	public boolean isChained() {
		return chained;
	}
	

}
