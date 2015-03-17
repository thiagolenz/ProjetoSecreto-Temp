package com.redfire.nutrieduc.router;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.redfire.nutrieduc.common.Constants;

import com.redfire.nutrieduc.restclient.HeaderReader;

/**
 * Handler Servlet. All the request will pass on this Servlet. 
 * @author thiagolenz
 * @since Aug 25, 2014
 */
@WebServlet("/handler/*")
public class RequestHandlerServlet extends HttpServlet {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6521308313565866334L;
	private HeaderReader headerReader = new HeaderReader();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(Constants.APPLICATION_JSON);
		
		RequestHandler handler = new RequestHandler(request.getPathInfo(), 
													response.getWriter(),  
													request.getMethod(),
													headerReader.readHeaders(request), 
													request,
													response);
		int status = handler.service();
		response.setStatus(status);
	}
}
