define (["components/FormDialog", 
         "moment", 
         "components/Binding", 
         "components/Formatter", 
         "i18n!modules/accountUsers/nls/AccountUser",
         "components/ToggleButton",
         "components/Service"], 
function (dialog, moment, binding, formatter, i18ns, toggleButton, service){
	
	function createNew (caller, account) {
		var user = {};
		_this.account = account;
		openDialog(caller, user);
	}
	
	function edit (caller, user, account) {
		_this.account = account;
		openDialog(caller, user);
	}
	
	function openDialog (_caller, user) {
		this.caller = _caller;
		_this.user = user;
		_this.onCloseModal = _caller.onCloseModal;
		dialog.open({
			title : isNew() ? i18ns.accountUser.form.newUser : i18ns.accountUser.form.changeUser,
			content: "modules/accountUsers/templates/AccountUserForm.html",
			restUrl: isNew () ? "userProfile/user/createUserAccount" : "userProfile/user/", 
			successEditMessage: i18ns.accountUser.form.successChange,
			successNewMessage: i18ns.accountUser.form.successCreate,
			id: user.id,
			i18n: i18ns,
			caller : _this
		});
	}
	
	function loadBeanFromForm () {
		var user = binding.copy ("accountUser", _this.user);
		user.account = _this.account;
		return user;
	}
	
	function afterLoadContent () {
		formatter.initializeDatePicker();
		loadUserTypes();
		binding.copyBeanToForm("accountUser", _this.user);
		if (!isNew())
			$("#passwordInputLine").remove();
	}
	
	function loadUserTypes () {
		service.get ({
			url : "userProfile/userType/retrieveTypes",
			success : function (data) {
				var filtered = new Array ();
				for (var i = 0; i < data.length; i++) {
					if (data [i].enumValue != "PATIENT")
						filtered.push (data[i]);
				}
				onLoadUserTypes (filtered, function() {
					if (!isNew())
						toggleButton.setSelected ("userType", _this.user.userType);
				});
			} 
		});
	}
	function onLoadUserTypes (data, onFinishCreate) {
		toggleButton.create ({
			container : "userTypeContainer",
			name : "userType",
			varname : "description",
			data : data,
			onFinishCreate : onFinishCreate
		});
	}
	
	function isNew () {
		return _this.user.id == undefined;
	}

	var _this = {
		createNew : createNew,
		edit : edit,
		prepareObjToSave: loadBeanFromForm,
		afterLoadContent: afterLoadContent
	};
	
	return _this;
});