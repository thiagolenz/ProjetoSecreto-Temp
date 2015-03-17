package com.redfire.nutrieduc.userprofile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.entities.consultation.ConsultationUserPicture;
import com.redfire.nutrieduc.userprofile.service.ConsultationUserPictureService;

@Controller
@RequestMapping(value="/consultationUserPicture", produces=MediaType.APPLICATION_JSON_VALUE)
public class ConsultationUserPictureController {
	@Autowired
	private ConsultationUserPictureService userPictureService;
	
	@RequestMapping(value="/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public ConsultationUserPicture uploadImageToUser (@PathVariable ("userId") Long userId, @RequestParam("file") MultipartFile fileMultiPart) throws BusinessException {
		return userPictureService.saveNewPicture(userId, fileMultiPart);
	}
}
