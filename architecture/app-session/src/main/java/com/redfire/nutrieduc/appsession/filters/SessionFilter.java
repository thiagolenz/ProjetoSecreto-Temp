package com.redfire.nutrieduc.appsession.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.redfire.nutrieduc.common.Constants;
import br.com.redfire.nutrieduc.common.ErrorResponseBean;

import com.redfire.nutrieduc.appsession.exception.AppSessionException;
import com.redfire.nutrieduc.appsession.servlets.UserLoginStorageBean;
import com.redfire.nutrieduc.appsession.storage.UserLocalStorage;
import com.redfire.nutrieduc.appsession.validators.ClientAppValidator;
import com.redfire.nutrieduc.appsession.validators.PublicRequestValidator;
import com.redfire.nutrieduc.appsession.validators.UserLoggedInValidator;
import com.redfire.nutrieduc.restclient.BeanJsonConverter;

/**
 * This filter Intercept each request and validate if the App client is valid and the user has
 * @author thiagolenz
 * @since Aug 25, 2014
 *
 */
@WebFilter("/handler/*")
public class SessionFilter implements Filter {
	
	private ClientAppValidator clientTierValidator = new ClientAppValidator();
	private UserLoggedInValidator userLoggedInValidator = new UserLoggedInValidator();
	private PublicRequestValidator publicRequestValidator = new PublicRequestValidator();
	private BeanJsonConverter beanStringConverter = new BeanJsonConverter();
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String sessionId = request.getHeader(Constants.USER_ATTRIBUTE);
		
		try {
			clientTierValidator.validateClientApp(request.getHeader(Constants.APP_CLIENT_KEY));
			if (request.getHeader(Constants.USER_ATTRIBUTE) != null){	
				userLoggedInValidator.validateUserLoggedIn(sessionId);
				putHeaderUserId(request, sessionId);
			} else 
				publicRequestValidator.validatePublicRequest(request.getHeader(Constants.PUBLIC_REQUEST_KEY));
			
			chain.doFilter(request, resp);
		} catch (AppSessionException e) {
			ErrorResponseBean errorBean = createErrorResponseBean(e);
			response.getWriter().write(beanStringConverter.convertToString(errorBean));
			response.setStatus(e.getStatus());
		}			
	}

	private void putHeaderUserId(HttpServletRequest request, String sessionId) {
		UserLoginStorageBean storageBean = UserLocalStorage.get(sessionId);
		String userId = beanStringConverter.convertToString(storageBean.getBean());
		request.getSession().setAttribute(Constants.USER_ID, userId);
		request.getSession().setAttribute(Constants.REQUEST_IS_ADMIN, storageBean.isAdmin());
	}
	
	public ErrorResponseBean createErrorResponseBean (AppSessionException e) {
		ErrorResponseBean errorBean = new ErrorResponseBean();
		errorBean.setErrorCode(""+e.getStatus());
		errorBean.setErrorDescription(e.getMessage());
		return errorBean;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}


}
