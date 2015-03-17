define (["components/HtmlLoader",
         "i18n!controllers/consultationView/nls/ConsultationViewManagement",
         "modules/consultationView/ConsultationViewForm"
],
function (htmlLoader, i18nsViewManag, consultationViewForm){
	function load (args) {
		$("#body").html();
	}

	return {
		load : load 
	};
});