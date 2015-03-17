define (["components/Messages",  
         "i18n!components/nls/Generic", 
         "components/AutoComplete"], 
function (messages, i18ns, autoComplete) {
	
	function validateMandatory (options) {
		var fields = getMandatoryFields(options);
		var firstField = null;
		for (var i = fields.length-1 ; i >= 0; i--) {
			var fieldConf = fields[i];
			if (fieldConf.info) {
				var hasError =  defineIfHasError(options, fieldConf);
					
				if (hasError){
					$(fieldConf.div).addClass("has-error");
					firstField = fieldConf;
				}else 
					$(fieldConf.div).removeClass("has-error");
			}
		}
		processEndValidations(options, firstField);
	}
	
	function defineIfHasError (options, fieldConf) {
		var element = fieldConf.info.element;
		var source = fieldConf.info.source;
		var varName = fieldConf.info.varname;
		if (isJustReadingElement(element)){
			var type = $(element).attr("data-type");
			if (type == "AutoComplete")
				return autoComplete.getValue($(element).attr("data-name")) == undefined;
		} else if (options.multipleSources) {
			if (!options.multipleSources [source] [varName])
				return true;
		} else if (!options.bean[varName])
				return true;
		return false;
	}
	
	function isJustReadingElement (element) {
		return $(element).attr("data-just-reading");
	}
	
	function processEndValidations (options, firstField) {
		if (firstField) {
			if (firstField.info.element.tagName != "INPUT")
				$(firstField.info.element).find("input").focus();
			else 
				$(firstField.info.element).focus();
			showError(options, i18ns.mandatoryFields);
		} else {
			var result = undefined;
			if (options.extraValidation) {
				result = options.extraValidation (options.bean);
				if (result != undefined && typeof result != "string") {
					$(result.field).parent().addClass("has-error");
					$(result.field).focus();
					result = result.message;
				}
			}
			if (result)
				showError(options, result);
			else {
				messages.clear();
				options.success ();
			}
		}
	}
	
	function showError (options, messageValue) {
		messages.error({
			title: i18ns.error,
			inModal : options.inModal,
			message: {
				errorDescription : messageValue
			}
		});
	}
	
	function getMandatoryFields (options) {
		var array = new Array();
		var divs = $("#" + options.container + " .required:not(.hidden):not(.disabled)");
		for (var i = 0; i < divs.length; i++) {
			if (options.multipleSources)
				array.push({
					div : divs[i],
					info : getMappingFieldMultiple(options, divs[i])
				});
			else 
				array.push({
					div : divs[i],
					info : getMappingField(options, divs[i])
				});
		}
		return array;
	}
	
	function getMappingFieldMultiple (options, subcontainer) {
		var elements = $(subcontainer).find("[name]");
		if (elements.length)
			return getNameField(elements[0]);
		
		elements = $(subcontainer).find("[data-name]");
		if (elements.length)
			return getDataNameField(elements[0]);
	}
	
	function getMappingField (options, subcontainer) {
		var elements = $(subcontainer).find("[name*='"+options.source+".']");
		if (elements.length)
			return getNameField(elements[0]);
		
		elements = $(subcontainer).find("[data-name*='"+options.source+".']");
		if (elements.length)
			return getDataNameField(elements[0]);
	}
	
	function getDataNameField (element) {
		var split = $(element).attr("data-name").split("."); 
		return {
			source : split [0],
			varname : split[1],
			element: element
		};
	}
	
	function getNameField (element) {
		var split = $(element).attr("name").split(".");
		return {
			source : split [0],
			varname : split[1],
			element: element
		};
	}
	
	return {
		validateMandatory: validateMandatory
	};
});