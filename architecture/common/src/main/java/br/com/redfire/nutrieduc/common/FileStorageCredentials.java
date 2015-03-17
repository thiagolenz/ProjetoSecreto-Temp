package br.com.redfire.nutrieduc.common;

public class FileStorageCredentials {
	private String bucketName;
	private String accessKey;
	private String secretKey;
	
	public FileStorageCredentials(String bucketName, String accessKey,
			String secretKey) {
		super();
		this.bucketName = bucketName;
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}
	
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
