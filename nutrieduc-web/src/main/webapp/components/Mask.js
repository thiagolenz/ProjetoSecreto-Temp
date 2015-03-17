define (["i18n!components/nls/Generic",
         "moment"], 
function(i18ns_g, moment){
	
	function formatDocumentNumber (value, personType) {
		var maskConf = getMaskPersonType(personType);
		value = documentNumberToString(value, maskConf);
		var input = document.createElement("input");
		$(input).val(value);
		$(input).mask(maskConf);
		return $(input).val();
	}
	
	function documentNumberToString (document, mask) {
		document = ""+document;
		mask = getValueWithoutMask(mask);
		for (var i = document.length; i < mask.length ; i++)
			document = "0" + document;
		return document;
	}
	
	function getValueWithoutMask (value) {
		var regex = /[^\w\s]/gi;
		return value.replace(regex,'');
	}
	
	function getMaskPersonType (personType) {
		if (personType == "LEGAL_PERSON") 
			return i18ns_g.masks.legalPerson;
		else  
			return i18ns_g.masks.naturalPerson;	
	}
	
	function maskHour (select) {
		$(select).mask ("99:99");
	}
	
	function setHourValue (select, dateValue) {
		var value = moment(dateValue).format('HHmm');
		$(select).val(value).mask("99:99");
	}
	
	function maskDate (input) {
		$(input).mask(i18ns_g.masks.date);
	}
	
	return {
		documentNumberToString : documentNumberToString,
		getValueWithoutMask : getValueWithoutMask,
		getMaskPersonType :  getMaskPersonType,
		formatDocumentNumber : formatDocumentNumber,
		maskHour : maskHour,
		maskDate : maskDate,
		setHourValue : setHourValue
	};
});