define ([], function () {

	function config (input) {
		$(input).keypress(function (e) {
			if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
				return false;
			}
		});
	}
	
	function getValue (input) {
		return Number ($(input).val());
	}
	
	function setValue (input, value) {
		var string = "" +value;
		value = string.split (".") [0];
		$(input).val(value);
	}

	return {
		config : config,
		setValue : setValue,
		getValue: getValue
	};
});
