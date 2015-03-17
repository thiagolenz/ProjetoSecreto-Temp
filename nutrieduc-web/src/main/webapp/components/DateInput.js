define (["components/Mask", 
         "moment",
         "i18n!components/nls/Generic"], 
function (mask, moment, i18ns_g){
	function configure () {
		var inputs = $(".dataTypeComponent");
		for (var i = 0; i< inputs.length ; i++) {
			var input =  inputs[i] ;
			var parent = $(input).parent ();
		
			$(input).css("width", "75%");
			$(input).css("float", "left");
			mask.maskDate (input);
			var calendarBtn = document.createElement("button");
			$(calendarBtn).css("float", "left");
			$(calendarBtn).css("width", "36px");
			$(calendarBtn).css("height", "34px");
			$(calendarBtn).css("cursor", "pointer");
			
			$(calendarBtn).addClass("input-group-addon");
			var icon = document.createElement ("i");
			$(icon).addClass("glyphicon glyphicon-calendar");
			$(calendarBtn).append (icon);
			$(parent).append(calendarBtn);
			
			$(calendarBtn).on ("click", function (){
				var prev = $(this);
				prev.datepicker ({
						autoclose: true,
						format: moment.langData()._longDateFormat.L.toLowerCase(),
						language: defineLang()
					}).on("changeDate", changeDate);
				prev.datepicker ("show");
			});
		}
	}
	
	function changeDate (event) {
		var tabables = $("input,button, textarea[tabindex != '-1']:visible");
		var currentIndex = tabables.index(event.target);
		console.log(event.value);
		var value = moment(event.date).format('L');
		$(event.target).prev().val (value);
		$(tabables[currentIndex+1]).focus(); // considerando o botao
	}
	
	function setValue (element, value) {
		var newValue = moment(value).format('L');
		$(element).val (newValue);
	}
	
	function getValue (element) {
		var text = $(element).val ();
		if (text)
			return moment (text, i18ns_g.formats.date).toDate ().getTime();
		
	}

	function defineLang () {
		var lang = moment.lang();
		lang = lang.substring(0, 3) + lang.substring(3,5).toUpperCase();
		return lang;
	}

	
	return {
		configure : configure,
		setValue : setValue,
		getValue: getValue
	};
});