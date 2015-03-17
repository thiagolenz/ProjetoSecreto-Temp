define([], function (){
	
	function create (options) {
		return createElements(options);
	}
	
	function createElements (options) {
		var btnGroup = document.createElement("div");
		$(btnGroup).addClass ("btn-group");
		$(btnGroup).attr("data-name" , options.name);
		$(btnGroup).attr("data-customvisibleclass", options.customVisibleClass != undefined);
		if (options.customSelectionClass)
			$(btnGroup).attr("data-customselectionclass", options.customSelectionClass);
		for (var i = 0 ; i< options.data.length; i++)
			createToggleButton (options.data[i], btnGroup, options);
		if (!options.container)
			return btnGroup;
		$("#"+options.container).append(btnGroup);
		
		if (!options.notSelectDefault)
			setActive($("#"+options.container + " button")[0]);
		if (options.onFinishCreate) 
			options.onFinishCreate();
	}
	
	function createToggleButton (element, btnGroup, options) {
		var button = document.createElement("button");
		$(button).attr("data-toggle", "button");
		$(button).attr("data-enumvalue", element.enumValue);
		$(button).html(element[options.varname]);
		if (options.customVisibleClass)
			$(button).addClass(options.customVisibleClass(element));
		
		$(button).addClass("btn");
		if (!options.notSelectDefault)
			$(button).addClass("btn-default");
		
		if (options.defaultValue == element.enumValue)
			setActive(button);
		
		$(button).click (function (event) {
			onButtonClick(event.target, options.name);
			if (options.onChange)
				options.onChange (getSelectedValue(options.name));
		});
		$(btnGroup).append (button);
	}
	
	function onButtonClick (button, name) {
		changeSelected (button, name);
	}
	
	function setSelected (name, value) {
		var button = $("[data-name='" + name + "']").find(" button[data-enumvalue='" + value + "']");
		changeSelected(button, name);
	}
	
	function changeSelected (newButton, name) {
		var selectionClass = getSelectionClass(name);
		$("[data-name='" + name + "'] button").removeClass("active "+getActiveVisibleClass(name)+
											" "+selectionClass).addClass(getNotSelectedClass(name));
		setActive(newButton, name);
	}
	
	function setActive (button, name) {
		var selectionClass = getSelectionClass(name);
		$(button).addClass(selectionClass+" active " + getActiveVisibleClass(name)).removeClass(getNotSelectedClass(name));
	}
	
	function getSelectedValue(name) {
		var selectionClass = getSelectionClass(name);
		var selected = $("[data-name='" + name + "']").find(" ." + selectionClass)[0];
		return $(selected).attr("data-enumvalue");
	}
	
	function getSelectionClass (name) {
		var customClass = $($("[data-name='" + name + "']")[0]).data("customselectionclass");
		return customClass ? customClass : "selected";
	}
	
	function getActiveVisibleClass (name) {
		var hasCustomClass = $($("[data-name='" + name + "']")[0]).data("customvisibleclass");
		if (!hasCustomClass)
			return "btn-secondary";
		return "";
	}
	
	function getNotSelectedClass (name) {
		var hasCustomClass = $($("[data-name='" + name + "']")[0]).data("customvisibleclass");
		if (!hasCustomClass)
			return "btn-default";
		return "";
	}
	
	function disableAll (container) {
		$("#"+container+" button").addClass("disabled");
	}

	return {
		create: create,
		getSelectedValue : getSelectedValue,
		setSelected : setSelected,
		disableAll : disableAll
	};
});