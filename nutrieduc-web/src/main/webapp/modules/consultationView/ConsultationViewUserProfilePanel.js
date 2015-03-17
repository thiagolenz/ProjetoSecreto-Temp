define (["components/HtmlLoader",
         "components/Service",
         "i18n!modules/consultationClinic/nls/ConsultationClinic",
         "i18n!components/nls/Generic",
         "components/Binding",
         "components/ViewValueCopy",
         "components/Mask"
], 
function (htmlLoader, service, i18ns, i18ns_g, binding, viewValueCopy, mask){
	function load (container, consultation) {
		_this.consultation = consultation;
		_this.patientUser = _this.consultation.userPatient;
		htmlLoader.load({
			container: container,
			file: "modules/consultationView/templates/ConsultationViewUserProfilePanel.html",
			i18n: [i18ns, i18ns_g]
		}, onFinishLoad);
	}
	
	function onFinishLoad () {
		loadUserProfile();

	}
	
	function loadUserProfile () {
		service.get({
			url :  "userProfile/userInfoProfile/"+_this.patientUser.id,
			success : function (userInfoProfile) {
				viewValueCopy.copyTextValue("patientUser.name", _this.patientUser.name);
				viewValueCopy.copyTextValue("userInfoProfile.age", userInfoProfile.age);
				viewValueCopy.copyDateValue("userInfoProfile.birthDate", userInfoProfile.birthDate);
				viewValueCopy.copyTextValue("userInfoProfile.addressStreet", userInfoProfile.addressStreet);
				viewValueCopy.copyTextValue("userInfoProfile.addressNumber", userInfoProfile.addressNumber);
				viewValueCopy.copyTextValue("userInfoProfile.addressComplement", userInfoProfile.addressComplement);
				viewValueCopy.copyTextValue("userInfoProfile.phoneNumber", userInfoProfile.phoneNumber);
				viewValueCopy.copyTextValue("userInfoProfile.documentNumber", userInfoProfile.documentNumber);
				viewValueCopy.copyTextValue("userInfoProfile.documentId", getDocumentIdWithMask(userInfoProfile));
				viewValueCopy.copyTextValue("patientUser.email", _this.patientUser.email);
				loadGenderType(userInfoProfile);
				loadAccountPatient ();
			}
		});
	}
	
	function getDocumentIdWithMask (userInfoProfile) {
		return mask.formatDocumentNumber(userInfoProfile.documentId, "PERSON");
	}
	
	function loadGenderType (userInfoProfile) {
		service.get ({
			url : "userProfile/genderType/retrieveTypes",
			skipLoading : true,
			success : function (data) {
				for (var i = 0; i < data.length; i++)
					if (data[i].enumValue == userInfoProfile.genderType) {
						viewValueCopy.copyTextValue("userInfoProfile.genderType", data[i].description);
						break;
					}
			} 
		});	
	}
	
	function loadAccountPatient () {
		service.get ({
			url : "userProfile/accountPatient/" +_this.consultation.userPatient.id,
			success : function (result) {
				_this.accountPatient = result;
				updateUserAvatarImage();
			}
		});	
	}
	
	function updateUserAvatarImage () {
		var dataImage = undefined;
		if (_this.accountPatient.avatarBase64 != undefined)
			dataImage = _this.accountPatient.avatarBase64;
		else 
			dataImage = $("#defaultAvatar").val();

		$(".userAvatar").attr("src","data:image/png;base64,"+ dataImage);
	}
	
	var _this = {
		load : load
	};
	
	return _this;
});