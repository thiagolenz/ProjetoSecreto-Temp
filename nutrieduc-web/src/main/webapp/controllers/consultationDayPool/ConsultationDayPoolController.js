define (["components/HtmlLoader",
         "i18n!modules/consultationDayPool/nls/ConsultationDayPool",
         "i18n!controllers/consultationDayPool/nls/ConsultationDayPoolManagement",
         "modules/consultationDayPool/ConsultationDayPoolGrid"
],
function (htmlLoader, i18ns, i18nManager, consultationDayPoolGrid){
	function load (args) {
		htmlLoader.load({
			container: "#body",
			file: "controllers/consultationDayPool/templates/ConsultationDayPoolLayout.html",
			i18n: [i18ns, i18nManager]
		}, function (){
			onFinishLoad(args);
		});
	}
	
	function onFinishLoad (args) {
		loadData();
	}
	
	function loadData () {
		consultationDayPoolGrid.create ("gridContainer", "gridDoneContainer");
		consultationDayPoolGrid.loadData();
	}

	var _this = {
		load : load 
	};
	
	return _this;
});