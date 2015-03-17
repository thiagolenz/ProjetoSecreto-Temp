define (["modules/userManagement/UserForm", 
         "modules/userManagement/UserGrid",
         "components/HtmlLoader",
         "i18n!modules/userManagement/nls/User",
         "i18n!controllers/userAccountAdminManagement/nls/UserManagement",
         "components/ModuleEvent"
         ],
function (userForm, userGrid, htmlLoader, i18nsUser, i18nsUserManagement, moduleEvent){
	
	function load (account) {
		this.account = account;
		htmlLoader.load({
			container: "#body",
			file: "controllers/userAccountAdminManagement/templates/UserManagementLayout.html",
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
		$("#btnNewUser").click(openNewUserModal);
		$("#btnBack").click(backToAccountModule) 
	}
	
	function openNewUserModal () {
		userForm.createNew (_this, _this.account);
	}
	
	function backToAccountModule () {
		moduleEvent.loadModule("accountManager");
	}
	
	function afterCloseUserModal () {
		userGrid.loadData( _this.account);
	}

	var _this = {
		load : load ,
		onCloseModal: afterCloseUserModal
	};
	
	return _this;
});