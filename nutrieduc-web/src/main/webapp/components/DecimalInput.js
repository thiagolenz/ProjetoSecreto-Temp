define(["i18n!components/nls/DecimalInput"],
function (i18ns){

	function createDecimalInput (config) {
		var input = document.createElement("input");
		configInput(input, config);
		return input;
	}
	
	function configInputSelection (selection, config) {
		configInput($(selection), config);
	}
	
	function configInput (input, config) {
		if (!config)
			config = {};
		$(input).maskMoney(i18ns.config.maskConfig);
		$(input).addClass("form-control text-right");
		$(input).attr("data-type", "Decimal");
		if (config.dataName) {
			$(input).attr("data-name", config.dataName);
		}
		$(input).on("blur", function (){
			setTimeout(function () {},10);
			var value = $(this).maskMoney('unmasked')[0];
			$(this).maskMoney('mask');
			if (value != $(input).data('value')){
				if (config.onValueChange){
					config.onValueChange(value, this);
				}
				$(input).attr('data-value', value);
			}
		});	
		if (config.onFocus) {
			$(input).on("focus", function (event) {
				config.onFocus(event.target);
			});
		}
		$(input).on("click", function (event) {
			var input = event.target;
			input.setSelectionRange(0, input.value.length);
		});
	}
	
	function getValue (input) {
		return $(input).maskMoney('unmasked')[0];
	}
	
	function setValue (input, value) {
		$(input).maskMoney('mask', value);
	}  
	
	var _this = {
			createDecimalInput: createDecimalInput,
			configInputSelection : configInputSelection,
			getValue : getValue,
			setValue : setValue
	};
	return _this;
});