package com.redfire.nutrieduc.restclient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploader {

	public List<String> upload (HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> fileNames = new ArrayList<>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart) {
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			ServletFileUpload upload = new ServletFileUpload(factory);

			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					String fileName = System.currentTimeMillis()+  item.getName();	 
					File path = new File("/tmp");
					File uploadedFile = new File(path + "/" + fileName);
					item.write(uploadedFile);
					fileNames.add(uploadedFile.getAbsolutePath());
				}
			}
		}
		return fileNames;
	}
}
