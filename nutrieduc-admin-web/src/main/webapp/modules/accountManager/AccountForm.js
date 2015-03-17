define (["components/FormDialog", 
         "moment", 
         "components/Binding", 
         "components/Formatter", 
         "i18n!modules/accountManager/nls/Account"], 
function (dialog, moment, binding, formatter, i18ns){
	
	function createNew (caller) {
		var account = {};
		openDialog(caller, account);
	}
	
	function edit (caller, account) {
		openDialog(caller, account);
	}
	
	function openDialog (_caller, account) {
		this.caller = _caller;
		this.account = account;
		_this.onCloseModal = _caller.onCloseModal;
		dialog.open({
			title : account.id ? i18ns.account.form.changeAccount : i18ns.account.form.newAccount,
			content: "modules/accountManager/template/AccountForm.html",
			restUrl: "userProfile/user/createNutritionistUser",
			successEditMessage: i18ns.account.form.successChange,
			successNewMessage: i18ns.account.form.successCreate,
			id: account.id,
			i18n: i18ns,
			caller : _this
		});
	}
	
	function loadBeanFromForm () {
		return binding.copy ("user");
	}
	
	function afterLoadContent () {
		formatter.initializeDatePicker();
		binding.copyBeanToForm("account", account);
	}

	var _this = {
		createNew : createNew,
		edit : edit,
		prepareObjToSave: loadBeanFromForm,
		afterLoadContent: afterLoadContent
	};
	
	return _this;
});