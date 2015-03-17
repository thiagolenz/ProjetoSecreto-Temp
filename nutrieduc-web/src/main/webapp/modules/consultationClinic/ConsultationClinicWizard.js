define (["components/HtmlLoader",
         "modules/consultationClinic/ConsultationWizardUserProfilePanel",
         "modules/consultationClinic/ConsultationWizardClinicalDataPanel",
         "modules/consultationClinic/ConsultationWizardLifeHabitPanel",
         "modules/consultationClinic/ConsultationWizardEatingHabitPanel",
         "modules/consultationClinic/ConsultationWizardBodyMeasurePanel",
         "i18n!modules/consultationClinic/nls/ConsultationClinic",
         "components/ModuleEvent",
         "components/Service",
         "components/CheckboxButton"
], 
function (htmlLoader, userProfilePanel, clinicalDataPanel, lifeHabit, eatingHabit, bodyMeasure,
		i18nCons, moduleEvent, service, checkboxButton) {
	function open (configCaller) {	
		_this.configCaller = configCaller;
		htmlLoader.load({
			container: "#body",
			file: "modules/consultationClinic/templates/ConsultationClinicWizard.html",
			i18n : [i18nCons]
		}, afterLoad);
	}
	
	function afterLoad () {
		$("#pageTittle").html(_this.configCaller.title);
		bindEvents();
		var consultation = prepareConsultation();
		_this.configPanel = {
				container : "#currentStepContainer",
				titleContainer : "#currentStepTitle",
				consultation : consultation,
				saveConsultation : saveConsultation,
				alreadyPatientBind : _this.configCaller.alreadyPatientBind
		};
		configurePanelsSequence();
		if (_this.currentIndex == 0)
			userProfilePanel.load(_this.configPanel, true);
		else {
			_this.currentIndex --;
			nextStep();
		}
	}
	
	function prepareConsultation () {
		var consultation = undefined;
		if (_this.configCaller.consultation) {
			 consultation = _this.configCaller.consultation;
			 _this.currentIndex = 0;
			 saveCurrentStep();
		} else {
			consultation = getConsultation();
			if (consultation.id != undefined) {
				consultation = {};
				_this.currentIndex = 0;
				saveCurrentStep();
			}
		}
		return consultation;
	}
	
	function configurePanelsSequence () {
		_this.currentStepPanel = userProfilePanel;	
		_this.panels = new Array();
		_this.panels.push (userProfilePanel);
		_this.panels.push (clinicalDataPanel);
		_this.panels.push (lifeHabit);
		_this.panels.push (eatingHabit);
		_this.panels.push (bodyMeasure);
		_this.currentIndex = loadCurrentStep();
	}
	
	function bindEvents () {
		$("#btnNext").click(function () {
			_this.currentStepPanel.validate(_this.configPanel.consultation, function () {
				if (_this.currentStepPanel.unload)
					_this.currentStepPanel.unload();
				if (_this.currentIndex +1 === _this.panels.length)
					save();
				else 
					nextStep();
			});
		});
		
		$("#btnPrevious").click(function () {
			previousStep();
		});
		
		$("#btnCancel").click(function () {
			cancel();
		});
	}
	
	function save () {
		var consultation = _this.configPanel.consultation;
		delete consultation.accountPatient;
		if (consultation.id == undefined)
			service.post ( {
				url : "userProfile/consultation/",
				data : consultation,
				success : onSaveSuccess
			});
		else
			service.put ({
				url : "userProfile/consultation/",
				data : consultation,
				id : consultation.id,
				success : onSaveSuccess,
			});
	}
	
	function onSaveSuccess (data) {
		saveConsultation({});
		_this.currentIndex = 0;
		saveCurrentStep();
		moduleEvent.loadModule("consultationClinic", {
			success : true,
			consultation: data
		});
	}
	
	function cancel () {
		localStorage.setItem("consultationWizard_bean", "");
		localStorage.setItem("consultationWizard_actualStep", "");
		moduleEvent.loadModule("consultationClinic");
	}
	
	function nextStep () {
		_this.currentIndex ++;
		_this.panels[_this.currentIndex].load(_this.configPanel);
		_this.currentStepPanel = _this.panels[_this.currentIndex];
		removePastStepFocus();
		setCurrentStepPanelFocus();
		updateProgressBar();
		saveConsultation(_this.configPanel.consultation);
	}
	
	function previousStep () {
		_this.currentIndex --;
		_this.panels[_this.currentIndex].load(_this.configPanel);
		_this.currentStepPanel = _this.panels[_this.currentIndex];
		removePastStepFocus();
		setCurrentStepPanelFocus();
		updateProgressBar();
	}
	
	function setCurrentStepPanelFocus() {
		var li = $("#tabContainer li")[_this.currentIndex];
		$(li).removeClass("disabled");
		changeNextButton();
		saveCurrentStep();
	}
	
	function changeNextButton () {
		if (_this.currentIndex +1 === _this.panels.length) {
			$("#btnNext").html("Finalizar");
			$("#containerYesSendEmail").removeClass("hide");
		} else {
			$("#containerYesSendEmail").addClass("hide");
			$("#btnNext").html("Próximo");
		}
		if (_this.currentIndex != 0) {
			$("#btnPrevious").removeClass("hide");
		} else 
			$("#btnPrevious").addClass("hide");
	}
	
	function saveCurrentStep () {
		localStorage.setItem("consultationWizard_actualStep", _this.currentIndex);
	}
	
	function loadCurrentStep () {
		var result = localStorage.getItem ("consultationWizard_actualStep");
		if (!result)
			result = 0;
		return result;
	}
	
	function removePastStepFocus() {
		$("#tabContainer li").addClass("disabled");
	}
	
	function saveConsultation(consultation) {
//		localStorage.setItem("consultationWizard_bean", JSON.stringify(consultation));
	}
	
	function getConsultation () {
		var result = localStorage.getItem ("consultationWizard_bean");
		if (!result)
			result = {};
		else 
			result = JSON.parse(result);
		return result;
	}
	
	function wasOpen () {
		var bean = localStorage.getItem ("consultationWizard_bean");
		return bean != undefined && bean != "";
	}
	
	function updateProgressBar() {
		var value = _this.currentIndex * 100 / _this.panels.length ;
		value = value + 10;
		$(".progress-bar").css("width", value + "%");
	}
	
	var _this = {
		open : open,
		wasOpen : wasOpen
	};
	
	return _this;
});