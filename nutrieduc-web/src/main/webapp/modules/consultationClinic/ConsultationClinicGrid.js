define (["components/Grid", 
         "components/Service", 
         "components/Messages", 
         "i18n!modules/consultationClinic/nls/ConsultationClinic",
         "i18n!components/nls/Generic",
         "components/Confirmation",
         "modules/consultationClinic/ConsultationClinicWizard",
], 
function (grid, service, messages, i18ns, i18ns_g, confirmation, consultationClinicWizard){
	var gridConfig = {
			idTable : "consultationClinicGrid",
			recordsRange: 10,
			onPageChange : loadData,
			columns : [{label: i18ns.consultation.name, width: 3, customContent : nameRender }, 
			           {label: i18ns.consultation.email, width: 1, customContent: emailRender},
			           {label: i18ns.consultation.history, 
			        	   width: 1, 
			        	   isButton: true, 
			        	   contentLabel: i18ns.consultation.actions.openHistory, 
			        	   className : "btn-primary",
			        	   onClick: openHistory},
			           {label: i18ns.consultation.form.newConsultation, 
				        	   width: 1, 
				        	   isButton: true, 
				        	   contentLabel: i18ns.consultation.form.newConsultation, 
				        	   className : "btn-success",
				        	   onClick: newConsultation}
			        ]
	};
	
	function nameRender (row) {
		return row.patientUser.name;
	}
	
	function emailRender (row) {
		return row.patientUser.email;
	}
	
	function create (placeAt) {
		gridConfig.placeAt = placeAt,
		grid.create (gridConfig);
	}
	
	function loadData () {
		var headers = grid.getSearchHeaders (gridConfig);
		service.post({
			url : "userProfile/accountPatient/retrieveAccountPatients",
			data: {},
			success: onLoadSuccess,
			error: error, 
			headers: headers 
		});
	}
	
	function onLoadSuccess (data) {
		grid.fill(data, gridConfig);
	} 
	
	function error (data) {
		messages.error({
			title: i18ns_g.error,
			message: data
		});
	}
	
	function newConsultation (row) {
		consultationClinicWizard.open({
			consultation: {
				userPatient : row.patientUser
			},
			alreadyPatientBind: true
		});
	}
	
	function openHistory (row) {
//		confirmation.showYesNo({
//			title: i18ns_g.remove,
//			message: i18ns.consultationClinic.grid.questionRemove,
//			onYes : function () {
//				service.remove({
//					url: "userProfile/accountPatient/",
//					id: row.id,
//					success: loadData
//				});
//			}
//		});
		
	}

	var _this = {
		create : create,
		loadData : loadData,
		onCloseModal : loadData
	};
	
	return _this;
});