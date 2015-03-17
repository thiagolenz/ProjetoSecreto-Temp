package com.redfire.nutrieduc.nutrieducweb.servlet.upload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpStatus;

import br.com.redfire.nutrieduc.common.Constants;

@WebServlet("/convertFileUpload/*")
public class ConvertFileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3187359326113032506L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		byte[] data = Base64.decodeBase64(getContent(request.getReader()));
		String fileName = "/tmp/"+System.currentTimeMillis() + request.getSession().getId();
		
		try (OutputStream stream = new FileOutputStream(fileName)) {
		    stream.write(data);
		}
		
		int httpStatus = 0;
		try {
			File file = new File(fileName);
			UploadServlet uploadServlet = new UploadServlet();
			httpStatus = uploadServlet.upload(file, request, response);
		}  catch (Exception e) {
			httpStatus = HttpStatus.SC_INTERNAL_SERVER_ERROR;
			response.getWriter().write(e.getMessage());
		}		
		
		response.setContentType(Constants.APPLICATION_JSON);
		response.setStatus(httpStatus);
	}
	
	private String getContent (BufferedReader reader ) throws IOException{
		StringBuffer buffer = new StringBuffer();
		String line = null;
		do {
			line = reader.readLine();
			if (line != null)	
				buffer.append(line.trim());
		} while (line != null);
		
		return buffer.toString();
	}
}
