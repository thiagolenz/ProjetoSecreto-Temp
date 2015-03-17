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
			file: "modules/consultationView/templates/ConsultationViewObjectivePanel.html",
			i18n: [i18ns, i18ns_g]
		}, function (){
			onFinishLoad(consultation);
		});
	}
	
	function onFinishLoad (consultation) {
		viewValueCopy.copyTextValue("consultation.objective", consultation.objective);
	}
	
	return {
		load : load
	};
});