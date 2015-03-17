package com.redfire.nutrieduc.appsession.validators;

import java.io.IOException;
import java.util.Properties;

import org.apache.http.HttpStatus;

import com.redfire.nutrieduc.appsession.exception.AppSessionException;

public class PublicRequestValidator {

	private static Properties authorizedTokens;

	static {
		authorizedTokens = new Properties();
		try {
			authorizedTokens.load(ClientAppValidator.class.getClassLoader().getResourceAsStream("authorizedPublicTokens.properties"));
		} catch (IOException e) {
			throw new RuntimeException("error on load properties ", e);
		}
	}
	
	public void validatePublicRequest (String token) throws AppSessionException {
		if (!isAuthorizedToken(token)) 
			throw new AppSessionException("Request non authorized", HttpStatus.SC_UNAUTHORIZED);
	}
	
	public boolean isAuthorizedToken (String token) {
		return token != null && authorizedTokens.contains(token);
	}
}
