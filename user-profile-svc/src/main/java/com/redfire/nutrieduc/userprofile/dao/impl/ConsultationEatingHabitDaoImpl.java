package com.redfire.nutrieduc.userprofile.dao.impl;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.entities.consultation.ConsultationEatingHabit;
import com.redfire.nutrieduc.userprofile.dao.ConsultationEatingHabitDAO;

@Repository
public class ConsultationEatingHabitDaoImpl extends
		AbstractJpaDAOImpl<ConsultationEatingHabit> implements
		ConsultationEatingHabitDAO {

}
