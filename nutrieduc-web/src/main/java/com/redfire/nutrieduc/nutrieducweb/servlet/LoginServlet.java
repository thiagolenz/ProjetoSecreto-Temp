package com.redfire.nutrieduc.nutrieducweb.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import br.com.redfire.nutrieduc.common.Constants;
import br.com.redfire.nutrieduc.common.ErrorResponseBean;
import br.com.redfire.nutrieduc.common.LoginResponseBean;

import com.redfire.nutrieduc.entities.user.SocialMediaProvider;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.restclient.BeanJsonConverter;
import com.redfire.nutrieduc.restclient.RestClientException;

@WebServlet("/"+ServletConstants.DOLOGIN)
public class LoginServlet extends DefaultSevlet {

	private static final long serialVersionUID = 7632319687442449315L;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		int httpStatus = 0;
		try {
			String urlAppMangm = getURLDecoded("/userlogin");
			Map<String, String> headers = readAndDefineHeaders(req);
			String userJson = getUserJson(req);
			CloseableHttpResponse httpResponse = restClient.doRequest(userJson, 
					urlAppMangm, 
					req.getMethod(), 
					headers);
			httpStatus = httpResponse.getStatusLine().getStatusCode();
			processLoginResponse(req, httpStatus, httpResponse, response);
		} catch (RestClientException e) {
			httpStatus = e.getStatusCode();
			response.getWriter().write(e.getMessage());
		}		
		if (httpStatus == HttpStatus.SC_OK)
			redirectToMainPage(req, response);
		else
			redirectBackLogin(response);
	}
	
	public String getUserJson (HttpServletRequest req) {
		User user = new User();
		defineLogin(req, user);
		definePassword(req, user);
		user.setAccessToken(req.getParameter("accessToken"));
		
		String socialMediaProvider = req.getParameter("socialMediaProvider");
		if (socialMediaProvider != null)
			user.setSocialMediaProvider(SocialMediaProvider.valueOf(socialMediaProvider));
		
		return jsonConverter.convertToString(user);
	}

	private void definePassword(HttpServletRequest req, User user) {
		String password = req.getParameter("password");
		if (password == null)
			password = (String) req.getAttribute("password_temp");
		user.setPassword(password);
	}

	private void defineLogin(HttpServletRequest req, User user) {
		String email = req.getParameter("email");
		if (email == null)
			email = (String) req.getAttribute("email_temp");
		user.setEmail(email);
	}

	private void processLoginResponse(HttpServletRequest req, int httpStatus,
			CloseableHttpResponse httpResponse, HttpServletResponse resp) throws IOException {
		String result = EntityUtils.toString(httpResponse.getEntity());
		if (httpStatus == HttpStatus.SC_OK) {
			LoginResponseBean bean = (LoginResponseBean) jsonConverter.convertToObject(result, LoginResponseBean.class); 
			req.getSession().setAttribute(Constants.USER_ATTRIBUTE, bean.getSessionId());
			req.getSession().setAttribute("contextUser", bean.getSessionBean());
			setCookieLanguage(convertFromJson((String)bean.getSessionBean()), req, resp );
		} else {
			ErrorResponseBean errorResponseBean = (ErrorResponseBean) jsonConverter.convertToObject(result, ErrorResponseBean.class); 
			req.getSession().setAttribute("error", errorResponseBean.getErrorDescription());
		}
	}

	private void redirectBackLogin(HttpServletResponse response)
			throws IOException {
		response.sendRedirect(ServletConstants.LOGIN);
	}
	
	protected void redirectToMainPage(HttpServletRequest req,
			HttpServletResponse response) throws IOException {
		response.sendRedirect(req.getContextPath() + getLastModuleLoaded(req));
	}
	
	private User convertFromJson (String json) throws IOException {
		BeanJsonConverter converter = new BeanJsonConverter();
		User user = (User) converter.convertToObject(json, User.class);
		return user;
	}
	
	private void setCookieLanguage (User user, HttpServletRequest req, HttpServletResponse response) {
		String cookieName = defineUrlForCookie(req) + "_language";
		Cookie cookie = new Cookie(cookieName, user.getLang());
		response.addCookie(cookie);
	}
	
	private String getLastModuleLoaded (HttpServletRequest req) {
		String cookieModule = defineUrlForCookie(req) + "_currentModule";
		for (Cookie cookie : req.getCookies()) 
			if (cookie.getName().equals(cookieModule))
				return "/#!"+cookie.getValue();
		return "";
	}
	
	private String defineUrlForCookie (HttpServletRequest request) {
		String url = request.getServerName();
		String context = request.getContextPath();
		if (context.length() > 1) {
			url += "_" + context.substring(1, context.length()).replaceAll("-", "_");
		}
		return url;
	}
}
