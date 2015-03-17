package com.redfire.nutrieduc.router;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.redfire.nutrieduc.restclient.ContentReader;
import com.redfire.nutrieduc.restclient.FileUploader;
import com.redfire.nutrieduc.restclient.RestClient;
import com.redfire.nutrieduc.restclient.RestClientException;

/**
 * this class receive a HttpRequest, build the request and route to the correct module 
 * @author thiagolenz
 * @since Aug 25, 2014
 */
public class RequestHandler {

	private URLDecoder urlDecoder = new URLDecoder();
	private RestClient restClient = new RestClient();
	private ContentReader contentReader = new ContentReader();
	private String pathInfo;
	private PrintWriter writer;
	private String method;
	private Map<String, String> headers;
	private HttpServletRequest request;
	private HttpServletResponse response;

	
	public RequestHandler(String pathInfo, PrintWriter writer,
			String method, Map<String, String> headers, HttpServletRequest request, HttpServletResponse response) {
		super();
		this.pathInfo = pathInfo;
		this.writer = writer;
		this.method = method;
		this.headers = headers;
		this.request = request;
		this.response = response;
	}

	public int service () throws IOException {
		String finalUrl = urlDecoder.decodeURL(pathInfo);
		String contentType = request.getContentType();
		
		try {
			CloseableHttpResponse httpResponse = null; 
			if (contentType != null && contentType.startsWith("multipart")){
				File fileMultidata = getFileMultidata();
				httpResponse = restClient.doRequest(fileMultidata, finalUrl, method, headers);
				fileMultidata.delete();
			} else {
				String requestBody = contentReader.getJSONContent(request.getReader());
				httpResponse = restClient.doRequest(requestBody, finalUrl, method, headers);
			}
			writer.write(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
			return httpResponse.getStatusLine().getStatusCode();
		} catch (RestClientException e) {
			return new ResponseErrorWriter().writeErrorResponse(writer, e);
		} catch (Exception e) {
			throw new IOException(e);
		}	
	}
	
	public File getFileMultidata () throws Exception {
		List<String> uploads = new FileUploader().upload(request, response);
		File file = new File(uploads.get(0));
		return file;
	}
	
	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}
	
	public void setUrlDecoder(URLDecoder urlDecoder) {
		this.urlDecoder = urlDecoder;
	}
}
