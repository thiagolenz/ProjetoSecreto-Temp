var requireConfig = {
		baseUrl: '.',
		paths: {
			i18n : "scripts/i18n",
			htmlLoader: "components/HtmlLoader"
		},
		locale : navigator.language.toLowerCase()
};

require.config(requireConfig);


define(["components/HtmlLoader",
        "i18n!modules/login/nls/Login.js"], function(htmlLoader, i18ns) {
	window.i18nLogin = i18ns;
	htmlLoader.load ({
		container: "#passwordChangeContainer",
		file: "modules/login/template/ChangePasswordForm.html",
		i18n : i18ns
	});
});

function validateSubmit () {
	var confirmPassword = $("#confirmPassword").val();
	var password = $("#password").val();
	if (confirmPassword != password) {
		$(getDivError()).html(window.i18nLogin.login.differentPasswords);
		$(password).focus();
		return false;
	}
	if ( password.trim() == "" || confirmPassword.trim() == "") {
		$(getDivError()).html(window.i18nLogin.login.informThePasswords);
		$(password).focus();
		return false;
	}
		
	return true;
}

function getDivError () {
	var div = $("#app-messages-container");
	$(div).addClass("alert alert-danger");
	return div;
}