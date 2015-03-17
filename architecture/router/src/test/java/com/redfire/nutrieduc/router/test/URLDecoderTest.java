package com.redfire.nutrieduc.router.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.redfire.nutrieduc.router.URLDecoder;

public class URLDecoderTest {
	
	private URLDecoder urlDecoder;
	
	@Before
	public void init () {
		System.setProperty("app.env", "D");
		urlDecoder = new URLDecoder();
	}

	@Test
	public void grantThatDecodeValidURL () {
		String decodeURL = urlDecoder.decodeURL("/defaultwebapp/submodule/usecase/?teste=aaa");
		assertEquals("http://localhost:8080/default-webapp/submodule/usecase/?teste=aaa", decodeURL);
	}
}
