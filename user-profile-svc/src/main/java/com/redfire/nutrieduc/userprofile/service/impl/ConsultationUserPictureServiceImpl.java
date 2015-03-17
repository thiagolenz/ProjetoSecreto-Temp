package com.redfire.nutrieduc.userprofile.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.redfire.nutrieduc.common.FileStorageCredentials;

import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.commonsvc.svc.service.FileStorageService;
import com.redfire.nutrieduc.entities.consultation.ConsultationUserPicture;
import com.redfire.nutrieduc.userprofile.dao.ConsultationUserPictureDAO;
import com.redfire.nutrieduc.userprofile.service.ConsultationUserPictureService;

@Service
public class ConsultationUserPictureServiceImpl implements ConsultationUserPictureService {
	@Autowired
	private ConsultationUserPictureDAO userPictureDAO;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Value("${bucket.storage.access.key}" )
	private String accessKey;
	
	@Value("${bucket.storage.secret.key}" )
	private String secretKey;
	
	@Value("${storage.bucketname}" )
	private String bucketName;

	@Transactional
	public ConsultationUserPicture saveNewPicture (Long userId, MultipartFile multipartFile) throws BusinessException {
		String fileName = userId + "-" + generateUniqueFileName();
		ConsultationUserPicture consultationUserPicture = new ConsultationUserPicture();
		consultationUserPicture.setFileName(fileName);
		consultationUserPicture.setUserId(userId);
		File file = saveIntoFile(multipartFile, fileName);
		fileStorageService.upload(file, "consultation", getCredentials());
		file.delete();
		userPictureDAO.insert(consultationUserPicture);
		return consultationUserPicture;
	}
	
	private File saveIntoFile (MultipartFile multipartFile, String fileName) throws BusinessException {
		fileName = "/tmp/" + fileName;
		try {
			try (OutputStream stream = new FileOutputStream(fileName)) {
			    stream.write(multipartFile.getBytes());
			}
			return new File(fileName);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Erro ");
		}
	}
	
	private FileStorageCredentials getCredentials () {
		return new FileStorageCredentials(bucketName, accessKey , secretKey);
	}
	
	
	// access key AKIAIOTAHYAZEGY2ZYCQ
	// secret key 
	
	public String generateUniqueFileName () {
		String base = UUID.randomUUID().toString().substring(0, 12);
		StringBuilder sb = new StringBuilder();
		byte [] bytes = base.getBytes();
		for  (int i=0; i< bytes.length ;i++){
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

}
