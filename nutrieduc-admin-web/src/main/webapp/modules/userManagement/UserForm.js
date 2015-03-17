define (["components/FormDialog", 
         "moment", 
         "components/Binding", 
         "components/Formatter", 
         "i18n!modules/userManagement/nls/User",
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
		this.user = user;
		_this.onCloseModal = _caller.onCloseModal;
		dialog.open({
			title : user.id ? i18ns.user.form.changeUser : i18ns.user.form.newUser,
			content: "modules/userManagement/template/UserForm.html",
			restUrl: "userProfile/user/createUserAccount",
			headers : {
				"rf-account-request": JSON.stringify (_this.account)
			},
			successEditMessage: i18ns.user.form.successChange,
			successNewMessage: i18ns.user.form.successCreate,
			id: user.id,
			i18n: i18ns,
			caller : _this
		});
	}
	
	function loadBeanFromForm () {
		var user = binding.copy ("user");
		user.account = this.account;
		return user;
	}
	
	function afterLoadContent () {
		formatter.initializeDatePicker();
		loadUserTypes();
		binding.copyBeanToForm("user", user);
	}
	
	function loadUserTypes () {
		service.get ({
			url : "userProfile/userType/retrieveTypes",
			success : function (data) {
				onLoadUserTypes (data, function() {
					if (!isNew())
						toggleButton.setSelected ("userType", user.userType);
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

	var _this = {
		createNew : createNew,
		edit : edit,
		prepareObjToSave: loadBeanFromForm,
		afterLoadContent: afterLoadContent
	};
	
	return _this;
});