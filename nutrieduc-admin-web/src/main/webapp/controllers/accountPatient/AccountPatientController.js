define (["modules/accountPatient/UserGrid",
         "components/HtmlLoader",
         "i18n!modules/userManagement/nls/User",
         "i18n!controllers/accountPatient/nls/AccountPatientManagement",
         "components/ModuleEvent"
         ],
function (userGrid, htmlLoader, i18nsUser, i18nsUserManagement, moduleEvent){
	
	function load (account) {
		this.account = account;
		htmlLoader.load({
			container: "#body",
			file: "controllers/accountPatient/templates/AccountPatientLayout.html",
			i18n: [i18nsUser, i18nsUserManagement]
		}, function () {
			onFinishLoad(account);
		});
	}
	
	function onFinishLoad (account) {
		userGrid.create("gridContainer", account);
		userGrid.loadData();
		$("#accountInfo").append(account.companyName)
		bindEvents();
	}
	
	function bindEvents () {
		$("#btnBack").click(backToAccountModule) 
	}
	
	function backToAccountModule () {
		moduleEvent.loadModule("accountManager");
	}

	var _this = {
		load : load 
	};
	
	return _this;
});