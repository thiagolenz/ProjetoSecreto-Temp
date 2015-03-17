package com.redfire.nutrieduc.userprofile.dao.impl;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.entities.consultation.ConsultationUserPicture;
import com.redfire.nutrieduc.userprofile.dao.ConsultationUserPictureDAO;

@Repository
public class ConsultationUserPictureDaoImpl extends
		AbstractJpaDAOImpl<ConsultationUserPicture> implements
		ConsultationUserPictureDAO {

}
