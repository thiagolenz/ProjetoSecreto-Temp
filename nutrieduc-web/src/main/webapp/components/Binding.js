define(["components/Formatter", 
        "moment", 
        "components/CheckboxButton",
        "components/Mask", 
        "components/AutoComplete",
        "components/MonetaryInput",
        "components/DecimalInput",
        "components/IntegerInput",
        "components/DateInput"
        ], 
function (formatter, moment, checkboxButton, mask, autoComplete, monetaryInput, decimalInput, integerInput, dateInput){
	
	function copyBeanToForm (source, bean) {
		copyFieldsToForm(source, bean);
		copyFieldsExtendsToForm(source, bean);
	} 
	
	function copyFieldsToForm (source, bean) {
		var mapAttFields = mapAttributesFields(source);
		for (var i = 0 ; i < mapAttFields.length; i++) {
			var obj = mapAttFields[i];
			setData(obj.element, obj.type, bean[obj.attribute]);
		}
	}
	
	function setData (element, type, value) {
		if (type === "String" || type == null) 
			$(element).val(value);
		else if (type === "Integer")
			$(element).val(value);
		else if (type === "Date") {
			var date = moment(value).format('L');
			$(element).datepicker('update', date);
		} else if (type == "masked") {
			var mask = $(element).attr("data-mask");
			$(element).val(value).mask(mask);
		}  else if (type == "Monetary") {
			monetaryInput.setValue (element, value);
		} else if (type == "Decimal") {
			decimalInput.setValue (element, value);
		} else if (type == "DateMask") {
			dateInput.setValue (element, value);
		}
	}
	
	function copyFieldsExtendsToForm (source, bean) {
		var mapAttFieldsExtends = mapAttributesFieldsExtends(source);
		for (var i = 0 ; i < mapAttFieldsExtends.length; i++) {
			var obj = mapAttFieldsExtends[i];
			setDataExtends(obj.element, obj.type, bean[obj.attribute]);
		}
	}
	
	function setDataExtends (element, type, value) {
		if (type == "checkbox") 
			checkboxButton.setValue(element, value);
		else if (type == "AutoComplete")
			autoComplete.setValue($(element).attr("data-name"), value);
		else if (type == "boolean")
			checkboxButton.setValue(element.id, value);
		else if (type == "Decimal")
			decimalInput.setValue(element, value);
		else if (type == "Monetary")
			monetaryInput.setValue(element, value);
		else if (type == "Integer") 
			integerInput.setValue (element, value);
	}
	
	function copy (source, destiny) {
		if (!destiny)
			destiny = {};
		
		copyFields(source, destiny);
		copyFieldsExtends(source, destiny);
		
		return destiny;
	}
	
	function copyFields (source, destiny) {
		var mapAttFields = mapAttributesFields(source);
		for (var i = 0 ; i < mapAttFields.length; i++) {
			var obj = mapAttFields[i];
			destiny[obj.attribute] = parseData (obj.element.value, obj.type, obj.element);
		}
	}
	
	function copyFieldsExtends (source, destiny) {
		var mapAttFieldsExtends = mapAttributesFieldsExtends(source);
		for (var i = 0 ; i < mapAttFieldsExtends.length; i++) {
			var obj = mapAttFieldsExtends[i];
			destiny[obj.attribute] = parseDataExtends (obj.element, obj.type);
		}
	}
	
	function mapAttributesFields (source) {
		var array = new Array();
		var elements = $("[name*='"+source+".']:not([data-just-reading='true']");
		for (var i = 0; i < elements.length ; i++) {
			var element = elements[i];
			var attribute = element.name.split(".")[1];
			array.push({
				attribute : attribute,
				element : element,
				type : element.getAttribute("data-type")
			});
		}
		return array;
	}
	
	function mapAttributesFieldsExtends (source) {
		var array = new Array();
		var elements = $("[data-name*='"+source+".']:not([data-just-reading='true']");
		for (var i = 0; i < elements.length ; i++) {
			var element = elements[i];
			var attribute = $(element).attr("data-name").split(".")[1];
			array.push({
				attribute : attribute,
				element : element,
				type : element.getAttribute("data-type")
			});
		}
		return array;
	}
	
	function parseData (value, type, element) {
		if (!type || type === "String" || !value)
			return value;
		else if (type === "Integer")
			return parseInt(value, 10);
		else if (type === "Date")
			return  moment(value, moment.langData()._longDateFormat.L).toDate();
		else if (type === "masked") 
			return mask.getValueWithoutMask(value);
		else if (type == "DateMask")
			return dateInput.getValue (element);
		return value;
	}
	
	function parseDataExtends (element, type) {
		if (type == "checkbox")
			return checkboxButton.getValue(element);
		else if (type == "AutoComplete")
			return autoComplete.getValue($(element).attr("data-name"));
		else if (type == "boolean")
			return checkboxButton.getValue(element);
		else if (type == "Monetary")
			return monetaryInput.getValue(element);
		else if (type == "Decimal")
			return decimalInput.getValue(element);
		else if (type == "Integer")
			return integerInput.getValue (element);
		else if (type == "DateMask")
			dateInput.setValue (element, value);
		return undefined;
	}
	
	return {
		copy : copy,
		copyBeanToForm : copyBeanToForm
	};
});