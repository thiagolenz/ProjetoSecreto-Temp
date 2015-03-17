package com.redfire.nutrieduc.router;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.redfire.nutrieduc.restclient.HeaderReader;
import com.redfire.nutrieduc.restclient.RestClient;
import com.redfire.nutrieduc.restclient.RestClientException;

@WebServlet("/serviceList/")
public class SevicesMapServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8844917720409207409L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (URLDecoder.getAppEnv().equals("D")) {
			List<String> modules = new ArrayList<>();
			URLDecoder decoder = new URLDecoder();
			RestClient rest = new RestClient();
			HeaderReader headerReader = new HeaderReader();

			for (Object moduleKey : URLDecoder.proxyProperties.keySet()) {
				modules.add(URLDecoder.proxyProperties.getProperty((String)moduleKey));
				String url = decoder.decodeURL(req.getRequestURI()+ "/"+ moduleKey + "/module/operations");
				try {
					CloseableHttpResponse doRequest = rest.doRequest("", url, "GET", headerReader.readHeaders(req));
					System.out.println(doRequest);
				} catch (RestClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
