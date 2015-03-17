define (function(){
	return {
		accountManager: {
			path: "controllers/accountManager/AccountManagerController",
			loadInURL: true
		}, 
		userAccountAdminManagement: {
			path: "controllers/userAccountAdminManagement/UserManagementController",
			loadInURL: false
		},
		accountPatientManagement: {
			path: "controllers/accountPatient/AccountPatientController",
			loadInURL: false
		}
	};
});