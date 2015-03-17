define (["components/Grid", 
         "components/Service", 
         "components/Messages", 
         "i18n!modules/accountPatients/nls/AccountPatients",
         "i18n!components/nls/Generic",
         "components/Confirmation",
         "components/ModuleEvent"
], 
function (grid, service, messages, i18ns, i18ns_g, confirmation, moduleEvent){
	var gridConfig = {
			idTable : "accountPatientsGrid",
			recordsRange: 10,
			onPageChange : function () {
				loadData(_this.filter, false);
			},
			columns : [{label: i18ns.accountPatients.name, width: 3, customContent : nameRender }, 
			           {label: i18ns.accountPatients.email, width: 1, customContent: emailRender},
			           {label: "Avaliação", 
			        	   width: 1, 
			        	   isButton: true, 
			        	   contentLabel: "Mostrar Histórico", 
			        	   className : "btn-secondary",
			        	   onClick: showHistory},
			           {label: "Nova Avaliação", 
			        	   width: 1, 
			        	   isButton: true, 
			        	   contentLabel: "Nova Avaliação", 
			        	   className : "btn-success",
			        	   onClick: openNewConsultation},
			           {label: i18ns_g.remove, 
			        	   width: 1, 
			        	   isButton: true, 
			        	   contentLabel: i18ns_g.remove, 
			        	   className : "btn-default",
			        	   onClick: remove}
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
	
	function openNewConsultation (row) {
		moduleEvent.loadModule ("consultationClinic", {
			consultation: {
				userPatient : row.patientUser
			},
			alreadyPatientBind: true
		});
	}
	
	function showHistory (row) {
		moduleEvent.loadModule ("consultationHistory", {userId : row.patientUser.id});
	}
	
	function loadData (filter, resetPatination) {
		if (filter == undefined)
			filter = {};
		_this.filter = filter;
		if (resetPatination)
			grid.resetPagination (gridConfig);
		var headers = grid.getSearchHeaders (gridConfig);
		service.post({
			url : "userProfile/accountPatient/retrieveAccountPatients",
			data: filter,
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
	
	function remove (row) {
		confirmation.showYesNo({
			title: i18ns_g.remove,
			message: i18ns.accountPatients.grid.questionRemove,
			onYes : function () {
				service.remove({
					url: "userProfile/accountPatient/",
					id: row.id,
					success: loadData
				});
			}
		});
		
	}

	var _this = {
		create : create,
		loadData : loadData,
		onCloseModal : loadData
	};
	
	return _this;
});