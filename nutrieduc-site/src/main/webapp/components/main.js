var Language = {
		getLanguage : function () {
			var cookie = Host.getHost() + "_language"; 
			var lang = Cookie.getCookie(cookie);
			if (lang)
				return lang.toLowerCase();
			else {
				var param = Language.getQueryVariable("lang");
				if (param)
					return param.toLowerCase ();
			}
			return navigator.language.toLowerCase();
		},
		getQueryVariable : function (variable) {
			var query = window.location.search.substring(1);
			var vars = query.split("&");
			for (var i=0;i<vars.length;i++) {
			   var pair = vars[i].split("=");
			   if (pair[0] == variable) {
			      return pair[1];
			   }
			} 
		}
};

var Cookie = {
		getCookie : function (name) {
			var cookie = {};
			var ca = document.cookie.split(';');
			for(var i=0; i<ca.length; i++) {
				var c = ca[i].trim();
				var parts = c.split ("=");
				cookie[parts[0]] = parts[1];
			}
			return cookie [name];
		},
		saveCookie : function (cookieName, value, expiry) {
			var expires = "; expires=" + expiry.toGMTString();
			var cookieConfig = Host.getHost()+ "_"+ cookieName + "=" + value + expires+ "; path=/";
			document.cookie=cookieConfig;
		}
};

var Host = {
		getHost : function () {
			var path = document.location.pathname;
			if (path != "/") {
				path = "_" + path.substring(1, path.length -1).replace(/\-/g,'_');
				return document.location.hostname +  path;
			} else 
				return document.location.hostname;
		}
};

var requireConfig = {
		baseUrl: '.',
		paths: {
			i18n : "js/i18n"
		},
		locale : Language.getLanguage()
};

require.config(requireConfig);

define(["components/Translator"], function(translator) {
	translator.tranlateAll ();
});


