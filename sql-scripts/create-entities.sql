create table account(
  id bigint NOT NULL,
  companyname varchar (255),
  registerdate timestamp not null,
  status varchar(255),
  accountType varchar(20)
); 

create table users (
	id bigint not null,
	name varchar (150) not null,
	email varchar (100) not null,
	password varchar (500) not null,
	account_id bigint not null,
	lang varchar (5),
	userType varchar (30),
  removed boolean default false
);

create table accountpatient(
  id bigint NOT NULL,
  account_id bigint not null,
  user_id bigint not null
); 

create table UserSocialMediaLogin (
 id bigint not null,
 user_id  bigint not null,
 socialMediaProvider varchar (50) not null,
 appClientToken varchar (150) not null,
 accessToken varchar (150) not null
);

create table admin_user(
    id bigint not null,
    username varchar (30) not null, 
    password varchar (100) not null,
    fullname varchar (150) not null
);

create table ChangePasswordToken(
  id bigint not null,
  user_id  bigint not null,
  token varchar (50) not null,
  newRequest boolean not null
);

create table appointment (
  id bigint not null,
  nutritionist_user_id bigint not null,
  patient_user_id bigint,
  startDate timestamp not null,
  finishDate timestamp not null,
  description varchar (200) not null,
  observation varchar (255)
);

create table CellPhoneRegister (
   id bigint not null,
   user_id bigint not null, 
   regId varchar (500)
);

create table UserAvatar (
   id bigint not null,
   userId bigint not null,
   avatarBase64 text not null
);

create table ConsultationBodyMeasure (
    id bigint not null,
    weight numeric (9,2),
    height numeric (9,2),
      
    bendingTriceps numeric (9,2),
    bendingBack numeric (9,2),
    bendingBiceps numeric (9,2),
    foldBelt numeric (9,2),
    abdominalTuck numeric (9,2),
    bendingThigh numeric (9,2),
    axillaryFold numeric (9,2),
    pectoralFold numeric (9,2),
    leftArmContracted numeric (9,2),
    rightArmContracted numeric (9,2),
    chestCircumference numeric (9,2),
    weistCircumference numeric (9,2),
    abdomenCircumference numeric (9,2),
    rightThighCircumference numeric (9,2),
    leftThighCircumference numeric (9,2),
    rightCalfCircumference numeric (9,2),
    leftCalfCircumference numeric (9,2)
);

create table ConsultationClinicalData(
  id bigint not null,
  diseasesInTreatment varchar (250),
  medicationsInUse varchar (250),
  didStomachSurgery boolean,
  intestineFrequencyType varchar (20),
  didTherapy boolean,
  bloodType varchar (20)
);

create table ConsultationEatingHabit (
  id bigint not null,
  energyFoods varchar(255),
  mostHungryTime varchar(255),
  hungryTimeFood varchar(255),
  notLikeFood varchar(255),
  notRemovableFoods varchar(255),
  mostDificultyDietThing varchar(255)
);

create table ConsultationLifeHabit (
  id bigint not null,
  doFisicalActivities boolean,
  fisicalActivitiesFrequency varchar (40),
  trainingTime varchar (40),
  nightHoursSleeping numeric (9,2),
  smoke boolean,
  drink boolean,
  anxietyType varchar (50),
  litersOfWaterPerDay numeric (9,2)
);

create table Consultation (
  id bigint not null,
  user_patient_id bigint not null,
  user_nutritionist_id bigint,
  consultDate timestamp not null,
  howKnewMyWork varchar (255),
  objective varchar (200) not null,
  clinical_data_id bigint not null,
  life_Habit_id bigint not null,
  eating_Habit_id bigint not null,
  body_Measure_id bigint not null,
  createDate timestamp
);

create table UserInfoProfile (
  id bigint not null,
  birthDate timestamp ,
  age bigint,
  phoneNumber varchar (20),
  user_id bigint not null,
  genderType varchar (20),
  addressStreet varchar (250),
  addressNumber varchar (10),
  addressComplement varchar (50)
); 

create table ConsultationUserPicture (
    id bigint not null,
    userId bigint not null,
    filename varchar (200) not null,
    miniPictureBase64 text
);