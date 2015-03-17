package com.redfire.nutrieduc.commonsvc.i18n;

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.redfire.nutrieduc.commonsvc.context.RequestContext;
import com.redfire.nutrieduc.entities.user.User;

/**
 * Class that translate messages according the language of User
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 */
@Component
public class MessageBundleService {
	
	@Autowired 
	private ApplicationContext applicationContext;
	
	private RequestContext requestContext;
	
	public String translate (String key) throws IOException {
		return translate(key, defineLocale(requestContext != null ? requestContext.getUser() : null));
	}
	
	public String translate (String key, Locale locale) throws IOException {
		return translate(key, null, locale);
	}
	
	public String translate (String key, Object[] params) throws IOException {
		Locale locale = defineLocale(requestContext != null ? requestContext.getUser() : null);
		return translate(key, params, locale);
	}
	
	private Locale defineLocale (User user) {
		if (user == null || user.getLang() == null)
			return Locale.US;
		return Locale.forLanguageTag(user.getLang());
	}
	
	public String translate (String key, Object[] params, Locale locale) throws IOException {
		return applicationContext.getMessage(key, params, locale);
	}

}

