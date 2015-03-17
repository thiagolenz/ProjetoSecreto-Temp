package com.redfire.nutrieduc.appsession.validators;

import java.util.UUID;

import org.apache.http.HttpStatus;

import com.redfire.nutrieduc.appsession.exception.AppSessionException;
import com.redfire.nutrieduc.appsession.storage.UserLocalStorage;

/**
 * Class responsible to verify if a given token is from a valid user session
 * @author thiagolenz
 * @since Aug 25, 2014
 */
public class UserLoggedInValidator {

	public void validateUserLoggedIn (String sessionId) throws AppSessionException {
		if (!isUserLoggedIn(sessionId))
			throw new AppSessionException("User not logged in.", HttpStatus.SC_UNAUTHORIZED);
	}
	
	public boolean isUserLoggedIn (String sessionId) {
		return sessionId != null && UserLocalStorage.get(sessionId) != null;
	}
	
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID());
	}
}
