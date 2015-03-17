define(["components/HtmlLoader", 
        "i18n!modules/login/nls/Login.js",
        "i18n!components/nls/Generic",
        "components/Service",
        "components/Messages"], 
function (htmlLoader, i18ns, i18ns_g, service, messages){

	function load () {
		htmlLoader.load ({
			container: "#loginContainer",
			file: "modules/login/template/LoginForm.html",
			i18n : i18ns
		}, function (){
			bindEvents();
		});
	}

	function bindEvents () {
		$("#facebookLoginBtn").click(facebookLogin);
		$("#googleLoginBtn").click(googleLogin);
		$("#btnSubmit").click (doSubmit);
		$("#formDoLogin input").keyup (function (event){
			if (event.keyCode == 13)
				doSubmit();
		});
	}
	
	function doSubmit () {
		$("#formDoLogin").submit();
	}
	
	function googleLogin () {
		var response = window.GOOGLE_RESPONSE;
		if (!response) {
			$("#gConnect").click();
			return;
		}
		var user = {
				name : response.displayName,
				login : response.email,
				accessToken : response.id,
				socialMediaProvider : "GOOGLE",
		};

		var userSocialMedia = {
				socialMediaProvider : "GOOGLE",
				user : {
					login : response.email
				}
		};
		processCreateUser(userSocialMedia, user);
	}

	function facebookAPILogin(){
		FB.login(function(response) {
			if (response.authResponse) {
				testAPI(function (){
					facebookLogin();
				});
			} else
			{
				console.log('Authorization failed.');
			}
		},{scope: 'email'});

	}

	function facebookLogin () {
		if (!window.FACEBOOK_ME_RESPONSE && !window.FACEBOOK_RESPONSE){
			facebookAPILogin();
			return;
		}
		var detailedResponse = window.FACEBOOK_ME_RESPONSE;
		var user = {
				name : detailedResponse.name,
				login : detailedResponse.email,
				accessToken : detailedResponse.id,
				socialMediaProvider : "FACEBOOK",
		};

		var userSocialMedia = {
				socialMediaProvider : "FACEBOOK",
				user : {
					login : detailedResponse.email
				}
		};
		processCreateUser(userSocialMedia, user);
	}

	function processCreateUser (userSocialMedia, user) {
		var headers = {
				"rf-public-request" : true
		};
		service.post({
			url : "userProfile/userSocialMedia/findExistentByLogin",
			data : userSocialMedia,
			headers : headers,
			error : function (data) {
				console.log(data);
			},
			success : function (data) {
				if (data.user)
					doSocialMediaLogin(user);
				else 
					service.post({
						url : "userProfile/userSocialMedia/createUser",
						data : user,
						headers : headers,
						error : error,
						success : function (data) {
							doSocialMediaLogin(user);
						}
					});
			}
		});
	}
	
	function error (data) {
		messages.error({
			title: i18ns_g.error,
			message: data
		});
	}

	function doSocialMediaLogin (user) {
		$("#accessToken").val(user.accessToken);
		$("#socialMediaProvider").val(user.socialMediaProvider);
		$("#formLogin").submit();
	}

	return {
		load : load
	};
});