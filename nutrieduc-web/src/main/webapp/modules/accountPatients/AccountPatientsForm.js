define (["components/HtmlLoader",
         "components/Service",
         "components/Grid",
         "components/Binding",
         "i18n!modules/accountPatients/nls/AccountPatients",
         "i18n!components/nls/Generic",
         "components/FieldsValidation",
         "components/Messages"
], 
function (htmlLoader, service, grid, binding, i18ns, i18ns_g, fieldsValidation, messages) {
	var gridConfig = {
			idTable : "usersGrid",
			placeAt : "gridContainerModal",
			recordsRange: 5,
			onPageChange : function () {
				loadData(_this.filter, false);
			},
			canSelectRow : true,
			onRowSelect : onRowSelect,
			columns : [{label: i18ns.accountPatients.name, width: 250, field: "name" }, 
			           {label: i18ns.accountPatients.login, width: 200, field: "login"}
			]
	};
	
	var config = null;
	
	function openNew (_config) {
		config = _config;
		openDialog();
	}

	function openDialog () {
		var idContainer = "dialogAccountPatientsForm";
		var modalContainer = document.createElement("div");
		modalContainer.id = idContainer;
		$("body").append(modalContainer);
		
		htmlLoader.load({
			container: "#"+idContainer,
			file: "modules/accountPatients/templates/AccountPatientsForm.html",
			i18n : [i18ns]
		}, loadBodyModal);
	}
	
	function loadBodyModal () {
		bindEvents();
		$("#modalAccountPatientForm").modal({
			backdrop: "static"
		});	
	
		focusInput();
		createGrid();
	}
	
	function onRowSelect (row) {
		_this.selectedUser = row;
	}
	
	function bindEvents () {
		$("#btnModalPhaseInputFormOk").on("click", onOkButton);
		
		$("#btnSearch").click(function (){
			createFilterAndLoadData();
		});
		$("#search").on("keypress",function (e){
			 if (e.keyCode == 13) 
				createFilterAndLoadData();
		});
		
		$('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
			 var target = e.target.attributes.href.value;
			 setTimeout(function (){
					focusInput();				 
			 }, 50);

		});
	}
	
	function onOkButton () {
		var tab = $(".tab-pane.active")[0];
		if (tab.id == "tabAddUser")
			processAddUser();
		else 
			finish(_this.selectedUser);
	}
	
	function processAddUser () {
		var user = binding.copy("user");
		fieldsValidation.validateMandatory({
			container : "tabAddUser",
			source : "user",
			inModal : true,
			bean : user,
			success : function () {
				success(user);
			}
		});
	}
	
	function success (user) {
		user.createFromNutritionist = true;
		if (validateExtraFields())
			service.post({
				url : "userProfile/user/createPatientUser",
				data : user,
				success : finish,
				error : error
			});
	}
	
	function validateExtraFields() {
		$("#userLogin,#userLoginConfirm,#userLoginConfirm,#userLogin").parent().removeClass("has-error");
		if ($("#userLogin").val() != $("#userLoginConfirm").val()){
			error ({
				value : i18ns.accountPatients.form.loginsAreDifferent
			});
			$("#userLogin,#userLoginConfirm").parent().addClass("has-error");
			$("#userLoginConfirm").focus();
		} else 
			return true;
	}
	
	function finish (user) {
		service.post({
			url : "userProfile/accountPatient/bindUserPatient",
			data : user,
			success : onSuccessAll,
			error : error
		});
	}
	
	function onSuccessAll () {
		config.onSuccess();
		close();
	}
	
	function createFilterAndLoadData () {
		var filter = binding.copy ("filter");
		loadData(filter, true);
	}
	
	function loadData (data, resetPagination) {
		if (resetPagination)
			grid.resetPagination(gridConfig);
		if (!data)
			data = {};
		_this.filter = data;
		service.post({
			url : "userProfile/user/retrieveUsers",
			data : data ,
			inModal : true,
			success: onLoadSuccess,
			error: error, 
			headers: grid.getSearchHeaders (gridConfig)
		});
	}
	
	function onLoadSuccess (data) {
		grid.fill(data, gridConfig);
	}
	
	function error (data) {
		messages.error({
			title: i18ns_g.error,
			message: data,
			inModal : true
		});
	}
	
	function changeToNewMode () {
		focusInput();
	}
	
	function createGrid () {
		grid.create(gridConfig);
	}

	function focusInput () {
		$($(".tab-pane.active input")[0]).focus();
	}
	
	function close () {
		$("#modalAccountPatientForm").modal("hide");
	}

	var _this =  {
		openNew : openNew
	};
	return _this;
});