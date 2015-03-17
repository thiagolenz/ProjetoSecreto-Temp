define (["components/HtmlLoader",
         "components/AreaReport"
  ],
function (htmlLoader, areaReport){
	function load () {
		htmlLoader.load({
			container: "#body",
			file: "controllers/dashboard/templates/DashboardLayout.html",
			i18n: [{}, {}]
		}, onFinishLoad);
	}
	
	function onFinishLoad () {
		areaReport.create("reportFinancial");
		//bindEvents();
	}
	
	function bindEvents () {
		$("#btnNewAccount").click(openNewAccountModal);
	}

	var _this = {
		load : load
	};
	
	return _this;
});