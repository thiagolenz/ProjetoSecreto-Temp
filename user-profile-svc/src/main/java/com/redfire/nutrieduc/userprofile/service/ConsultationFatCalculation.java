package com.redfire.nutrieduc.userprofile.service;

import com.redfire.nutrieduc.entities.consultation.Consultation;
import com.redfire.nutrieduc.entities.user.UserInfoProfile;

public interface ConsultationFatCalculation {
	double calculate (Consultation cons, UserInfoProfile infoProfile);
}
