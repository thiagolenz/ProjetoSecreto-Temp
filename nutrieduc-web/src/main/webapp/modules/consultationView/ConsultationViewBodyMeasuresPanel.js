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
			file: "modules/consultationView/templates/ConsultationViewBodyMeasuresPanel.html",
			i18n: [i18ns, i18ns_g]
		}, function (){
			onFinishLoad(consultation);
		});
	}
	
	function onFinishLoad (consultation) {
		var bodyMeasure = consultation.bodyMeasure;
		viewValueCopy.copyTextValue("bodyMeasure.weight", formatValue (bodyMeasure.weight));
		viewValueCopy.copyTextValue("bodyMeasure.height", formatValue (bodyMeasure.height));
		viewValueCopy.copyTextValue("bodyMeasure.bendingTriceps", formatValue (bodyMeasure.bendingTriceps));
		viewValueCopy.copyTextValue("bodyMeasure.bendingBack", formatValue (bodyMeasure.bendingBack));
		viewValueCopy.copyTextValue("bodyMeasure.bendingBiceps", formatValue (bodyMeasure.bendingBiceps));
		viewValueCopy.copyTextValue("bodyMeasure.foldBelt", formatValue (bodyMeasure.foldBelt));
		viewValueCopy.copyTextValue("bodyMeasure.abdominalTuck", formatValue (bodyMeasure.abdominalTuck));
		viewValueCopy.copyTextValue("bodyMeasure.bendingThigh", formatValue (bodyMeasure.bendingThigh));
		viewValueCopy.copyTextValue("bodyMeasure.axillaryFold", formatValue (bodyMeasure.axillaryFold));
		viewValueCopy.copyTextValue("bodyMeasure.pectoralFold", formatValue (bodyMeasure.pectoralFold));
		viewValueCopy.copyTextValue("bodyMeasure.leftArmContracted", formatValue (bodyMeasure.leftArmContracted));
		viewValueCopy.copyTextValue("bodyMeasure.rightArmContracted", formatValue (bodyMeasure.rightArmContracted));
		viewValueCopy.copyTextValue("bodyMeasure.chestCircumference", formatValue (bodyMeasure.chestCircumference));
		viewValueCopy.copyTextValue("bodyMeasure.weistCircumference", formatValue (bodyMeasure.weistCircumference));
		viewValueCopy.copyTextValue("bodyMeasure.abdomenCircumference", formatValue (bodyMeasure.abdomenCircumference));
		viewValueCopy.copyTextValue("bodyMeasure.rightThighCircumference", formatValue (bodyMeasure.rightThighCircumference));
		viewValueCopy.copyTextValue("bodyMeasure.leftThighCircumference", formatValue (bodyMeasure.leftThighCircumference));
		viewValueCopy.copyTextValue("bodyMeasure.rightCalfCircumference", formatValue (bodyMeasure.rightCalfCircumference));
		viewValueCopy.copyTextValue("bodyMeasure.leftCalfCircumference", formatValue (bodyMeasure.leftCalfCircumference));
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