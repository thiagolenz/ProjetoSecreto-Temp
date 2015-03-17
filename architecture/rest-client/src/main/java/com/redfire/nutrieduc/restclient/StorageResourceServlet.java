package com.redfire.nutrieduc.restclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.redfire.nutrieduc.common.FileStorageCredentials;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;

@WebServlet("/storageresource/*")
public class StorageResourceServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7279584673198174467L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FileStorageCredentials credentials = getCredentials();

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(credentials.getAccessKey(), credentials.getSecretKey());
		AmazonS3 s3client = new AmazonS3Client(awsCreds);
		try {
			String fileName = getFileName(request);
			AccessControlList acl = new AccessControlList();
			acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.Read);
			File localFile = new File("/tmp/" + fileName);
			s3client.getObject(new GetObjectRequest("web-test.nutrieduc.com", fileName), localFile);           
			response.setContentType("image/jpeg");
			FileInputStream fin = new FileInputStream(localFile);
			BufferedInputStream bin = new BufferedInputStream(fin);  
			OutputStream out = response.getOutputStream();
			BufferedOutputStream bout = new BufferedOutputStream(out);  
			byte[] buf = new byte[2048];
			int count = 0;
			while ((count = bin.read(buf)) >= 0) {
				out.write(buf, 0, count);
			}

			bin.close();  
			fin.close();  
			bout.close();  
			out.close();  
			localFile.delete();
		} catch (AmazonServiceException ase) {
			logErrorResponse(ase);
		} catch (AmazonClientException ace) {
			logErrorResponse(ace);
		}
	}
	
	public String getFileName (HttpServletRequest request) {
		String file = request.getPathInfo();
		return file.substring(1, file.length());
	}

	private void logErrorResponse(AmazonClientException ace) {
		System.out.println("Caught an AmazonClientException, which " +
				"means the client encountered " +
				"an internal error while trying to " +
				"communicate with S3, " +
				"such as not being able to access the network.");
		System.out.println("Error Message: " + ace.getMessage());
	}

	private void logErrorResponse(AmazonServiceException ase) {
		System.out.println("Caught an AmazonServiceException, which " +
				"means your request made it " +
				"to Amazon S3, but was rejected with an error response" +
				" for some reason.");
		System.out.println("Error Message:    " + ase.getMessage());
		System.out.println("HTTP Status Code: " + ase.getStatusCode());
		System.out.println("AWS Error Code:   " + ase.getErrorCode());
		System.out.println("Error Type:       " + ase.getErrorType());
		System.out.println("Request ID:       " + ase.getRequestId());
	}

	private FileStorageCredentials getCredentials () {
		return new FileStorageCredentials("web-test.nutrieduc.com", "AKIAIOTAHYAZEGY2ZYCQ" , "pq/PEva1zzDKp9AhqOESVNCKArUHzkB1Ykoobecy");
	}
}
