define (["components/Grid", 
         "components/Service", 
         "components/Messages", 
         "i18n!modules/accountPatients/nls/AccountPatients",
         "i18n!components/nls/Generic",
         "components/Confirmation"
], 
function (grid, service, messages, i18ns, i18ns_g, confirmation){
	var gridConfig = {
			idTable : "accountPatientsGrid",
			recordsRange: 10,
			onPageChange : loadData,
			columns : [{label: i18ns.accountPatients.name, width: 250, customContent : nameRender }, 
			           {label: i18ns.accountPatients.login, width: 200, customContent: loginRender},
			           {label: i18ns_g.remove, 
			        	   width: 100, 
			        	   isButton: true, 
			        	   contentLabel: i18ns_g.remove, 
			        	   className : "btn-default",
			        	   onClick: remove}
			        ]
	};
	
	function nameRender (row) {
		return row.patientUser.name;
	}
	
	function loginRender (row) {
		return row.patientUser.login;
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