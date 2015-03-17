define (function(){
	return {
		dashboard: {
			path: "controllers/dashboard/DashboardController",
			loadInURL: true
		},
		
		home : {
			path: "controllers/homeController/HomeController",
			loadInURL: true
		},
		
		patientMyProfile: {
			path: "controllers/patientMyProfile/PatientMyProfileController",
			loadInURL: true
		},
		
		accountPatients: {
			path: "controllers/accountPatients/AccountPatientsController",
			loadInURL: true
		},
		
		patientsSchedule: {
			path: "controllers/patientsSchedule/PatientsScheduleController",
			loadInURL: true
		},
		
		accountUsers: {
			path: "controllers/accountUsers/AccountUsersController",
			loadInURL: true
		},
		
		chat: {
			path: "controllers/chat/ChatController",
			loadInURL: false
		},
		
		consultationClinic : {
			path: "controllers/consultationClinic/ConsultationClinicController",
			loadInURL: true
		},
		
		consultationHistory : {
			path: "controllers/consultationHistory/ConsultationHistoryController",
			loadInURL: true
		},
		
		consultationView : {
			path: "controllers/consultationView/ConsultationViewController",
			loadInURL: false
		},
		
		consultationDayPool : {
			path: "controllers/consultationDayPool/ConsultationDayPoolController",
			loadInURL: true
		}
	};
});

