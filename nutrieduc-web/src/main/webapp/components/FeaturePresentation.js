define (["components/HtmlLoader",
         "i18n!components/nls/Generic"], 
function (htmlLoader, i18ns_g){
	function open (config) {
		var container = $("#dialogContainer");
		if (!container [0]){
			container = document.createElement("div");
			container.id = "dialogContainer";
			$("body").append(container)
		}
		htmlLoader.load({
			container: "#dialogContainer",
			file: "components/templates/FeaturePresentation.html",
			i18n: [{}, i18ns_g]
		}, function () {
			onFinishLoad(config);
		});
	}
	
	function onFinishLoad (config) {
		$("#modalFeaturePresentation").modal();
	}
	
	return {
		open : open
	};
});