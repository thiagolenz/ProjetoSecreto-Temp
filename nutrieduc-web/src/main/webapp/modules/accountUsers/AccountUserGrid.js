define (["components/Grid", 
         "components/Service", 
         "moment", 
         "modules/accountUsers/AccountUserForm", 
         "components/Messages", 
         "components/Confirmation",
         "i18n!modules/accountUsers/nls/AccountUser",
         "i18n!components/nls/Generic"
], 
function (grid, service, moment, userForm, messages, confirmation, i18ns, i18ns_g){
	var gridConfig = {
			idTable : "userTable",
			columns : [{label: i18ns.accountUser.name, width: 250, field : "name"}, 
			           {label: i18ns.accountUser.login, width: 200, field: "login"},
			           {label: i18ns_g.change, 
			        	   width: 100, 
			        	   isButton: true, 
			        	   contentLabel: i18ns_g.change,
			        	   className : "btn-primary",
			        	   onClick: edit},
			           {label: i18ns_g.remove, 
			        	   width: 100, 
			        	   isButton: true, 
			        	   contentLabel: i18ns_g.remove, 
			        	   className : "btn-default",
			        	   onClick: remove}
			        ]
	};
	function create (placeAt, account) {
		gridConfig.placeAt = placeAt,
		this.account  = account;
		grid.create (gridConfig);
	}
	
	function remove (row) {
		confirmation.showYesNo({
			title: i18ns_g.remove,
			message: i18ns.accountUser.grid.questionRemove,
			onYes : function () {
				service.remove({
					url: "userProfile/user",
					id: row.id,
					success: loadData
				});
			}
		});
		
	}
	
	function edit (row) {
		userForm.edit(_this, row, _this.account);
	}
	
	function registerDateFormater (row) {
		return moment(row.registerDate).format('L');
	}
	
	function loadData () {
		var headers = grid.getSearchHeaders (gridConfig);
		service.post({
			url : "userProfile/user/retrieveUsersByAccount",
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