package com.redfire.nutrieduc.commonsvc.context;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.redfire.nutrieduc.common.Constants;

import com.redfire.nutrieduc.entities.account.Account;
import com.redfire.nutrieduc.entities.user.AdminUser;
import com.redfire.nutrieduc.entities.user.User;

/**
 * Aspect that is executed before each Controller or BundleService. It reads the headers and configure the {@link RequestContext} object
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 */
@Aspect
public class RequestContextAspect {
	
	@Autowired
	HttpServletRequest httpServletRequest;

	@Before("bean(*Controller) or bean(*BundleService)")
	public void logBefore(JoinPoint joinPoint) throws IllegalArgumentException, IllegalAccessException, IOException {
		for (Field field : joinPoint.getTarget().getClass().getDeclaredFields()) {
			if (field.getType().equals(RequestContext.class)) {
				field.setAccessible(true);
				field.set(joinPoint.getTarget(), createRequestContext());
				break;
			}
		}
	}
	
	public RequestContext createRequestContext () throws IOException {
		RequestContext context = new RequestContext();
		confitureUserContext(context);
		
		String currentPage = httpServletRequest.getHeader("rf-pagination-currentpage");
		if (currentPage == null)
			currentPage = "0";
		context.setCurrentPage(Integer.valueOf(currentPage));
		
		String limit = httpServletRequest.getHeader("rf-pagination-recordsrange");
		if (limit == null)
			limit = "0";
		context.setLimit(Integer.valueOf(limit));
		return context;
	}

	private void confitureUserContext(RequestContext context) throws IOException {
		Object userContext = getUserContext();
		if (userContext instanceof User) {
			context.setUser((User) userContext);
			context.setAccount(context.getUser().getAccount());
		}
		else if (userContext instanceof AdminUser ){
			context.setAdminUser((AdminUser)userContext);
			ObjectMapper objectMapper = new ObjectMapper();
			String obj = httpServletRequest.getHeader("rf-account-request");
			if (obj != null)
				context.setAccount(objectMapper.readValue(obj, Account.class));
		}
	}
	
	private Object getUserContext () throws IOException {
		String obj = httpServletRequest.getHeader(Constants.USER_ID);
		if (obj != null) {
			boolean isAdmin = Boolean.valueOf(httpServletRequest.getHeader(Constants.REQUEST_IS_ADMIN));
			ObjectMapper objectMapper = new ObjectMapper();
			if (isAdmin)
				return (AdminUser) objectMapper.readValue(obj, AdminUser.class);
			return (User) objectMapper.readValue(obj, User.class);
		}
		return null;
	}
 
}
