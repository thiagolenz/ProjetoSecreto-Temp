package com.redfire.nutrieduc.userprofile.dao.impl;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.entities.consultation.ConsultationClinicalData;
import com.redfire.nutrieduc.userprofile.dao.ConsultationClinicalDataDAO;

@Repository
public class ConsultationClinicalDataDaoImpl extends
		AbstractJpaDAOImpl<ConsultationClinicalData> implements
		ConsultationClinicalDataDAO {

}
