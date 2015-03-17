define (["components/HtmlLoader",
         "i18n!modules/accountUsers/nls/AccountUser",
         "i18n!controllers/accountUsers/nls/AccountUsersManagement",
         "modules/accountUsers/AccountUserGrid",
         "modules/accountUsers/AccountUserForm"
],
function (htmlLoader, i18nsAccount, i18nsAccountManager, accountUsersGrid, accountUsersForm){
	function load () {
		htmlLoader.load({
			container: "#body",
			file: "controllers/accountUsers/templates/AccountUsersLayout.html",
			i18n: [i18nsAccount, i18nsAccountManager]
		}, onFinishLoad);
	}
	
	function onFinishLoad () {
		accountUsersGrid.create("gridContainer");
		accountUsersGrid.loadData();
		bindEvents();
	}
	
	function bindEvents () {
		$("#btnNewAccountUser").click(openNewaccountUsersFormModal);
	}
	
	function openNewaccountUsersFormModal () {
		accountUsersForm.createNew ({
			onSuccess : afterCloseAccountModal
		});
	}
	
	function afterCloseAccountModal () {
		accountUsersGrid.loadData();
	}

	var _this = {
		load : load ,
		onCloseModal: afterCloseAccountModal
	};
	
	return _this;
});