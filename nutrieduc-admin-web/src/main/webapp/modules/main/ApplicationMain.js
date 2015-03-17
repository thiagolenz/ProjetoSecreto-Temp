define (["components/ModuleEvent", "moment"], function (moduleEvent, moment) {
	function load (requireConfig, selectedModule) {
		$(document).ready(function() {
			moment.lang(requireConfig.locale);
			configureMenuEvents();
			if (selectedModule)
				moduleEvent.loadModule(selectedModule);
		}) 
	}
	
	function configureMenuEvents () {
		$("#header").load("staticpages/header.html", function (){
			$(".moduleMenu").click(function (event){
				var module = $(event.target).data("module");
				moduleEvent.loadModule(module);
			});
		});
	}
	
	return {
		load : load 
	};
});