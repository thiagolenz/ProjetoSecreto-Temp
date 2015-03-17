define (["components/Grid", 
         "components/Service", 
         "components/Messages", 
         "i18n!modules/userManagement/nls/User",
         "i18n!components/nls/Generic"
], 
function (grid, service, messages, i18ns, i18ns_g){
	var gridConfig = {
			idTable : "userTable",
			columns : [{label: i18ns.user.name, width: 250, field : "name"}, 
			           {label: i18ns.user.login, width: 200, field: "login"},
			        ]
	};
	
	function create (placeAt, account) {
		gridConfig.placeAt = placeAt,
		this.account  = account;
		grid.create (gridConfig);
	}
	
	function loadData () {
		var headers = grid.getSearchHeaders (gridConfig);
		headers["rf-account-request"] = JSON.stringify(_this.account);
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

	var _this = {
		create : create,
		loadData : loadData,
		onCloseModal : loadData
	};
	
	return _this;
});