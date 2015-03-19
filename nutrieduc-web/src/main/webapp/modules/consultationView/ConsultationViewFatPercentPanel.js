define (["components/HtmlLoader",
         "components/Service",
         "i18n!modules/consultationClinic/nls/ConsultationClinic",
         "i18n!components/nls/Generic",
         "components/Numeral",
         "components/ViewValueCopy",
], 
function (htmlLoader, service, i18ns, i18ns_g, numeral, viewValueCopy){
	function load (container, consultation) {
		htmlLoader.load({
			container: container,
			file: "modules/consultationView/templates/ConsultationViewFatPercentPanelPanel.html",
			i18n: [i18ns, i18ns_g]
		}, function (){
			onFinishLoad(consultation);
		});
	}
	
	function onFinishLoad (consultation) {
		var bodyMeasure = consultation.bodyMeasure;
		viewValueCopy.copyTextValue("bodyMeasure.faulknerFatPercent", formatValue (bodyMeasure.faulknerFatPercent));
		viewValueCopy.copyTextValue("bodyMeasure.guedesFatPercent", formatValue (bodyMeasure.guedesFatPercent));
		viewValueCopy.copyTextValue("bodyMeasure.jacksonPolloFatPercent", formatValue (bodyMeasure.jacksonPolloFatPercent));
		viewValueCopy.copyTextValue("bodyMeasure.durninWomersleyFatPercent", formatValue (bodyMeasure.durninWomersleyFatPercent));
	}
	
	function formatValue (value) {
		if (value == undefined )
			value = 0;
		return numeral.formatDecimal(value);
	}
	
	return {
		load : load
	};
});