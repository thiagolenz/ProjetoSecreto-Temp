package com.redfire.nutrieduc.nutrieducweb.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import br.com.redfire.nutrieduc.common.Constants;
import br.com.redfire.nutrieduc.common.ErrorResponseBean;

import com.redfire.nutrieduc.entities.user.ChangePasswordToken;
import com.redfire.nutrieduc.restclient.RestClientException;

@WebServlet("/changePassword")
public class ChangePasswordLinkServlet extends DefaultSevlet {
	private static final long serialVersionUID = -7436236790084855347L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		String token = req.getParameter("token");
		int httpStatus = 0;
		try {	
			String urlAppMangm = getURLDecoded("/handler/userProfile/changePassword/startChangeRequest");
			Map<String, String> headers = readAndDefineHeaders(req);
			headers.remove(Constants.USER_ATTRIBUTE);
			String userJson = getRequestJson(token);
			CloseableHttpResponse httpResponse = restClient.doRequest(userJson, 
					urlAppMangm, 
					"POST", 
					headers);
			httpStatus = httpResponse.getStatusLine().getStatusCode();
			processResult(httpStatus,req, response, httpResponse);	
		} catch (RestClientException e) {
			httpStatus = e.getStatusCode();
			response.getWriter().write(e.getMessage());
		}	
		
	}
	
	private void processResult ( int httpStatus,
								 HttpServletRequest request, 
								 HttpServletResponse response, 
								 CloseableHttpResponse httpResponse) throws ParseException, IOException, ServletException {
		String result = EntityUtils.toString(httpResponse.getEntity());
		
		if (httpStatus == HttpStatus.SC_OK) {
			ChangePasswordToken bean = (ChangePasswordToken) jsonConverter.convertToObject(result, ChangePasswordToken.class);
			request.getSession().setAttribute("CHANGE_PASSWORD_TOKEN", bean);
			request.getSession().removeAttribute("error");
		} else {
			ErrorResponseBean errorResponseBean = (ErrorResponseBean) jsonConverter.convertToObject(result, ErrorResponseBean.class); 
			request.getSession().setAttribute("error", errorResponseBean.getErrorDescription());
		}
		response.sendRedirect(ServletConstants.CHANGEPASSWORD);
	}
	
	private String getRequestJson (String token) {
		ChangePasswordToken changePasswordToken = new ChangePasswordToken();
		changePasswordToken.setToken(token);
		return jsonConverter.convertToString(changePasswordToken);
	}
}
