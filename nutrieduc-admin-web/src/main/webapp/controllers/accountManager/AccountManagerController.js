define (["modules/accountManager/AccountForm", 
         "modules/accountManager/AccountGrid",
         "components/HtmlLoader",
         "i18n!modules/accountManager/nls/Account",
         "i18n!controllers/accountManager/nls/AccountManager"
         ],
function (accountForm, accountGrid, htmlLoader, i18nsAccount, i18nsAccountManager){
	function load () {
		htmlLoader.load({
			container: "#body",
			file: "controllers/accountManager/templates/AccountManagerLayout.html",
			i18n: [i18nsAccount, i18nsAccountManager]
		}, onFinishLoad);
	}
	
	function onFinishLoad () {
		accountGrid.create("gridContainer");
		accountGrid.loadData();
		bindEvents();
	}
	
	function bindEvents () {
		$("#btnNewAccount").click(openNewAccountModal);
	}
	
	function openNewAccountModal () {
		accountForm.createNew (_this);
	}
	
	function afterCloseAccountModal () {
		accountGrid.loadData();
	}

	var _this = {
		load : load ,
		onCloseModal: afterCloseAccountModal
	};
	
	return _this;
});