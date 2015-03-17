define (["components/Grid", 
         "components/Service", 
         "moment", 
         "modules/accountManager/AccountForm", 
         "components/Messages", 
         "components/Confirmation",
         "i18n!modules/accountManager/nls/Account",
         "i18n!components/nls/Generic",
         "components/ModuleEvent",
         "modules/accountManager/AccountManagementPanel"
], 
function (grid, service, moment, accountForm, messages, confirmation, i18ns, i18ns_g, moduleEvent, managementPanel){
	var gridConfig = {
			idTable : "accountTable",
			recordsRange: 10,
			onPageChange : loadData,
			columns : [{label: i18ns.account.companyName, width: 250, field : "companyName"}, 
			           {label: i18ns.account.registerDate, width: 200, field: "registerDate", customContent: registerDateFormater},
			           
			           {label: i18ns.account.grid.manage,
			        	 width: 100,
			        	 isButton : true,
			        	 contentLabel: i18ns.account.grid.manage,
			        	 className : "btn-primary",
			        	 onClick: manage
			           },
			    
			           {label: i18ns_g.remove, 
			        	   width: 100, 
			        	   isButton: true, 
			        	   contentLabel: i18ns_g.remove, 
			        	   className : "btn-default",
			        	   onClick: remove}
			        ]
	};

	function create (placeAt) {
		gridConfig.placeAt = placeAt;
		grid.create (gridConfig);
	}
	
	function remove (row) {
		confirmation.showYesNo({
			title: i18ns_g.remove,
			message: i18ns.account.grid.questionRemove,
			onYes : function () {
				service.remove({
					url: "u/account",
					id: row.id,
					success: loadData
				});
			}
		});
		
	}
	
	function manage (row) {
		managementPanel.create(row);
	}
	
	function openUsers (row) {
		moduleEvent.loadModule("userAccountAdminManagement", row);
	}
	
	function edit (row) {
		accountForm.edit(_this, row);
	}
	
	function registerDateFormater (row) {
		return moment(row.registerDate).format('L');
	}
	
	function loadData () {
		service.post({
			url : "userProfile/account/retrieveNutritionistAccounts",
			data : {},
			success: onLoadSuccess,
			error: error,
			headers: grid.getSearchHeaders(gridConfig)
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

	var _this = {
		create : create,
		loadData : loadData,
		onCloseModal : loadData
	};
	
	return _this;
});