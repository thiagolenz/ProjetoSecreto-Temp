package com.redfire.nutrieduc.userprofile.dao.impl;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.entities.consultation.ConsultationLifeHabit;
import com.redfire.nutrieduc.userprofile.dao.ConsultationLifeHabitDAO;

@Repository
public class ConsultationLifeHabitDaoImpl extends
		AbstractJpaDAOImpl<ConsultationLifeHabit> implements
		ConsultationLifeHabitDAO {

}
