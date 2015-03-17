package com.redfire.nutrieduc.userprofile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfire.nutrieduc.entities.mobile.CellPhoneRegister;
import com.redfire.nutrieduc.userprofile.dao.CellPhoneRegisterDAO;
import com.redfire.nutrieduc.userprofile.service.CellPhoneRegisterService;

@Service
public class CellPhoneRegisterServiceImpl implements CellPhoneRegisterService {
	@Autowired
	private CellPhoneRegisterDAO registerDAO;
	
	@Transactional
	public void insert(CellPhoneRegister cellPhoneRegister) {
		registerDAO.insert(cellPhoneRegister);
	}
}
