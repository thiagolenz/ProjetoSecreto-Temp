define([
        "components/Binding", 
        "components/HtmlLoader",
        "i18n!modules/consultationClinic/nls/ConsultationClinic",
        "components/FieldsValidation",
],
function (binding, htmlLoader, i18ns, fieldsValidation) {
	
	function load (config) {
		_this.config = config;
		htmlLoader.load({
			container: config.container,
			file: "modules/consultationClinic/templates/ConsultationWizardEatingHabitPanel.html",
			i18n: [i18ns]
		}, function () {
			onFinishLoad();
		});
		$(config.titleContainer).html(i18ns.consultation.clinicalDataPanel);
	}
	
	function onFinishLoad () {
		_this.consultation = _this.config.consultation;
		setup();
		$($("#currentStepContainer input")[0]).focus();
	}
	
	
	function setup () {
		_this.eatingHabit = _this.consultation.eatingHabit;
		if (_this.eatingHabit == undefined)
			_this.eatingHabit = {};			
		binding.copyBeanToForm("eatingHabit", _this.eatingHabit);
	}
	
	function validate(consultation, onSuccess) {
		var eatingHabit = binding.copy("eatingHabit", _this.eatingHabit);
		fieldsValidation.validateMandatory ({
			bean : eatingHabit,
			source : "eatingHabit",
			container : "body", 
			success : function() {
				consultation.eatingHabit = eatingHabit;
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