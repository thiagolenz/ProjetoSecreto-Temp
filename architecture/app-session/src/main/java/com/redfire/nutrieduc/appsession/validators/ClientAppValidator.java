package com.redfire.nutrieduc.appsession.validators;

import java.io.IOException;
import java.util.Properties;

import org.apache.http.HttpStatus;

import com.redfire.nutrieduc.appsession.exception.AppSessionException;

/**
 * Class responsible to validate if the app client of the request is valid
 * @author thiagolenz
 * @since Aug 25, 2014
 *
 */
public class ClientAppValidator {
	private static Properties authorizedClients;

	static {
		authorizedClients = new Properties();
		try {
			authorizedClients.load(ClientAppValidator.class.getClassLoader().getResourceAsStream("authorizedAppClients.properties"));
		} catch (IOException e) {
			throw new RuntimeException("error on load properties ", e);
		}
	}
	
	/**
	 * Given a token validate if
	 * @param token
	 * @throws AppSessionException Application not authorized
	 */
	public void validateClientApp (String token) throws AppSessionException {
		if (!isAuthorizedApp(token)) 
			throw new AppSessionException("Application non authorized", HttpStatus.SC_NOT_ACCEPTABLE);
	}
	
	public boolean isAuthorizedApp (String token) {
		return token != null && authorizedClients.contains(token);
	}

}
