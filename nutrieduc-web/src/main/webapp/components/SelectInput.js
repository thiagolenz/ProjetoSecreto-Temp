define ([], 
function () {
	
	function create (config) {
		var container = createContainer();
		container.id = "selectInput_" + new Date().getTime ();
		var buttonDrop = createButtonDrop(config);
		$(container).append(buttonDrop);
		var menuDrop = createMenuDrop();
		appendItens(menuDrop, config, container.id);
		$(container).append(menuDrop);
		setCurrent(config.defaultObject.enumValue, container);
		if (config.container)
			$(config.container).html(container);
		else 
			return container;
	}
	
	function createContainer () {
		var divDrop = document.createElement("div");
		$(divDrop).addClass("dropdown");
		return divDrop;
	}
	
	function createButtonDrop (config) {
		var buttonDrop = document.createElement("button");
		$(buttonDrop).attr('tabindex', -1);
		$(buttonDrop).addClass("btn dropdown-toggle "+ (config.className ? config.className : ""));
		$(buttonDrop).attr("data-toggle", "dropdown");
		var span = document.createElement("span");
		$(span).addClass("glyphicon glyphicon-chevron-down margin-left-10");
		$(buttonDrop).append(span);
		return buttonDrop;
	}
	
	function createMenuDrop () {
		var menu = document.createElement("ul");
		$(menu).addClass("dropdown-menu");
		return menu;
	}
	
	function appendItens (menuDrop, config, container) {
		createAndAppend (menuDrop, config.defaultObject, container, config);
		for (var i = 0; i < config.data.length; i++) {
			var obj = config.data[i];
			createAndAppend(menuDrop, obj, container, config);
		}
	}
	
	function createAndAppend (menuDrop, obj, container, config) {
		var menuItem = createMenuItem(obj, container, config);
		$(menuDrop).append (menuItem);
	}
	
	function createMenuItem (obj, container, config) {
		var li = document.createElement("li");
		var a = document.createElement("a");
		a.href = "javascript:;";
		a.role = "menuitem";
		a.tabindex = -1;
		$(a).html(obj.description);
		$(a).attr("data-enumvalue", obj.enumValue);
		$(a).attr("data-container", container);
		$(a).click(function (event) {
			var value = onClick(event);
			if ( config.onChange)
				config.onChange (value);
		});
		$(li).append(a);
		return li;
	}
	
	function onClick (event) {
		var enumValue = $(event.target).data("enumvalue");
		var container = $(event.target).data("container");
		setCurrent(enumValue, "#" + container);
		return enumValue;
	}
	
	function setCurrent (enumValue, container) {
		var btn = $(container).find("button");
		var span = $(btn).find("span");
		var option = $(container).find("a[data-enumvalue='" + enumValue +"']");
		$(btn).attr("data-enumvalue", enumValue);
		$(btn).html(option.html());
		$(btn).append(span);
	}

	var _this = {
		create : create	
	};
	
	return _this;
});