define (["components/HtmlLoader",
         "components/Service",
         "i18n!modules/consultationClinic/nls/ConsultationClinic",
         "i18n!components/nls/Generic",
], 
function (htmlLoader, service, i18ns, i18ns_g){
	function load (container, consultation) {
		htmlLoader.load({
			container: container,
			file: "modules/consultationView/templates/ConsultationViewPicturesPanel.html",
			i18n: [i18ns, i18ns_g]
		}, function (){
			onFinishLoad(consultation);
		});
	}
	
	function onFinishLoad (consultation) {
		var bodyMeasure = consultation.bodyMeasure;
		updateImage("frontPicture", bodyMeasure);
		updateImage("backPicture", bodyMeasure);
		updateImage("leftPicture", bodyMeasure);
		updateImage("rightPicture", bodyMeasure);
	}
	
	function updateImage (fieldName, bodyMeasure) {
		if (bodyMeasure [fieldName])
			$("#"+fieldName).attr("src", "storageresource/consultation/"+bodyMeasure[fieldName].fileName);
	}
	
	return {
		load : load
	};
});