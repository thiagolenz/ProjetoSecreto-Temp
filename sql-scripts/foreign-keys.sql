ALTER TABLE users ADD FOREIGN KEY (account_id) REFERENCES account(id);

ALTER TABLE accountpatient ADD FOREIGN KEY (account_id) REFERENCES account(id);
ALTER TABLE accountpatient ADD FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE UserSocialMediaLogin ADD FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE ChangePasswordToken ADD FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE appointment ADD FOREIGN KEY (patient_user_id) REFERENCES users(id);
ALTER TABLE appointment ADD FOREIGN KEY (nutritionist_user_id) REFERENCES users(id);
ALTER TABLE CellPhoneRegister ADD FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE UserAvatar ADD FOREIGN KEY (userId) REFERENCES users(id);
ALTER TABLE Consultation ADD FOREIGN KEY (user_patient_id) REFERENCES users(id);
ALTER TABLE Consultation ADD FOREIGN KEY (user_nutritionist_id) REFERENCES users(id);
ALTER TABLE Consultation ADD FOREIGN KEY (clinical_data_id) REFERENCES ConsultationClinicalData(id);
ALTER TABLE Consultation ADD FOREIGN KEY (life_Habit_id) REFERENCES ConsultationLifeHabit(id);
ALTER TABLE Consultation ADD FOREIGN KEY (eating_Habit_id) REFERENCES ConsultationEatingHabit(id);
ALTER TABLE Consultation ADD FOREIGN KEY (body_Measure_id) REFERENCES ConsultationBodyMeasure(id);

ALTER TABLE UserInfoProfile ADD FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE ConsultationUserPicture ADD FOREIGN KEY (userId) REFERENCES users(id);



