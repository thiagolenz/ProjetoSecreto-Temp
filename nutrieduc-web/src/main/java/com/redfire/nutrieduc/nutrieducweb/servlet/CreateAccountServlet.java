package com.redfire.nutrieduc.nutrieducweb.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import br.com.redfire.nutrieduc.common.ErrorResponseBean;

import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.restclient.RestClientException;

@WebServlet("/"+ServletConstants.DOCREATEACCOUNT)
public class CreateAccountServlet extends LoginServlet {

	private static final long serialVersionUID = 7632319687442449315L;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		int httpStatus = 0;
		try {
			String urlAppMangm = getURLDecoded("/handler/userProfile/user/createPatientUser");
			Map<String, String> headers = readAndDefineHeaders(req);
			String userJson = getUserJson(req);
			CloseableHttpResponse httpResponse = restClient.doRequest(userJson, 
					urlAppMangm, 
					req.getMethod(), 
					headers);
			httpStatus = httpResponse.getStatusLine().getStatusCode();
			processCreateAccountResponse(req, httpStatus, httpResponse, response);
		} catch (RestClientException e) {
			httpStatus = e.getStatusCode();
			response.getWriter().write(e.getMessage());
		}		
		if (httpStatus == HttpStatus.SC_OK)
			super.service(req, response);
		else
			redirectBackCreateAccount(response);
	}
	
	public String getUserJson (HttpServletRequest req) {
		User user = new User();
		defineLogin(req, user);
		definePassword(req, user);		
		defineName(req, user);
		return jsonConverter.convertToString(user);
	}

	private void definePassword (HttpServletRequest req, User user) {
		String password = req.getParameter("password");
		user.setPassword(password);
	}

	private void defineLogin(HttpServletRequest req, User user) {
		String login = req.getParameter("email");
		user.setEmail(login);
	}
	
	private void defineName (HttpServletRequest req, User user) {
		String name = req.getParameter("name");
		user.setName(name);
	}

	private void processCreateAccountResponse (HttpServletRequest req, int httpStatus,
			CloseableHttpResponse httpResponse, HttpServletResponse resp) throws IOException, ServletException {
		String result = EntityUtils.toString(httpResponse.getEntity());
		if (httpStatus != HttpStatus.SC_OK) {
			ErrorResponseBean errorResponseBean = (ErrorResponseBean) jsonConverter.convertToObject(result, ErrorResponseBean.class); 
			req.getSession().setAttribute("error", errorResponseBean.getErrorDescription());
		}
	}

	private void redirectBackCreateAccount(HttpServletResponse response)
			throws IOException {
		response.sendRedirect(ServletConstants.CREATEACCOUNT);
	}
}
