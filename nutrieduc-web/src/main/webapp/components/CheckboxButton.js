define ([], function (){
	function create (options) {
		var btnGroup = document.createElement("div");
		$(btnGroup).addClass ("btn-group"); 
		
		createCheckboxButton({label : "Sim", value: "YES"}, btnGroup, options.onChange);
		createCheckboxButton({label : "Não", value: "NO"}, btnGroup, options.onChange);
		
		$("#"+options.container).attr ("data-type", "boolean");
		
		$("#"+options.container).append(btnGroup);
	
		if (options.label) {
			$("#"+options.container).append(options.label);
			$(btnGroup).addClass("margin-rigth-10");
		}

		setValue(options.container, "NO");
		if (options.onFinishCreate)
			options.onFinishCreate();
	}
	
	function createCheckboxButton (element, btnGroup, onChange) {
		var button = document.createElement("button");
		$(button).attr ("data-value", element.value);
		$(button).addClass("btn");
		$(button).html(element.label);
		$(button).click (function (event){
			onClick(event);
			if (onChange)
				onChange();
		});
		$(btnGroup).append (button);
	}
	
	function onClick (event) {
		toggleButton(event.target);
		toggleButton($(event.target).siblings()[0]);
	}
	
	function getValue (container) {
		return $(container).find("button.active").attr("data-value") == "YES";
	}
	
	function setValue (container, value) {
		var buttons = $("#"+container).find("button");
		if (value == "NO" || !value) {
			$(buttons[1]).addClass("btn-secondary active");
			$(buttons[1]).removeClass("btn-default");
			$(buttons[0]).removeClass("btn-secondary active");
			$(buttons[0]).addClass("btn-default");
		} else {
			$(buttons[0]).addClass("btn-secondary active");
			$(buttons[0]).removeClass("btn-default");
			$(buttons[1]).removeClass("btn-secondary active");
			$(buttons[1]).addClass("btn-default");
		}
	}

	function toggleButton (button) {
		$(button).toggleClass("active");
		$(button).toggleClass("btn-secondary");
		$(button).toggleClass("btn-default");
	}
 	
	return {
		create : create,
		getValue : getValue,
		setValue : setValue
	} ;
});