define([
        "components/Binding", 
        "components/HtmlLoader",
        "i18n!modules/consultationClinic/nls/ConsultationClinic",
        "components/FieldsValidation",
        "components/ToggleButton",
        "components/Service",
        "components/CheckboxButton",
        "components/DecimalInput"
],
function (binding, htmlLoader, i18ns, fieldsValidation, toggleButton, service, checkboxButton, decimalInput) {
	
	function load (config) {
		_this.config = config;
		htmlLoader.load({
			container: config.container,
			file: "modules/consultationClinic/templates/ConsultationWizardLifeHabitPanel.html",
			i18n: [i18ns]
		}, function () {
			onFinishLoad();
		});
		$(config.titleContainer).html(i18ns.consultation.clinicalDataPanel);
	}
	
	function onFinishLoad () {
		createAnxietyType ();
		createCheckboxex();
		decimalInput.configInputSelection (".decimal");
		_this.consultation = _this.config.consultation;
		setup();
		$($("#currentStepContainer input")[0]).focus();
	}
	
	function createAnxietyType () {
		service.get ({
			url : "userProfile/anxietyType/retrieveTypes",
			success : function (data) {
				onLoadAnxietyTypes (data, function() {
					if (_this.lifeHabit.anxietyType)
						toggleButton.setSelected ("lifeHabit.anxietyType", _this.lifeHabit.anxietyType);
				});
			} 
		});	
	}
	
	function onLoadAnxietyTypes (data, onFinishCreate) {
		toggleButton.create ({
			container : "anxietyTypeContainer",
			name : "anxietyType",
			varname : "description",
			data : data,
			onFinishCreate : onFinishCreate
		});
	}
	
	function createCheckboxex () {
		checkboxButton.create ({
			container : "doFisicalActivitiesContainer"
		});
		
		checkboxButton.create ({
			container : "smokeContainer"
		});
		
		checkboxButton.create ({
			container : "drinkContainer"
		});
	}
	
	
	function setup () {
		_this.lifeHabit = _this.consultation.lifeHabit;
		if (_this.lifeHabit == undefined)
			_this.lifeHabit = {};			
		binding.copyBeanToForm("lifeHabit", _this.lifeHabit);
	}
	
	function validate(consultation, onSuccess) {
		var lifeHabit = binding.copy("lifeHabit", _this.lifeHabit);
		lifeHabit.anxietyType = toggleButton.getSelectedValue ("anxietyType");
			
		fieldsValidation.validateMandatory ({
			bean : lifeHabit,
			source : "lifeHabit",
			container : "body", 
			success : function() {
				consultation.lifeHabit = lifeHabit;
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