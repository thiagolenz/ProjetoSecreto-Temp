define ([], function () {
	function config (options) {
		var inputs = $(options.container).find("input[maxlength]");
		processArray(inputs);
		var textareas = $(options.container).find("textarea[maxlength]");
		processArray(textareas);
	}	
	
	function processArray (array) {
		$.each (array, function (i, input){
			$(input).on("focus", function (event){
				showTyping(input);
			});
			$(input).on("blur", function (event){
				hideTyping(input);
			});
			
			$(input).keyup(function (event) {
				showTyping(input);
			});
		});
	}
	
	function showTyping (input) {
		var small = $(input).prev();
		if ($(small).is("small"))
			hideTyping(input);
		small = document.createElement ("small");
		$(small).css("float", "right");
		$(small).insertBefore(input); 
		small.textContent = input.value.length + " de " + $(input).attr("maxlength");
	}
	
	function hideTyping (input) {
		$(input).prev().remove();
	}
	
	return {
		config : config
	};
});