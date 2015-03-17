define (["components/HtmlLoader",
         "i18n!controllers/consultationView/nls/ConsultationViewManagement",
         "modules/consultationView/ConsultationViewForm"
],
function (htmlLoader, i18nsViewManag, consultationViewForm){
	function load (args) {
		htmlLoader.load({
			container: "#body",
			file: "controllers/consultationView/templates/ConsultationViewLayout.html",
			i18n: [i18nsViewManag]
		}, function (){
			onFinishLoad(args);
		});
	}
	
	function onFinishLoad (args) {
		consultationViewForm.load ("#consultationContainer", args.consultation);
		bindEvents();
	}
	
	function bindEvents () {	
		$("#btnSearch").click (function () {
			alphabetFilter.clear ({container :  "#alphabetFilter"});
			var filter = {
				name : $("#nameFilter").val()
			};
			consultationViewGrid.loadData(filter, true);
		});
	}

	var _this = {
		load : load 
	};
	
	return _this;
});