package com.redfire.nutrieduc.appsession.servlets;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import br.com.redfire.nutrieduc.common.Constants;
import br.com.redfire.nutrieduc.common.ErrorResponseBean;
import br.com.redfire.nutrieduc.common.LoginResponseBean;

import com.redfire.nutrieduc.appsession.exception.AppSessionException;
import com.redfire.nutrieduc.appsession.storage.UserLocalStorage;
import com.redfire.nutrieduc.appsession.validators.ClientAppValidator;
import com.redfire.nutrieduc.restclient.BeanJsonConverter;
import com.redfire.nutrieduc.restclient.ContentReader;
import com.redfire.nutrieduc.restclient.HeaderReader;
import com.redfire.nutrieduc.restclient.RestClient;
import com.redfire.nutrieduc.restclient.RestClientException;
import com.redfire.nutrieduc.router.URLDecoder;

/**
 * Abstract class of LoginServlet 
 * @author thiagolenz
 * @since Aug 25, 2014
 *
 */
public abstract class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4416916192395323453L;	
	protected HeaderReader headerReader = new HeaderReader();
	protected ContentReader contentReader = new ContentReader();
	protected URLDecoder decoder = new URLDecoder();
	protected RestClient restClient = new RestClient();
	protected ClientAppValidator clientAppValidator = new ClientAppValidator();
	protected BeanJsonConverter beanStringConverter = new BeanJsonConverter();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int status = 0;
		String decodedUrl = null;
		try {
			clientAppValidator.validateClientApp(request.getHeader(Constants.APP_CLIENT_KEY));
			decodedUrl = decodeLoginURL(request);
			String content = contentReader.getJSONContent(request.getReader());
		
			CloseableHttpResponse httpResponse = restClient.doRequest(content, 
					decodedUrl, 
					request.getMethod(), 
					headerReader.readHeaders(request));
			status = httpResponse.getStatusLine().getStatusCode();
			processLoginResult(response, status, httpResponse);
			
		} catch (AppSessionException e) {
			status = handleAppSessionException(response, e);
		} catch (RestClientException e) {
			status = handleRestClientException(response, e);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error on connect with the URL: "+ decodedUrl);
			status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
			writeConnectionError(response);
		}
		response.setStatus(status);
		response.setContentType(Constants.APPLICATION_JSON);
	}

	private void processLoginResult(HttpServletResponse response, int status,
			CloseableHttpResponse httpResponse) throws IOException {
		if (status == HttpStatus.SC_OK) {
			UserLoginStorageBean beanStorage = getSessionEntity(httpResponse.getEntity());
			String token = saveLoginData(beanStorage);
			String beanJson = beanStringConverter.convertToString(beanStorage.getBean());
			LoginResponseBean responseBean = new LoginResponseBean(token, beanJson);
			response.getWriter().write(beanStringConverter.convertToString(responseBean));
		} else 
			response.getWriter().write(EntityUtils.toString(httpResponse.getEntity()));
	}

	private int handleRestClientException(HttpServletResponse response,
			RestClientException e) throws IOException {
		int status;
		ErrorResponseBean errorBean = createErrorResponseBean(e.getStatusCode(), e.getMessage());
		response.getWriter().write(beanStringConverter.convertToString(errorBean));
		status = e.getStatusCode();
		return status;
	}
	
	
	public String saveLoginData (Object bean) {
		String sessionToken = UUID.randomUUID().toString();
		UserLocalStorage.put(sessionToken, bean);
		return sessionToken;
	}

	private int handleAppSessionException(HttpServletResponse response,
			AppSessionException e) throws IOException {
		int status;
		ErrorResponseBean errorBean = createErrorResponseBean(e.getStatus(), e.getMessage());
		response.getWriter().write(beanStringConverter.convertToString(errorBean));
		status = e.getStatus();
		return status;
	}
	
	private void writeConnectionError (HttpServletResponse response) throws IOException {
		ErrorResponseBean errorBean = createErrorResponseBean (HttpStatus.SC_INTERNAL_SERVER_ERROR, 
																	  "Internal Server error: Connetion error");
		response.getWriter().write(beanStringConverter.convertToString(errorBean));
		
	}

	public ErrorResponseBean createErrorResponseBean (int status, String message) {
		ErrorResponseBean errorBean = new ErrorResponseBean();
		errorBean.setErrorCode(""+status);
		errorBean.setErrorDescription(message);
		return errorBean;
	}

	
	public abstract String decodeLoginURL (HttpServletRequest request);
	
	public abstract UserLoginStorageBean getSessionEntity (HttpEntity entity) throws IOException;
}
