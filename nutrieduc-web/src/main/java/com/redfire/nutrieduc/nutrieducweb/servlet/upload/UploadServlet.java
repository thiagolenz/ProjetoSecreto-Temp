package com.redfire.nutrieduc.nutrieducweb.servlet.upload;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import br.com.redfire.nutrieduc.common.Constants;

import com.redfire.nutrieduc.nutrieducweb.servlet.DefaultRestServlet;
import com.redfire.nutrieduc.restclient.FileUploader;
import com.redfire.nutrieduc.restclient.RestClientException;
/**
 * Servlet implementation class UploadFile
 */

@WebServlet("/upload/*")
public class UploadServlet extends DefaultRestServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4756894420728309133L;

	@Override
	protected void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(Constants.APPLICATION_JSON);
		int httpStatus = 0;
		try {
			List<String> files = new FileUploader().upload(request, response);
			File file = new File(files.get(0));
			httpStatus = upload(file, request, response);
		}  catch (Exception e) {
			httpStatus = HttpStatus.SC_INTERNAL_SERVER_ERROR;
			response.getWriter().write(e.getMessage());
		}		
		
		response.setStatus(httpStatus);
	}
	
	public int upload (File file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		int httpStatus = 0;
		try {
			String urlAppMangm = getURLDecoded(request);
			Map<String, String> readHeaders = headerReader.readHeaders(request);
			addAditionalHeaders(readHeaders, request);
			CloseableHttpResponse httpResponse = restClient.doRequest(file, 
					urlAppMangm, 
					"POST", 
					readHeaders);

			httpStatus = httpResponse.getStatusLine().getStatusCode();
			response.getWriter().write(EntityUtils.toString(httpResponse.getEntity()));
			file.delete();
		} catch (RestClientException e) {
			httpStatus = e.getStatusCode();
			response.getWriter().write(e.getMessage());
		} catch (Exception e) {
			httpStatus = HttpStatus.SC_INTERNAL_SERVER_ERROR;
			response.getWriter().write(e.getMessage());
		}	
		return httpStatus;
	}
}
