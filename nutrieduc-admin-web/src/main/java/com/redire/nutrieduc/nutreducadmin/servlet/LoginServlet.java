package com.redire.nutrieduc.nutreducadmin.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import br.com.redfire.nutrieduc.common.Constants;
import br.com.redfire.nutrieduc.common.ErrorResponseBean;
import br.com.redfire.nutrieduc.common.LoginResponseBean;

import com.redfire.nutrieduc.entities.user.AdminUser;
import com.redfire.nutrieduc.restclient.BeanJsonConverter;
import com.redfire.nutrieduc.restclient.HeaderReader;
import com.redfire.nutrieduc.restclient.RestClient;
import com.redfire.nutrieduc.restclient.RestClientException;

@WebServlet("/"+ServletConstants.DOLOGIN)
public class LoginServlet extends HttpServlet {

	private RestClient restClient = new RestClient();
	private HeaderReader headerReader = new HeaderReader();
	private BeanJsonConverter jsonConverter = new BeanJsonConverter();

	/**
	 * 
	 */
	private static final long serialVersionUID = 7632319687442449315L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		int httpStatus = 0;

		try {
			String urlAppMangm = getURLDecoded();
			Map<String, String> headers = readAndDefineHeaders(req);
			String adminUserJson = getAdminUserJson(req);
			CloseableHttpResponse httpResponse = restClient.doRequest(adminUserJson, 
					urlAppMangm, 
					req.getMethod(), 
					headers);
			httpStatus = httpResponse.getStatusLine().getStatusCode();
			processLoginResponse(req, httpStatus, httpResponse, response);
		} catch (RestClientException e) {
			e.printStackTrace();
			httpStatus = e.getStatusCode();
			response.getWriter().write(e.getMessage());
		}		
		if (httpStatus == HttpStatus.SC_OK)
			redirectToMainPage(req, response);
		else
			redirectBackLogin(response);
	}
	
	public String getURLDecoded () throws IOException {
		String baseURL = ContextPropertyReader.getValue(Constants.SERVICE_GATEWAY_URL);
		String restUrl = "/adminlogin";
		return baseURL+restUrl;
	}
	
	private Map<String, String> readAndDefineHeaders (HttpServletRequest req) throws IOException {
		Map<String, String> headers = headerReader.readHeaders(req);
		addAditionalHeaders(headers);
		return headers;
	}

	public void addAditionalHeaders (Map<String, String> headers) throws IOException {
		headers.put(Constants.APP_CLIENT_KEY, ContextPropertyReader.getValue(Constants.APP_CLIENT_KEY));
	}
	
	public String getAdminUserJson (HttpServletRequest req) {
		AdminUser user = new AdminUser();
		user.setUsername(req.getParameter("username"));
		user.setPassword(req.getParameter("password"));
		return jsonConverter.convertToString(user);
	}

	private void processLoginResponse(HttpServletRequest req, int httpStatus,
			CloseableHttpResponse httpResponse, HttpServletResponse resp) throws IOException {
		String result = EntityUtils.toString(httpResponse.getEntity());
		if (httpStatus == HttpStatus.SC_OK) {
			LoginResponseBean bean = (LoginResponseBean) jsonConverter.convertToObject(result, LoginResponseBean.class); 
			req.getSession().setAttribute(Constants.USER_ATTRIBUTE, bean.getSessionId());
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
	
	private void redirectToMainPage(HttpServletRequest req,
			HttpServletResponse response) throws IOException {
		response.sendRedirect(req.getContextPath() + getLastModuleLoaded(req));
	}
	
	private AdminUser convertFromJson (String json) throws IOException {
		BeanJsonConverter converter = new BeanJsonConverter();
		AdminUser user = (AdminUser) converter.convertToObject(json, AdminUser.class);
		return user;
	}
	
	private void setCookieLanguage (AdminUser user, HttpServletRequest req, HttpServletResponse response) {
		String cookieName = defineUrlForCookie(req) + "_language";
		Cookie cookie = new Cookie(cookieName, "en-US");
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
