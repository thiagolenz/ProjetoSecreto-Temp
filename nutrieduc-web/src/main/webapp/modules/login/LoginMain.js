var requireConfig = {
		baseUrl: '.',
		paths: {
			i18n : "scripts/i18n",
			htmlLoader: "components/HtmlLoader"
		},
		locale : navigator.language.toLowerCase()
};

require.config(requireConfig);


define(["modules/login/LoginForm"], function(loginForm) {
	loginForm.load();
});