define (["components/Formatter",
         "moment"], 
function (formatter, moment){
	function copyTextValue (varname, value) {
		if (value == undefined || value == "")
			value = "-";
		copyValue(varname, value);
	}
	
	function copyDateValue (varname, value) {
		if (value == undefined)
			value = "-";
		else 
			value =  moment(value).format('L');
		copyValue(varname, value);
	}
	
	function copyValue (varname, value) {
		$("[data-name='"+varname+"'").html(value);
	}
	
	return {
		copyTextValue : copyTextValue,
		copyDateValue : copyDateValue
	};
});