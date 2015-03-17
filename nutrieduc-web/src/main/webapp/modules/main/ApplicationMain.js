define (["components/ModuleEvent",
         "components/HtmlLoader",
         "moment",
         "i18n!staticpages/nls/Header",], 
function (moduleEvent, htmlLoader, moment, i18n) {
	function load (requireConfig, selectedModule) {
		$(document).ready(function() {
			moment.lang(requireConfig.locale);
			SessionInfo.getUserContext (function (user){
				configureMenuEvents();
				_this.user = user;
				if (user.account.accountType == "PATIENT") {
					$(".pageContainerSize").addClass("containerWide mainContainer");
					$(".pageContainerSize").removeClass("containerClinic");
				} else {
					$(".pageContainerSize").removeClass("containerWide");
					$(".pageContainerSize").addClass("containerClinic");
				} 
					
				if (selectedModule)
					moduleEvent.loadModule(selectedModule);
			}) ;
		}) ;
	}
	
	function configureMenuEvents () {
		htmlLoader.load({
			container: "#header",
			file: "staticpages/header.html",
			i18n: [i18n]
		}, onFinishLoadHeader);
	}
	
	function onFinishLoadHeader () {
		$(".welcome-name").html(_this.user.name)
		$.ajax({
			url : "menuItens",
			success : function (data) {
				for (var i = 0 ; i < data.menus.length; i++){
					var menu = data.menus[i];
					var dropDown = createMenu(menu, "#menuContainerLoading");
					if (dropDown)
						addMenuSubItem(menu, dropDown);
				}
				$(".moduleMenu").click(function (event){
					var module = $(event.target).data("module");
					moduleEvent.loadModule(module);
				});
			}
		});
//		moduleEvent.loadModule("chat");
	}
	
	function addMenuSubItem (menu, parent) {
		for (var i = 0 ; i < menu.subMenus.length; i++){
			var subMenu = menu.subMenus[i];
			var dropDown = createMenu(subMenu, parent);
			if (dropDown)
				addMenuSubItem(subMenu, dropDown);
		}
	}
	
	function createMenu (menu, parent) {
		var li = document.createElement("li");
		var link = document.createElement("a");
		$(li).append(link);
		link.href = "#";
		
		if (menu.subMenus.length > 0) {
			$(li).addClass("dropdown");
			$(link).addClass("dropdown-toggle");
			$(link).attr("data-toggle", "dropdown");
			var dropDown = document.createElement("ul");
			$(dropDown).addClass("dropdown-menu with-arrow pull-right");
			$(li).append(dropDown);
		} else {
			$(link).addClass("moduleMenu");
			$(link).attr("data-module", menu.moduleId);
		}

		$(link).html(i18n.header.menu[menu.menuLabel]);
		$(parent).append(li);
		return $(li).find("ul")[0];
	}
	
	var _this = {
		load : load 
	};
	
	return _this;
});