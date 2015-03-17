package com.redfire.nutrieduc.userprofile.service;

import org.springframework.web.multipart.MultipartFile;

import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.entities.consultation.ConsultationUserPicture;

public interface ConsultationUserPictureService {

	ConsultationUserPicture saveNewPicture (Long userId, MultipartFile file) throws BusinessException;
}
