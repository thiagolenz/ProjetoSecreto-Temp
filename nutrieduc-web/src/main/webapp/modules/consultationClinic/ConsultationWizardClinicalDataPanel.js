define([
        "components/Binding", 
        "components/HtmlLoader",
        "i18n!modules/consultationClinic/nls/ConsultationClinic",
        "components/FieldsValidation",
        "components/ToggleButton",
        "components/Service",
        "components/CheckboxButton"
],
function (binding, htmlLoader, i18ns, fieldsValidation, toggleButton, service, checkboxButton) {
	
	function load (config) {
		_this.config = config;
		htmlLoader.load({
			container: config.container,
			file: "modules/consultationClinic/templates/ConsultationWizardClinicalDataPanel.html",
			i18n: [i18ns]
		}, function () {
			onFinishLoad();
		});
		$(config.titleContainer).html(i18ns.consultation.clinicalDataPanel);
	}
	
	function onFinishLoad () {
		createBloodType ();
		createIntestineFrequencyType ();
		createCheckboxex();
		_this.consultation = _this.config.consultation;
		setup();
		$($("#currentStepContainer input")[0]).focus();
	}
	
	
	function createBloodType () {
		service.get ({
			url : "userProfile/bloodType/retrieveTypes",
			success : function (data) {
				onLoadBloodTypes (data, function() {
					if (_this.clinicalData.bloodType)
						toggleButton.setSelected("clinicalData.bloodType", _this.clinicalData.bloodType);
				});
			} 
		});	
	}
	
	function onLoadBloodTypes (data, onFinishCreate) {
		toggleButton.create ({
			container : "bloodTypeContainer",
			name : "bloodType",
			varname : "description",
			data : data,
			onFinishCreate : onFinishCreate
		});
	}
	
	function createIntestineFrequencyType () {
		service.get ({
			url : "userProfile/intestineFrequencyType/retrieveTypes",
			success : function (data) {
				onLoadIntestineFrequencyTypes (data, function() {
					if (_this.clinicalData.intestineFrequencyType)
						toggleButton.setSelected("clinicalData.intestineFrequencyType", _this.clinicalData.intestineFrequencyType);
				});
			} 
		});	
	}
	
	function onLoadIntestineFrequencyTypes (data, onFinishCreate) {
		toggleButton.create ({
			container : "intestineFrequencyTypeContainer",
			name : "intestineFrequencyType",
			varname : "description",
			data : data,
			onFinishCreate : onFinishCreate
		});
	}
	
	function createCheckboxex () {
		checkboxButton.create ({
			container : "stomachSurgeryContainer"
		});
		
		checkboxButton.create ({
			container : "therapyContainer"
		});

	}
	
	function setup () {
		_this.clinicalData = _this.consultation.clinicalData;
		if (_this.clinicalData == undefined)
			_this.clinicalData = {};			
		binding.copyBeanToForm("clinicalData", _this.clinicalData);
	}
	
	function validate(consultation, onSuccess) {
		var clinicalData = binding.copy("clinicalData", _this.clinicalData);
		clinicalData.intestineFrequencyType = toggleButton.getSelectedValue("intestineFrequencyType");
		clinicalData.bloodType =  toggleButton.getSelectedValue("bloodType");
		fieldsValidation.validateMandatory ({
			bean : clinicalData,
			source : "clinicalData",
			container : "body", 
			success : function() {
				consultation.clinicalData = clinicalData;
				onSuccess();
			}
		});
	}
	
	function unload () {
		_this.consultation = undefined;
		_this.config = undefined;
	}
	
	var _this = {
		load : load,
		validate : validate,
		unload : unload
	};
	
	return _this;
});