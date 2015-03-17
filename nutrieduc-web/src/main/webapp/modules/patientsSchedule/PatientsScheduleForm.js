define (["components/FormDialog", 
         "moment", 
         "components/Binding", 
         "components/Formatter", 
         "i18n!modules/patientsSchedule/nls/PatientsSchedule",
         "components/AutoComplete",
         "components/Mask"
], 
function (dialog, moment, binding, formatter, i18ns, autoComplete, mask){
	
	function createNew (config) {
		var patientsSchedule = {};
		_this.config = config;
		openDialog(config.caller, patientsSchedule);
	}
	
	function edit (caller, patientsSchedule) {
		_this.config = caller;
		openDialog(caller, patientsSchedule);
	}
	
	function openDialog (_caller, patientsSchedule) {
		this.caller = _caller;
		_this.patientsSchedule = patientsSchedule;
		_this.onCloseModal = _caller.onCloseModal;
		dialog.open({
			title : patientsSchedule.id ? i18ns.patientsSchedule.form.changeAppointment : i18ns.patientsSchedule.form.newAppointment,
			content: "modules/patientsSchedule/templates/PatientsScheduleForm.html",
			restUrl: "userProfile/appointment/",
			successEditMessage: i18ns.patientsSchedule.form.successChange,
			successNewMessage: i18ns.patientsSchedule.form.successCreate,
			id: patientsSchedule.id,
			source : "patientsSchedule",
			i18n: i18ns,
			large : true,
			caller : _this,
			afterSuccess : function (data) {
				_caller.onSuccess(data);
			}
		});
	}
	
	function loadBeanFromForm () {
		var bean = binding.copy ("patientsSchedule", _this.patientsSchedule);
		var date = $('#patientsScheduleDate').datepicker('getDate');
		bean.startDate = newDateTime("#startTime", date);
		bean.finishDate = newDateTime("#finishTime", date);
		return bean;
	}
	
	function newDateTime (hourInput, date) {
		var hourStart = $(hourInput).val().split(":");
		return new Date (date.getFullYear(), date.getMonth(), date.getDate(), hourStart[0], hourStart[1]);
	}
	
	function afterLoadContent () {
		formatter.initializeDatePicker();
		createAutoCompleteNutritionist();
		createAutoCompletePatient();
		
		mask.maskHour ("[data-type='Hour']");
		binding.copyBeanToForm("patientsSchedule", _this.patientsSchedule);
		if ( _this.patientsSchedule.id == undefined) {
			autoComplete.setValue ("patientsSchedule.nutritionistUser", _this.config.nutritionist);
			autoComplete.setValue ("patientsSchedule.patientUser", _this.config.patient);
			$('#patientsScheduleDate').datepicker('update', _this.config.date);
		} else {
			$('#patientsScheduleDate').datepicker('update', moment(_this.patientsSchedule.startDate).format('L'));
			mask.setHourValue("#startTime", new Date(_this.patientsSchedule.startDate));
			mask.setHourValue("#finishTime", new Date(_this.patientsSchedule.finishDate));
		}
		
		if (_this.config.absence || (_this.patientsSchedule.patientUser == null && _this.patientsSchedule.id != null)) 
			$("#patientContainer").addClass("hidden");
	}
	
	
	function createAutoCompleteNutritionist () {
		autoComplete.create ({
			container : "autoCompleteNutritionist",
			url : "userProfile/nutritionist/retrieveAccountNutritionists",
			varDisplay : "name",
			varSearch : "name",
			canCreate : false
		});
	}
	
	function createAutoCompletePatient () {
		autoComplete.create ({
			container : "autoCompletePatient",
			url : "userProfile/accountPatient/retrieveUsers",
			varDisplay : "name",
			varSearch : "name",
			canCreate : false
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