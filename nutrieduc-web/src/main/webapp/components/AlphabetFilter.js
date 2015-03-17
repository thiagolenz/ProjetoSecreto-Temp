define ([], function (){
	
	function create (options) {
		for (var i = 65; i <= 90 ; i++)  
		   appendLetter(String.fromCharCode(i), options);
	} 
	
	function appendLetter (letter, options) {
		var a = document.createElement ("a");
		$(a).html(letter);
		$(a).addClass("alphabet-filter-letter");
		$(a).click (function (){
			$(this).siblings().removeClass("selected");
			$(this).addClass("selected");
			options.onChoose ($(this).html());
		});
		$(options.container).append (a);
	}
	
	function clear (options) {
		$(options.container).find(".selected").removeClass("selected");
	}
	
	return {
		create : create,
		clear : clear
	};
});