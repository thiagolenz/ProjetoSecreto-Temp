define (["components/AutoCompleteHtml", 
         "components/Service"], function (autoCompleteHtml, service){
	var TABKEY = 9;
	var ESCAPE = 27;
	var ENTER = 13;
	var DOWN = 40;
	var UP = 38;
	
	var selectedBeans = {};
	var loadedData = {};
	var configs = {};
	
	function create (config) {
		config.onRemoveTag = function (config) {
			setValue(getDataName(config), undefined);
		};
		if (typeof config.container === "string")
			config.container = "#" + config.container;
		autoCompleteHtml.createAutoCompleteComponent(config);
		bindEvents(config);
		configs [$(config.container).attr("data-name")] = config;
		return $(config.container);
	}
	
	function bindEvents (config) {
		$(config.container).find("input").keyup (function (event) {
			onKeyupEvent(event, config);
		});
		$(config.container).find(".tagsinput").click (function (event) {
			$(config.container).find("input").focus();
		});
		$(config.container).find("input").on( 'focusin', function( event ) {
			autoCompleteHtml.focus(config);
		});
		
		$(config.container).find("input").on( 'focusout', function( event ) {
			autoCompleteHtml.removeFocus(config);
		});
	}
	
	function onKeyupEvent (event, config) {
		if (event.keyCode != TABKEY) {
			if (event.keyCode == ESCAPE)  
				processEscapeKeyEvent(event, config);
			else if (event.keyCode == DOWN )  
				processDownEvent(event, config);
			else if (event.keyCode == ENTER) 
				processEnterEvent(event, config);
			else 
				loadData(config, event.target.value);
		}
	}
	
	function processEscapeKeyEvent (event, config) {
		autoCompleteHtml.hideDropDown(config);
		var selectedValue = $(config.container).attr("data-selectedvalue");
		if (selectedValue) 
			setShowDisplayValue(config, selectedValue);
		else 
			setShowDisplayValue(config, "");
	}
	
	function processDownEvent (event, config) {
		autoCompleteHtml.showDropDown(config);
		autoCompleteHtml.removeCurrentTag(config);
		var a = $(config.container).find("ul > li > a ")[0];
		if (a == undefined)
			loadData (config, "");
		else 
			$(a).focus();
	}
	
	function loadData (config, value, isPagination) {
		if (!isPagination)
			_this.currentPage = 0;
		var  data = defineData(config, value);
		service.post ({
			url : config.url,
			data : data, 
			skipLoading: true,
			headers : getSearchHeaders (config),
			success : function (result) {
				_this.eventDone = false;
				if (!isPagination)
					autoCompleteHtml.fillAndShowDropDown(config, result, value);
				else 
					autoCompleteHtml.fillDropDown (config, result, false);
				bindEventOnChoose (config);
				bindPagination (config, value);
				saveLoadedData (config, result, isPagination);
				hideShowMoreIfNeed (config, result);
			}
		});
	}
	
	function defineData (config, value) {
		var  data = {};
		data [config.varSearch] = value;
		if (config.beforeSearch)
			config.beforeSearch(data);
		return data;
	}
	
	function saveLoadedData (config, data, isPagination) {
		if (!isPagination)
			loadedData[config.container] = {};
		for (var i = 0 ; i < data.dataList.length; i++) {
			var row = data.dataList[i];
			loadedData[config.container] [row.id] = row;
		}
	}
	
	function getSearchHeaders (config) {
		return {
			"bd-pagination-currentpage": _this.currentPage,
			"bd-pagination-recordsrange" : 7
		};
	}
	
	function bindEventOnChoose (config) {
		$(config.container).find(".dropdown-menu a").unbind("click").click (function (event) {
			_this.eventDone = true;
			event.preventDefault();
			if ($(event.target).parent().hasClass("elementNew")) {
				createNewRecord($(event.target).html(), config);
			} else if ($(event.target).hasClass("elementNew")) {
				createNewRecord($(event.target).find("strong").html(), config);
			} else { 
				onChooseElement(config, event);
			}
			config.eventDone = true;
		});
	}
	
	function bindPagination (config, value) {
		$(config.container).find(".elementShowMore a").unbind().click (function (){
			event.preventDefault();
			if (_this.currentPage == 0 )
				_this.currentPage = 1;
			else if (_this.currentPage == 1)
				_this.currentPage = 2;
			loadData(config, value, true);
		});
	}
	
	function hideShowMoreIfNeed (config, result) {
		if (_this.currentPage == 2 || (_this.currentPage == 1 && getObjectCount(config) == result.totalRecordsCount) ) {
			autoCompleteHtml.removeShowMore(config);
			$(config.container).find("a").focus();
		}
	}
	
	function getObjectCount (config) {
		var count = 0;
		for (var i in loadedData[config.container]) 
		      ++count;
		return count;
	}
	
	function processEnterEvent (event, config) {
		if (!_this.eventDone && ($(event.target).hasClass("elementNew") || $(event.target).hasClass("autocomplete-input")))
			createNewRecord(event.target.value, config);
	}
	
	function createNewRecord (value, config) {
		var object = {};
		object [config.varSearch] = value;
		if ( config.preSaveNewObject)
			config.preSaveNewObject (object);
		service.post ({
			url : config.createUrl,
			data : object,
			success : function (data) {
				setValue(getDataName(config), data, undefined);
				autoCompleteHtml.hideDropDown(config);
			}
		});
	}
	
	function onChooseElement (config, event) {
		var id = $(event.target).attr("data-id");
		var obj = loadedData[config.container][id];
		var value = defineDisplaySelectedValue (config, event, obj);
		
		setShowDisplayValue(config, value);
		autoCompleteHtml.resizeInput(config);
		autoCompleteHtml.hideDropDown (config);
		$(config.container).attr("data-selectedvalue", event.target.text);
		
		if (config.onChange)
			config.onChange (obj);

		selectedBeans [getDataName(config)] = obj;
		
		findNextElementToFocus(config);
	}
	
	function defineDisplaySelectedValue (config, event, obj) {
		var value = event.target.text;
		if (typeof config.varDisplay == 'function' )
			value = config.varDisplay (obj);
		else if (config.varDisplay)
			value = obj [config.varDisplay];
		return value;
	} 
	
	function getDataName (config) {
		return $(config.container).attr("data-name");
	}
	
	function findNextElementToFocus (config) {	
		var tabables = $("input, button, textarea[tabindex != '-1']:visible");
		var current = $(config.container).find("input");
		var currentIndex = tabables.index(current);
		$(tabables[currentIndex+1]).focus();
	}
	
	function setValue (name, bean, component) {
		selectedBeans[name] = bean;
		if (bean) {
			var element = component;
			if (!element)
				element = $("[data-name='"+ name +"']")[0];
			var value = bean[$(element).attr("data-varDisplay")];
			setShowDisplayValue(configs[name], value);
			autoCompleteHtml.resizeInput(configs[name]);
		} else 
			autoCompleteHtml.removeCurrentTagByName(name);
	}
	
	function setShowDisplayValue (config, value) {
		$(config.container).find("input").val("");
		if (value) {		
			var tag = autoCompleteHtml.createBoxSelected(config, value);
			$(config.container).find(".tagsinput").prepend(tag);
			//$("#"+ config.container + " input").attr("placeholder", "");
		}// else 
			//$("#"+ config.container + " input").attr("placeholder", "Digite um valor");
	}

	function getValue (name) {
		return selectedBeans[name];
	}
	
	function cleanSelected() {
		selectedBeans = {};
	}
	
	function disable (config) {
		autoCompleteHtml.disable (config);
	}
	
	function enable (config) {
		autoCompleteHtml.enable (config);
	}
	
	var _this =  {
		create : create,
		getValue : getValue,
		setValue : setValue,
		cleanSelected : cleanSelected,
		disable : disable,
		enable : enable
	};
	
	return _this;
});