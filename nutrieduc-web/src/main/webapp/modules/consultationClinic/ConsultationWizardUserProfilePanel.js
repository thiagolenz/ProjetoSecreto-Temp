define([
        "moment", 
        "components/Binding", 
        "components/Formatter",
        "components/HtmlLoader",
        "i18n!modules/consultationClinic/nls/ConsultationClinic",
        "i18n!components/nls/Generic",
        "components/AutoComplete",
        "components/FieldsValidation",
        "components/ToggleButton",
        "components/Service",
        "components/Messages",
        "components/IntegerInput",
        "components/EmailValidation",
        "components/Mask",
        "components/DateInput"
],
function (moment, binding, formatter, htmlLoader, i18ns, i18ns_g, autoComplete, fieldsValidation, 
		toggleButton, service, messages, integerlInput, emailValidation, mask, dateInput) {
	
	function load (config, start) {
		_this.config = config;
		htmlLoader.load({
			container: config.container,
			file: "modules/consultationClinic/templates/ConsultationWizardUserProfilePanel.html",
			i18n: [i18ns]
		}, function () {
			_this.start = start;
			onFinishLoad();
		});
		$(config.titleContainer).html(i18ns.consultation.userProfilePanel);
	}
	
	function onFinishLoad () {
		dateInput.configure();
		integerlInput.config ("#consultationAge");
		createNutritionistAutoComplete();
		createGenderType ();
		_this.consultation = _this.config.consultation;
		setup();
		$($("#currentStepContainer input")[0]).focus();
	}
	
	function createNutritionistAutoComplete () {
		autoComplete.create ({
			container : "nutritionistAutoCompleteContainer",
			url : "userProfile/nutritionist/retrieveAccountNutritionists",
			varDisplay : "name",
			varSearch : "name",
			canCreate : false
		});
	}
	
	function createGenderType () {
		service.get ({
			url : "userProfile/genderType/retrieveTypes",
			skipLoading : true,
			success : function (data) {
				onLoadGenderTypes (data, function() {
				});
			} 
		});	
	}
	
	function onLoadGenderTypes (data, onFinishCreate) {
		toggleButton.create ({
			container : "genderTypeContainer",
			name : "userInfoProfile.genderType",
			varname : "description",
			data : data,
			onFinishCreate : onFinishCreate
		});
	}
	
	function setup () {
		createMasks();
		binding.copyBeanToForm("consultation", _this.consultation);
		binding.copyBeanToForm("patientUser", _this.consultation.userPatient);
		if (_this.consultation.userPatient.id != undefined) {
			loadUserInfoProfile();
			loadAlreadIsPatient();
		}
	}
	
	function loadUserInfoProfile () {
		if (_this.start)
			service.get({
				url :  "userProfile/userInfoProfile/"+_this.consultation.userPatient.id,
				success : function (userInfoProfile) {
					ajustDocumentNumberEdit (userInfoProfile);
					copyUserInfoProfile(userInfoProfile);
				}
			});
		else {
			copyUserInfoProfile(_this.userInfoProfile);
		}
	}
	
	function ajustDocumentNumberEdit (userInfoProfile) {
		if (userInfoProfile.documentId) {
			var maskConf = mask.getMaskPersonType("PERSON");
			userInfoProfile.documentId = mask.documentNumberToString(userInfoProfile.documentId, maskConf);
		}
	}
	
	function copyUserInfoProfile (userInfoProfile) {
		binding.copyBeanToForm("userInfoProfile", userInfoProfile);
		if (userInfoProfile.genderType)
			toggleButton.setSelected ("userInfoProfile.genderType", userInfoProfile.genderType);
		_this.userInfoProfile = userInfoProfile;
	}
	
	function loadAlreadIsPatient () {
		service.get ({
			url : "userProfile/accountPatient/" +_this.consultation.userPatient.id,
			success : function (result) {
				if (result.patientUser)
					_this.isPatient = true;
				_this.accountPatient = result;
			}
		});
	}
	
	function createMasks () {
		var maskConf = mask.getMaskPersonType("PERSON");
		$("#documentId").mask(maskConf).attr("data-mask", maskConf);
	}
	
	function validate(consultation, onSuccess) {
		if (!consultation)
			consultation = {};
		consultation = binding.copy("consultation", consultation);
		consultation.accountPatient = _this.accountPatient;
		
		_this.consultation.userPatient = binding.copy("patientUser", _this.consultation.userPatient);
		
		var userInfoProfile = binding.copy("userInfoProfile", _this.userInfoProfile);
		
		userInfoProfile.genderType = toggleButton.getSelectedValue ("userInfoProfile.genderType");
		
		validateAll (_this.consultation.userPatient, userInfoProfile, consultation, function (){
			savePatientUser (_this.consultation.userPatient, userInfoProfile, onSuccess);
		});
	}
	
	function validateAll (patientUser, userInfoProfile, consultation, success) {
		fieldsValidation.validateMandatory ({
			multipleSources : {
				"patientUser": patientUser,
				"userInfoProfile": userInfoProfile,
				"consultation":consultation
			},
			extraValidation : function (bean) {
				var emailValid = emailValidation.isValid (patientUser.email);
				if (!emailValid) {
					return {
						field : "#consultationEmail",
						message : "O email informado não é válido. Siga o formato email@exemplo.com ou email@exemplo.com.br"
					};
				}
			},
			container : "body", 
			success : function() {
				success ();
			}
		});
	}
	
	function savePatientUser (patientUser, userInfoProfile, success) {
		var urlCreate = "userProfile/user/createPatientUser";
		var update = "userProfile/user/";
		var request = {
			data : patientUser,
			id : patientUser.id,
			success : function (user) {
				_this.consultation.userPatient = user;
				saveUserInfoProfile(user, userInfoProfile, function (){
					if (_this.isPatient == false)
						bindPatientUser(user, success);
					else 
						success ();
				});

			},
			error : error
		};
		if (patientUser.id == undefined) {
			request.url = urlCreate;
			service.post(request);
		} else {
			request.url = update;
			service.put(request);
		}
	}
	
	function saveUserInfoProfile (user, userInfoProfile, success) {
		userInfoProfile.user = user;
		var request = {
			url : "userProfile/userInfoProfile/",
			data : userInfoProfile,
			id : userInfoProfile.id,
			success : function (result) {
				_this.userInfoProfile = result;
				success();
			},
			error : error 
		};
		if (userInfoProfile.id)
			service.put (request);
		else 
			service.post (request);
	}
	
	function bindPatientUser (user, success) {
		service.post({
			url : "userProfile/accountPatient/bindUserPatient",
			data : user,
			success : success,
			error : error
		});
	}
	
	function error (data) {
		messages.error({
			title: i18ns_g.error,
			message: data
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