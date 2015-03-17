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

@WebServlet("/"+ServletConstants.DOCHANGEPASSWORD)
public class ChangePasswordServlet extends DefaultSevlet{
	private ChangePasswordToken changePasswordToken;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7189015685313752140L;
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		validateConfirmPassword(req, resp);
		int httpStatus = 0;
		try {
			String urlAppMangm = getURLDecoded("/handler/userProfile/changePassword/finalizeChangeRequest");
			Map<String, String> headers = readAndDefineHeaders(req);
			headers.remove(Constants.USER_ATTRIBUTE);
			String userJson = getRequestJson(req);
			CloseableHttpResponse httpResponse = restClient.doRequest(userJson, 
					urlAppMangm, 
					req.getMethod(), 
					headers);
			httpStatus = httpResponse.getStatusLine().getStatusCode();
			processResult(httpStatus, req, resp, httpResponse);
		} catch (RestClientException e) {
			httpStatus = e.getStatusCode();
			resp.getWriter().write(e.getMessage());
		}		
	}

	private void validateConfirmPassword (HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (!req.getParameter("password").equals(req.getParameter("confirmPassword"))) {
			req.getSession().setAttribute("error", "The passwords are different");
			resp.sendRedirect(ServletConstants.CHANGEPASSWORD);
		}
	}

	private String getRequestJson (HttpServletRequest req) {
		this.changePasswordToken = (ChangePasswordToken) req.getSession().getAttribute("CHANGE_PASSWORD_TOKEN");
		this.changePasswordToken.getUser().setPassword(req.getParameter("password"));
		return jsonConverter.convertToString(changePasswordToken);
	}
	
	private void processResult ( int httpStatus,
			HttpServletRequest request, 
			HttpServletResponse response, 
			CloseableHttpResponse httpResponse) throws ParseException, IOException, ServletException {
		String result = EntityUtils.toString(httpResponse.getEntity());

		if (httpStatus == HttpStatus.SC_OK) {
			request.getSession().setAttribute("CHANGE_PASSWORD_TOKEN", null);
			request.getSession().removeAttribute("error");
			request.setAttribute("login_temp", this.changePasswordToken.getUser().getEmail());
			request.setAttribute("password_temp", this.changePasswordToken.getUser().getPassword());
			new LoginServlet().service(request, response);
		} else {
			ErrorResponseBean errorResponseBean = (ErrorResponseBean) jsonConverter.convertToObject(result, ErrorResponseBean.class); 
			request.getSession().setAttribute("error", errorResponseBean.getErrorDescription());
			response.sendRedirect(ServletConstants.CHANGEPASSWORD);
		}
		
	}
}
