ALTER TABLE account ADD CONSTRAINT account_pk PRIMARY KEY (id);
ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY (id);
ALTER TABLE accountpatient ADD CONSTRAINT accountpatient_pk PRIMARY KEY (id);
ALTER TABLE UserSocialMediaLogin ADD CONSTRAINT UserSocialMediaLogin_pk PRIMARY KEY (id);
ALTER TABLE admin_user ADD CONSTRAINT admin_user_pk PRIMARY KEY (id);
ALTER TABLE ChangePasswordToken ADD CONSTRAINT ChangePasswordToken_pk PRIMARY KEY (id);
ALTER TABLE appointment ADD CONSTRAINT appointment_pk PRIMARY KEY (id);
ALTER TABLE CellPhoneRegister ADD CONSTRAINT CellPhoneRegister_pk PRIMARY KEY (id);
ALTER TABLE UserAvatar ADD CONSTRAINT userAvatar_pk PRIMARY KEY (id);
ALTER TABLE ConsultationBodyMeasure ADD CONSTRAINT ConsultationBodyMeasure_pk PRIMARY KEY (id);
ALTER TABLE ConsultationClinicalData ADD CONSTRAINT ConsultationClinicalData_pk PRIMARY KEY (id);
ALTER TABLE ConsultationEatingHabit ADD CONSTRAINT ConsultationEatingHabit_pk PRIMARY KEY (id);
ALTER TABLE ConsultationLifeHabit ADD CONSTRAINT ConsultationLifeHabit_pk PRIMARY KEY (id);
ALTER TABLE Consultation ADD CONSTRAINT Consultation_pk PRIMARY KEY (id);
ALTER TABLE UserInfoProfile ADD CONSTRAINT UserInfoProfile_pk PRIMARY KEY (id);

ALTER TABLE ConsultationUserPicture ADD CONSTRAINT ConsultationUserPicture_pk PRIMARY KEY (id);


