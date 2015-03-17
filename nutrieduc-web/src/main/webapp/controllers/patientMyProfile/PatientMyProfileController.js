define (["components/HtmlLoader"
  ],
function (htmlLoader){
	function load () {
		htmlLoader.load({
			container: "#body",
			file: "controllers/patientMyProfile/templates/PatientMyProfileLayout.html",
			i18n: [{}, {}]
		}, onFinishLoad);
	}
	
	function onFinishLoad () {
		
	}
	
	function bindEvents () {
		$("#btnNewAccount").click(openNewAccountModal);
	}

	var _this = {
		load : load
	};
	
	return _this;
});