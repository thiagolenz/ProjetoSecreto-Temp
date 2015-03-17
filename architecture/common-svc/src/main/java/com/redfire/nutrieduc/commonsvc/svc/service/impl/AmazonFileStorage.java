package com.redfire.nutrieduc.commonsvc.svc.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;

import br.com.redfire.nutrieduc.common.FileStorageCredentials;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.redfire.nutrieduc.commonsvc.svc.service.FileStorageService;

@Service
public class AmazonFileStorage implements FileStorageService {

	private static final String S3_SERVER = "https://s3-sa-east-1.amazonaws.com/";
	
	@Override
	public String upload (File file, String dirUpload, FileStorageCredentials credentials) {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(credentials.getAccessKey(), credentials.getSecretKey());
        AmazonS3 s3client = new AmazonS3Client(awsCreds);
        String destinyFileName = dirUpload + "/" + file.getName();
        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            
            AccessControlList acl = new AccessControlList();
            acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.Read);
            s3client.putObject(new PutObjectRequest(
            		                 credentials.getBucketName(), destinyFileName, file).withAccessControlList(acl));
            return S3_SERVER + credentials.getBucketName() + "/"+ destinyFileName;
         } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            return null;
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            return null;
        }
	}

}
