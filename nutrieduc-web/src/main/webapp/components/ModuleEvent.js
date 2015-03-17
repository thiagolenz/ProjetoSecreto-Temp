define (["controllers/mapping/ModulesMapping"], 
function (modulesMapping){
	
	function loadModule (moduleId, args) {
		console.log("loading: "+ moduleId);
		
		if ( modulesMapping[moduleId] == undefined) {
			console.error ("module not found : "+ moduleId);
			return;
		}
		
		var moduleConfig = modulesMapping[moduleId];
		requireAndLoadModule(moduleConfig, moduleId, args);
	}
	
	function requireAndLoadModule (moduleConfig, moduleId, args) {
		require([moduleConfig.path], function (module){
			module.load(args);
			if (moduleConfig.loadInURL) {
				appendModuleInUrl(moduleId);
				saveModuleCookie(moduleId);
				showSelectedMenu(moduleId);
			}
			console.log("module loaded:"+ moduleId);
		});
	}
	
	function appendModuleInUrl (moduleId) {
		var urlParts = document.URL.split("/#");
		var lastPart = "#!" + moduleId;
		if (!urlParts[0].endsWith("/"))
			lastPart = "/" + lastPart;
		history.pushState('', '', urlParts[0] + lastPart);
	}
	
	function showSelectedMenu (moduleId) {
		$(".active-menu").removeClass("active-menu");
		var menuModule = $("#menuContainerLoading").find("[data-module='"+moduleId+"']")[0];
		if (menuModule) {
			var dropDown = $(menuModule).closest ('.dropdown')[0];
			if (dropDown)
				menuModule = $(dropDown).find("a");
			$(menuModule).addClass("active-menu");
		}
	}
	
	function saveModuleCookie (moduleId) {
		var expiry = new Date(new Date().getTime()  + (30 * 24 * 60 * 60 * 1000));
		Cookie.saveCookie ("currentModule", moduleId, expiry);
	}
	
	return {
		loadModule: loadModule
	};
});