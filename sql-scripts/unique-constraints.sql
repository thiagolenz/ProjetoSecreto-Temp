CREATE UNIQUE INDEX UK_USER_EMAIL ON users (email) where removed is false;
CREATE UNIQUE INDEX UK_ACCOUNT_PATIENT_USER ON accountpatient (account_id, user_id);
CREATE UNIQUE INDEX UK_USER_SOCIAL_MEDIA_LOGIN ON UserSocialMediaLogin (user_id, socialMediaProvider, appClientToken);
CREATE UNIQUE INDEX UK_USER_SOCIAL_MEDIA_LOGIN_TOKEN ON UserSocialMediaLogin (socialMediaProvider, accessToken);
CREATE UNIQUE INDEX UK_CHANGE_PASSWORD_TOKEN ON ChangePasswordToken (token);
CREATE UNIQUE INDEX UK_UserAvatar ON UserAvatar (userId);
CREATE UNIQUE INDEX UK_UserInfoProfile ON UserInfoProfile (user_Id);