package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.entities.consultation.Consultation;
import com.redfire.nutrieduc.entities.user.UserInfoProfile;

public interface ConsultationFatCalculation {
	void calculate (Consultation cons, UserInfoProfile infoProfile);
}
