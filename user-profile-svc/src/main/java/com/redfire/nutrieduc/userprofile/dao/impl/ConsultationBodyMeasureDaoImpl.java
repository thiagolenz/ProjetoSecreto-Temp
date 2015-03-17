package com.redfire.nutrieduc.userprofile.dao.impl;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.entities.consultation.ConsultationBodyMeasure;
import com.redfire.nutrieduc.userprofile.dao.ConsultationBodyMeasureDAO;

@Repository
public class ConsultationBodyMeasureDaoImpl extends
		AbstractJpaDAOImpl<ConsultationBodyMeasure> implements
		ConsultationBodyMeasureDAO {

}
