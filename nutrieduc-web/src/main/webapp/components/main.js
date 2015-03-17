var Language = {
		getLanguage : function () {
			var cookie = "language"; 
			var lang = Cookie.getCookie(cookie);
			if (lang)
				return lang.toLowerCase();
			else 
				return navigator.language.toLowerCase();
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
			return cookie [ Host.getHost()+ "_"+ name];
		},
		saveCookie : function (cookieName, value, expiry) {
			if (!expiry)
				expiry = new Date(new Date().getTime()  + (30 * 24 * 60 * 60 * 1000));
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
			bootstrap :  'scripts/bootstrap',
			datePicker : "scripts/bootstrap-datepicker",
			moment : "scripts/moment-with-langs",
			i18n : "scripts/i18n",
			treeGridJs : "scripts/jquery.treegrid",
			numeral : "scripts/numeral.min"
		},
		locale : Language.getLanguage()
};


var SessionInfo = {
		getUserContext : function (callback) {
			$.ajax({
				url : "sessionInfo",
				success : function (result) {
					callback (result);
				}
			});
		}
};

var AppConfig = {
		getConfig : function (callback) {
			$.ajax({
				url : "appConfig",
			}).done(callback);
		}
};

require.config(requireConfig);
var parts = document.URL.split("#!");
var selectedModule = parts[1];
//if (selectedModule == undefined && Cookie.getCookie(Host.getHost()+"_login") == "true")
//location.reload();
console.log(selectedModule);

define(["modules/main/ApplicationMain", 
        "bootstrap", 
        "datePicker", 
        "treeGridJs"], function(applicationMain) {
	require(["scripts/locales/bootstrap-datepicker.pt-BR",
	         "scripts/jquery.maskedinput",
	         "scripts/jquery.maskMoney",
	         "scripts/languages.min",
	         "scripts/bootstrap-tour.min"
	         ]);
	applicationMain.load(requireConfig, selectedModule);
});

var UtilInterface = {
	showWideBackground : function () {
		$("#normalBackground").fadeOut();
		$("#backgroundWide").fadeIn("slow");
		$("body").addClass("bodyWhite");
		$("#body").html("");
	},
	
	showNormalBackground : function () {
		$("#backgroundWide").fadeOut();
		$("#normalBackground").fadeIn(1600);
		$("#backgroundWide").html("");
		$("body").removeClass("bodyWhite");
	}
};

//define functions 

Array.prototype.remove = function(value) {
	var idx = this.indexOf(value);
	if (idx != -1) {
		return this.splice(idx, 1); // The second parameter is the number of elements to remove.
	}
	return false;
};

String.prototype.endsWith = function(suffix) {
	return this.indexOf(suffix, this.length - suffix.length) !== -1;
};


