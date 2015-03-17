define (["components/HtmlLoader",
         "i18n!modules/consultationClinic/nls/ConsultationClinic",
         "i18n!controllers/consultationClinic/nls/ConsultationClinicManagement",
         "modules/consultationClinic/ConsultationClinicGrid",
         "modules/consultationClinic/ConsultationClinicWizard",
         "components/Service",
         "components/ModuleEvent"
],
function (htmlLoader, i18nsAccount, i18nsAccountManager, consultationClinicGrid, consultationClinicWizard, service, moduleEvent){
	function load (args) {
		if (args && args.success )
			loadSuccess(args.consultation);
		else 
			htmlLoader.load({
				container: "#backgroundWide",
				file: "controllers/consultationClinic/templates/ConsultationClinicLayout.html",
				i18n: [i18nsAccount, i18nsAccountManager]
			}, function (){
				onFinishLoad(args);
			}); 
	}
	
	function onFinishLoad (args) {
		if (args)
			consultationClinicWizard.open (args);
		else {	
			UtilInterface.showWideBackground ();
			$("input").focus();
			bindEvents();
		}
	}
	
	function bindEvents () {
		$("#btnContinue").click(continueClick);
		$("#btnShowSearch").click(showSearch);
		$(".btnOk").click (openNewconsultationClinicFormModal);
		$("#btnCancel").click (function (){
			UtilInterface.showNormalBackground ();
			moduleEvent.loadModule ("home");
		});
	}
	
	function continueClick () {
		var documentNumber = $("#documentNumber").val();
		if (documentNumber) {
			$("#documentNumber").parent().removeClass("has-error");
			service.post ({
				url : "userProfile/userInfoProfile/getByDocumentNumber",
				data : {
					documentNumber : documentNumber
				},
				success : function (userInfoProfile) {
					_this.user = userInfoProfile.user;
					showModlByResult(userInfoProfile);
				}
			});
		}
		else {
			$("#documentNumber").parent().addClass("has-error");
			$("#documentNumber").focus();
		}
	}
	
	function showModlByResult (userInfoProfile) {
		if (userInfoProfile.user) {
			$("#modalContinueResult").modal();
			$("#patientName").html (userInfoProfile.user.name);
		} else {
			$("#modalNotFoundResult").modal ();
		}
	}
	
	function afterCloseAccountModal (consultation) {
		loadSuccess(consultation);
	}
	
	function loadSuccess (consultation) {
		htmlLoader.load({
			container: "#body",
			file: "controllers/consultationClinic/templates/ConsultationSuccess.html",
			i18n: [i18nsAccount, i18nsAccountManager]
		}, function (){
			$("#btnNewConsultation").click ( function (){
				load ();
			});
			$("#openConsultationView").click (function (){
				moduleEvent.loadModule ("consultationView", {consultation : consultation});
			});
		});
	}

	function openNewconsultationClinicFormModal () {
		$("#modalContinueResult").modal("hide");
		$("#modalNotFoundResult").modal("hide");
		if (_this.user == undefined)
			_this.user = {};
		UtilInterface.showNormalBackground ();
		consultationClinicWizard.open ({
			onSuccess : afterCloseAccountModal,
			consultation : {
				userPatient : _this.user
			}
		});
	}
	
	function showSearch () {
		$("#blankSlate").addClass("hide");
		$("#divSearch").removeClass("hide");
	}

	var _this = {
		load : load ,
		onCloseModal: afterCloseAccountModal
	};
	
	return _this;
});