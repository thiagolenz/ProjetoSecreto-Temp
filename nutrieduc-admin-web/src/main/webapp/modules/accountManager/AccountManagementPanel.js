define (["components/HtmlLoader",
         "components/ModuleEvent"], 
function (htmlLoader, moduleEvent){
	
	function create (account) {
		_this.account = account;
		htmlLoader.load({
			container: "#body",
			file: "modules/accountManager/template/AccountManagementPanel.html",
			i18n: [{},{}]
		}, onFinishLoad);
	}
	
	function onFinishLoad () {
		$("#userButton").click (openUsers);
		$("#patientsButton").click (openPatients);
		$(".panel-body").append(_this.account.companyName);
	}
	
	function openUsers () {
		moduleEvent.loadModule("userAccountAdminManagement", _this.account);
	}
	
	function openPatients () {
		moduleEvent.loadModule("accountPatientManagement", _this.account);
	}
	
	var _this = {
		create : create
	};
	
	return _this;
});