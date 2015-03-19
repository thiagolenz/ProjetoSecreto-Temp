define (["components/HtmlLoader",
         "components/Service",
         "i18n!modules/consultationClinic/nls/ConsultationClinic",
         "i18n!components/nls/Generic",
         "modules/consultationView/ConsultationViewUserProfilePanel",
         "modules/consultationView/ConsultationViewNutritionistInfoPanel",
         "modules/consultationView/ConsultationViewObjectivePanel",
         "modules/consultationView/ConsultationViewClinicalPanel",
         "modules/consultationView/ConsultationViewLifeHabitPanel",
         "modules/consultationView/ConsultationViewEatingHabitPanel",
         "modules/consultationView/ConsultationViewBodyMeasuresPanel",
         "modules/consultationView/ConsultationViewPicturesPanel",
         "modules/consultationView/ConsultationViewFatPercentPanel",
         "modules/consultationView/ConsultationViewPrint",
         "components/ModuleEvent"
], 
function (htmlLoader, service, i18ns, i18ns_g, userProfilePanel, nutritionistInfoPanel, 
		objectivePanel, clinicalPanel, lifeHabitPanel, eatingHabitPanel, bodyMeasuresPanel, 
		picturesPanel, fatPercentPanel, viewPrint, moduleEvent){
	function load (container, consultation) {
		htmlLoader.load({
			container: container,
			file: "modules/consultationView/templates/ConsultationViewForm.html",
			i18n: [i18ns, i18ns_g]
		}, function (){
			viewPrint.load ("#printContainer", function (){
				onFinishLoad(consultation);
			});
			SessionInfo.getUserContext (function (user) {
				_this.user = user;
				bindEvents();
			});
		});
	}
	
	function onFinishLoad (consultation) {
		_this.consultation = consultation;
		userProfilePanel.load ("#userProfilePanel", consultation);
		nutritionistInfoPanel.load ("#nutritionistInfo", consultation);
		objectivePanel.load ("#objectivePanel", consultation);
		clinicalPanel.load ("#clinicalPanel", consultation);
		lifeHabitPanel.load ("#lifeHabitPanel", consultation);
		eatingHabitPanel.load ("#eatingHabitPanel", consultation);
		bodyMeasuresPanel.load ("#bodyMeasuresPanel", consultation);
		picturesPanel.load ("#picturesPanel", consultation);
		fatPercentPanel.load ("#fatPercentPanel", consultation);
		
	}
	function bindEvents () {
		_this.currentTab = "TAB1";
		$("#btnTab1").click (function (e){
			$(".tab1").removeClass("hide");
			$(".tab2").addClass("hide");
			$("#btnTab1").addClass("hide");
			$("#btnTab2").html("Próximo");
			_this.currentTab = "TAB1";
		});
		
		$("#btnTab2").click (function (e) {
			if (_this.currentTab == "TAB2")
				finishConsultation();
			else {
				$(".tab2").removeClass("hide");
				$(".tab1").addClass("hide");
				$("#btnTab1").removeClass("hide");
				if (isViewMode())
					$("#btnTab2").html("Ok");
				else 
					$("#btnTab2").html("Finalizar");
				_this.currentTab = "TAB2";
			}
		});
		$("#btnPrint").click (print);
	}
	
	function print () {
		viewPrint.print ();
	}
	
	function finishConsultation() {
		if (isViewMode())
			moduleEvent.loadModule ("consultationHistory");
		else {
			_this.consultation.consultationStatus = "DIETING";
			service.put ({
				url : "userProfile/consultation/",
				id : _this.consultation.id,
				data : _this.consultation,
				success : function () {
					moduleEvent.loadModule ("consultationDayPool");
				} 
			});
		}
	}
	
	function isViewMode () {
		if (_this.user.id != _this.consultation.userNutritionist.id)
			return true;
		if (_this.consultation.consultationStatus == "PENDING")
			return false;
		return true;
	}
	
	var _this = {
		load : load
	};
	return _this;
});