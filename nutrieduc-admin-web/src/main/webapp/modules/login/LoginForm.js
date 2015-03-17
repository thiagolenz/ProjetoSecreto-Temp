define(["components/HtmlLoader", 
        "i18n!modules/login/nls/Login.js"], function (htmlLoader, i18ns){
	
	function load (){
		htmlLoader.load ({
			container: "#loginContainer",
			file: "modules/login/template/LoginForm.html",
			i18n : i18ns
		});
	}
	
	return {
		load : load
	}
});