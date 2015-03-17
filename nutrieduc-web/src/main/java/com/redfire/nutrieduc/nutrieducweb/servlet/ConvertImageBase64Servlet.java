package com.redfire.nutrieduc.nutrieducweb.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.redfire.nutrieduc.restclient.FileUploader;

@WebServlet("/convertBase64/")
public class ConvertImageBase64Servlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6189077507861906881L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<String> files = new FileUploader().upload(request, response);

			for (String file : files) {
				String encode = encodeFileToBase64Binary(file);
				response.getWriter().write(encode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private String encodeFileToBase64Binary(String fileName)
			throws IOException {
 
		File file = new File(fileName);
		byte[] bytes = loadFile(file);
		byte[] encoded = Base64.encodeBase64(bytes);
		String encodedString = new String(encoded);
		file.delete();
		return encodedString;
	}
 
	private byte[] loadFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);
 
	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }
	    
	    is.close();
 
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }
 
	    return bytes;
	}
}
