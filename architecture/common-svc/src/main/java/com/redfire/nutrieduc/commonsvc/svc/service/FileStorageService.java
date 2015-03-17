package com.redfire.nutrieduc.commonsvc.svc.service;

import java.io.File;

import br.com.redfire.nutrieduc.common.FileStorageCredentials;

public interface FileStorageService {
	/**
	 * Uploads file to storage and return the URL of file
	 * @param file
	 * @param fileName
	 * @return URL string of file
	 */
	String upload (File file, String pathDestiny, FileStorageCredentials credentials);
}
