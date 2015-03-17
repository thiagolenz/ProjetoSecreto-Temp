define ([], function () {
	function createAutoCompleteComponent (config) {
		var completeDiv = document.createElement ("div");
		$(completeDiv).addClass ("tagsinput");
		
		var input = document.createElement("input");
		$(input).addClass("autocomplete-input");
		if (config.noFocus)
			$(input).attr('tabindex', -1);
		$(completeDiv).append (input);
	
		$(config.container).append (completeDiv);
		$(config.container).addClass ( "dropdown");
		$(config.container).attr ( "data-varDisplay", config.varDisplay);
		
		createDropDown(config);
	}
	
	function fillAndShowDropDown (config, result, value) {
		showDropDown(config);
		removeCurrentTag(config);
		
		fillDropDown(config, result, true);
		
		if (config.canCreate == undefined)
			config.canCreate = true;
		
		if (config.canCreate) {
			if (value.trim() != "") {
				createOptionNewOnDropDown(config, value);
				if (result.dataList.length == 0)
					removeDivider(config, "dividerNew");
			} else 
				removeCreateNew (config);
		}
	}
	
	function fillDropDown (config, result, reset) {
		var dropDown = findDropDown(config);
		var divider = undefined;
		if (reset)
			$(dropDown).html("");
		else 
			divider = $(config.container).find(".dividerShowMore");
		for (var i = 0; i < result.dataList.length; i++) {
			var item = document.createElement("li");
			$(item).attr ("role","presentation");
			var linkable = createLinkableElement(config, result, i);
			$(item).append(linkable);
			if (reset)
				$(dropDown).append (item);
			else 
				$(item).insertBefore(divider);
		}
		
		if (result.totalRecordsCount > result.dataList.length && !divider) {
			createDivider(dropDown, true, "dividerShowMore");	
			createShowMoreRowsLink (config, dropDown);
		} 
	}
	
	function createOptionNewOnDropDown (config, value) {
		var item = $(config.container).find(".elementNew")[0];
		var dropDown = $(config.container).find("ul");
		if (item == undefined) {
			createDivider(dropDown, false, "dividerNew");
			item = createLinkCreateNew(dropDown);
		} else {
			$(item).html("");			
		}
		$(item).append ("Criar novo ");
		var strong = document.createElement("strong");
		$(strong).html(value);
		$(item).append(strong);
	}
	
	function createDivider (dropdown, append, extraClass) {
		var divider = document.createElement("li");
		$(divider).addClass("divider " + extraClass);
		$(divider).css("margin", "0px");
		$(divider).attr ("tabindex", "-1");
		if (append)
			$(dropdown).append(divider);
		else
			$(dropdown).prepend(divider);
	}
	
	function createShowMoreRowsLink (config, dropdown){
		var li = document.createElement ("li");
		$(li).addClass("elementShowMore");
		$(dropdown).append(li);
		item = document.createElement ("a");
		$(item).addClass("autocomplete-item");
		$(item).attr ("tabindex", "-1");
		$(item).attr ("role", "menuitem");
		var span = document.createElement("strong");
		$(span).html("Mostrar mais ...");
		$(item).append(span);
		item.href = "#";
		$(li).append(item);
		return item;
	}
	
	function removeShowMore (config) {
		$(config.container).find(".elementShowMore").remove();
		removeDivider(config, "dividerShowMore");
	}
	
	function findDropDown (config) {
		return $(config.container).find("ul");
	}
	
	function removeCreateNew (config) {
		$(config.container).find(".elementNew").remove();
		removeDivider(config, "dividerNew");
	}
	
	function removeDivider (config, className) {
		$(config.container).find("." + className).remove();
	}
	
	function createLinkCreateNew (dropdown) {
		var li = document.createElement ("li");
		$(dropdown).prepend(li);
		item = document.createElement ("a");
		$(item).addClass("elementNew");
		$(item).addClass("autocomplete-item");
		$(item).attr ("tabindex", "-1");
		$(item).attr ("role", "menuitem");
		item.href = "#";
		$(li).append(item);
		return item;
	}
	
	function createDropDown (config) {
		var dropDown = document.createElement ("ul");
		$(dropDown).addClass("dropdown-menu");
		$(dropDown).attr ("role", "menu");		
		$(config.container).append (dropDown);
	} 
	
	function createLinkableElement (config, result, index) {
		var a = document.createElement ("a");
		var obj = result.dataList[index];
		var displayValue = "";
		if (config.displayTemplate)
			displayValue = config.displayTemplate(obj);
		else 
			displayValue = obj [config.varDisplay];
		$(a).html(displayValue);
		$(a).addClass("autocomplete-item");
		$(a).attr ("tabindex", "-1");
		$(a).attr ("role", "menuitem");
		$(a).attr ("data-id", obj.id);
		a.href = "#";
		return a;
	}
	
	function createBoxSelected (config, value) {
		var spanTag = createSelectedSpanTag(config);
		
		var spanText = createSelectedSpanText(value);
		$(spanTag).append (spanText);
		
		var remove = createSelectedRemoveButton(config);
		$(spanTag).append (remove);

		return spanTag;
	}
	
	function createTagsInputIfNotExist () {
		
	}
	
	function createSelectedSpanTag (config) {
		var spanTag = document.createElement ("span");
		$(spanTag).addClass("tag");
		return spanTag;
	}
	
	function createSelectedSpanText (value) {
		var spanText = document.createElement ("span");
		$(spanText).html ( value);
		return spanText;
	}
	
	function createSelectedRemoveButton (config) {
		var remove = document.createElement ("button"); 
		$(remove).html ("x");
		$(remove).addClass("close");
		$(remove).attr('tabindex', -1);
		$(remove).click (function () {
			removeCurrentTag (config);
			if (config.onChange)
				config.onChange (undefined);
		});
		return remove;
	}
	
	function disable (config) {
		enableDisable(config, true);
	}
	
	function enable (config) {
		enableDisable(config, false);
	}
	
	function enableDisable (config, flag) {
		$(config.container).find(".tagsinput").attr('disabled', flag);
		$(config.container).find(".close").attr('disabled', flag);
		$(config.container).find("input").attr('disabled', flag);
		if (flag)
			$(config.container).parent().addClass("disabled");
		else 
			$(config.container).parent().removeClass("disabled");
	}
	
	function removeCurrentTag (config)  {
		config.onRemoveTag(config);
		$(config.container).find(".tag").remove();
	}
	
	function removeCurrentTagByName (name)  {
		$("[data-name='"+ name +"'] .tag").remove();
	}
	
	function showDropDown (config) {
		$(config.container).addClass("open");
	}
	
	function hideDropDown (config) {
		$(config.container).removeClass("open");
	}
	
	function focus (config) {
		$(config.container).find(".tagsinput").addClass("focus");
	}
	
	function removeFocus (config) {
		$(config.container).find(".tagsinput ").removeClass("focus");
	}
	
	function resizeInput (config) {
		var sizeInput = 0;
		var containerWidth = $(config.container).width ();
		if (containerWidth > 0) {
			var spanTag = $(config.container).find(".tag");
			if (spanTag) 
				sizeInput = containerWidth - $(spanTag).width() - 45;
			else 
				sizeInput = containerWidth;
		} else {
			sizeInput = 70;
		}
		if (sizeInput < 30) 
			sizeInput = 30;
		
		$(config.container).find ("input").css("width", sizeInput);
	}


	return {
		createAutoCompleteComponent : createAutoCompleteComponent, 
		createBoxSelected : createBoxSelected, 
		removeCurrentTag : removeCurrentTag,
		showDropDown : showDropDown,
		hideDropDown : hideDropDown,
		createOptionNewOnDropDown : createOptionNewOnDropDown,
		fillAndShowDropDown : fillAndShowDropDown,
		fillDropDown : fillDropDown,
		createDropDown : createDropDown,
		focus : focus,
		removeFocus : removeFocus,
		removeCurrentTagByName : removeCurrentTagByName, 
		disable: disable,
		enable : enable,
		removeShowMore : removeShowMore,
		resizeInput : resizeInput
	};
});