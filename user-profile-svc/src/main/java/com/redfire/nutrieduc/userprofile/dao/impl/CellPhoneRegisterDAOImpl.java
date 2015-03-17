package com.redfire.nutrieduc.userprofile.dao.impl;

import org.springframework.stereotype.Repository;

import com.redfire.nutrieduc.commonsvc.svc.dao.impl.AbstractJpaDAOImpl;
import com.redfire.nutrieduc.entities.mobile.CellPhoneRegister;
import com.redfire.nutrieduc.userprofile.dao.CellPhoneRegisterDAO;

@Repository
public class CellPhoneRegisterDAOImpl extends
		AbstractJpaDAOImpl<CellPhoneRegister> implements CellPhoneRegisterDAO {

}
