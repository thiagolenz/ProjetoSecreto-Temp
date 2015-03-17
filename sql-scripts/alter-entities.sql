ALTER TABLE account ADD accountType varchar(20);

alter table users add removed boolean default false;

alter table UserInfoProfile add documentNumber varchar (20);
alter table UserInfoProfile add documentId bigint ;

alter table ConsultationBodyMeasure add front_picture_id bigint;
alter table ConsultationBodyMeasure add back_picture_id bigint;
alter table ConsultationBodyMeasure add left_picture_id bigint;
alter table ConsultationBodyMeasure add right_picture_id bigint;

ALTER TABLE ConsultationBodyMeasure ADD FOREIGN KEY (front_picture_id) REFERENCES ConsultationUserPicture(id);
ALTER TABLE ConsultationBodyMeasure ADD FOREIGN KEY (back_picture_id) REFERENCES ConsultationUserPicture(id);
ALTER TABLE ConsultationBodyMeasure ADD FOREIGN KEY (left_picture_id) REFERENCES ConsultationUserPicture(id);
ALTER TABLE ConsultationBodyMeasure ADD FOREIGN KEY (right_picture_id) REFERENCES ConsultationUserPicture(id);

alter table accountpatient add avatarBase64 text;

alter table consultation add consultationStatus varchar (20);
update consultation set consultationStatus = 'PENDING';