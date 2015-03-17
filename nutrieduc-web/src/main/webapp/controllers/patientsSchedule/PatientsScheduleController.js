define (["components/HtmlLoader",
         "i18n!modules/patientsSchedule/nls/PatientsSchedule",
         "i18n!controllers/patientsSchedule/nls/PatientsScheduleManagement",
         "modules/patientsSchedule/PatientsScheduleGrid",
         "modules/patientsSchedule/PatientsScheduleForm",
         "components/AutoComplete",
         "components/Binding"
],
function (htmlLoader, i18nsSchedule, i18nsScheduleManager, patientsScheduleGrid, patientsScheduleForm, autoComplete, binding){
	function load () {
		htmlLoader.load({
			container: "#body",
			file: "controllers/patientsSchedule/templates/PatientsScheduleLayout.html",
			i18n: [i18nsSchedule, i18nsScheduleManager]
		}, onFinishLoad);
	}
	
	function onFinishLoad () {
		
		patientsScheduleGrid.create("gridContainer");
		$("#selectedDate").datepicker({
			todayHighlight: true
		}).on('changeDate', function(e){
	       loadAppointments();
	    });
		todayDate();
		bindEvents();
		createAutoCompleteNutritionist();
		createAutoCompletePatient();
		
		recoveryAndUpdateFilter();
		loadAppointments();
	}
	
	function recoveryAndUpdateFilter () {
		_this.filter = getFilterSaved();
		autoComplete.setValue ("patientsScheduleFilter.nutritionistUser", _this.filter.nutritionistUser);
		autoComplete.setValue ("patientsScheduleFilter.patientUser", _this.filter.patientUser);
	}
	
	function createAutoCompleteNutritionist () {
		autoComplete.create ({
			container : "autoCompleteNutritionistFilter",
			url : "userProfile/nutritionist/retrieveAccountNutritionists",
			varDisplay : "name",
			varSearch : "name",
			canCreate : false,
			onChange : onChangeAutoCompleteNutritionist
		});
	}
	
	function onChangeAutoCompleteNutritionist (row) {
		_this.filter.nutritionistUser = row;
		loadAppointments();
	}
	
	function createAutoCompletePatient () {
		autoComplete.create ({
			container : "autoCompletePatientFilter",
			url : "userProfile/accountPatient/retrieveUsers",
			varDisplay : "name",
			varSearch : "name",
			canCreate : false,
			onChange : onChangeAutoCompletePatient
		});
	}
	
	function onChangeAutoCompletePatient (row) {
		_this.filter.patientUser = row;
		loadAppointments();
	}
	
	function bindEvents () {
		$("#btnNewAppointment").click(function () {
			openNewAppointmentFormModal (false);
		});
		$("#btnNewAbsence").click(function () {
			openNewAppointmentFormModal (true);
		});
		$("#btnToday").click (todayDate);
		$("#btnClean").click (cleanDate);
	}
	
	function todayDate () {
		var dateTemp = new Date();
		$('#selectedDate').datepicker('update', getNewDateFromDate(dateTemp)).on('changeDate', function(e){
	       loadAppointments();
	    });;
	}
	
	function cleanDate () {
		$('#selectedDate').datepicker('update', '').on('changeDate', function(e){
	       loadAppointments();
	    });;
	}
	
	function openNewAppointmentFormModal (absence) {
		patientsScheduleForm.createNew ({
			nutritionist : autoComplete.getValue ("patientsScheduleFilter.nutritionistUser"),
			patient :  autoComplete.getValue ("patientsScheduleFilter.patientUser"),
			date : $('#selectedDate').datepicker('getDate'),
			absence : absence,
			caller : {
				onSuccess : afterCloseAccountModal
			}
		});
	}
	
	function afterCloseAccountModal () {
		loadAppointments();
	}
	
	function loadAppointments () {
		var filter = _this.filter;
		filter.startDate = getNewDateFromDate($('#selectedDate').datepicker('getDate'), 0, 0);
		filter.finishDate = getNewDateFromDate($('#selectedDate').datepicker('getDate'), 23, 59);
		saveFilter(filter);
		if (filter.nutritionistUser && filter.startDate)
			patientsScheduleGrid.loadData(filter);
	}
	
	function saveFilter (filter) {
		localStorage.setItem("patientsSchedule_filter", JSON.stringify(filter));
	}
	
	function getFilterSaved () {
		var result = JSON.parse(localStorage.getItem ("patientsSchedule_filter"));
		if (!result)
			result = {};
		return result;
	}
	
	function getNewDateFromDate(dateTemp, hour, minutes) {
		if (!hour)
			hour = 0;
		if (!minutes)
			minutes = 0;
		return new Date(dateTemp.getFullYear(), dateTemp.getMonth(), dateTemp.getDate(), hour, minutes);
	}

	var _this = {
		load : load ,
		onCloseModal: afterCloseAccountModal
	};
	
	return _this;
});