define (["components/HtmlLoader",
         "components/Service",
         "i18n!modules/consultationClinic/nls/ConsultationClinic",
         "i18n!components/nls/Generic",
         "components/ViewValueCopy",
], 
function (htmlLoader, service, i18ns, i18ns_g, viewValueCopy){
	function load (container, consultation) {
		htmlLoader.load({
			container: container,
			file: "modules/consultationView/templates/ConsultationViewClinicalPanel.html",
			i18n: [i18ns, i18ns_g]
		}, function (){
			onFinishLoad(consultation);
		});
	}
	
	function onFinishLoad (consultation) {
		var clinicalData = consultation.clinicalData;
		viewValueCopy.copyTextValue("clinicalData.diseasesInTreatment", clinicalData.diseasesInTreatment);
		viewValueCopy.copyTextValue("clinicalData.medicationsInUse", clinicalData.medicationsInUse);
		viewValueCopy.copyTextValue("clinicalData.didStomachSurgery", clinicalData.didStomachSurgery ? i18ns_g.yes : i18ns_g.no);
		viewValueCopy.copyTextValue("clinicalData.didTherapy", clinicalData.didTherapy ? i18ns_g.yes : i18ns_g.no);
		viewValueCopy.copyTextValue("clinicalData.lastTypeDidBloodExam", clinicalData.lastTypeDidBloodExam);
		
		loadBloodType (clinicalData);
		loadIntestineType (clinicalData);
	}
	
	function loadBloodType (clinicalData) {
		service.get ({
			url : "userProfile/bloodType/retrieveTypes",
			success : function (data) {
				for (var i = 0; i < data.length; i++)
					if (data[i].enumValue == clinicalData.bloodType) {
						viewValueCopy.copyTextValue("clinicalData.bloodType", data[i].description);
						break;
					}
			} 
		});	
	}
	
	function loadIntestineType (clinicalData) {
		service.get ({
			url : "userProfile/intestineFrequencyType/retrieveTypes",
			success : function (data) {
				for (var i = 0; i < data.length; i++)
					if (data[i].enumValue == clinicalData.intestineFrequencyType) {
						viewValueCopy.copyTextValue("clinicalData.intestineFrequencyType", data[i].description);
						break;
					}
			} 
		});	
	}
	
	return {
		load : load
	};
});