<%@page
	import="com.redfire.nutrieduc.nutrieducweb.servlet.ContextPropertyReader"%>
<%@page import="java.util.Properties"%>
<%
	Properties properties = new Properties();
	String fileName = "socialMediaConfig_"
			+ System.getProperty("app.env") + ".properties";
	properties.load(ContextPropertyReader.class.getClassLoader()
			.getResourceAsStream(fileName));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:fb="http://www.facebook.com/2008/fbml" xml:lang="en" lang="en">
<head>
<script>
		var facebookAppId = '<%=properties.get("facebookAppId")%>
	';
</script>
<script type="text/javascript" src="scripts/jquery-2.1.0.js"></script>
<script type="text/javascript" src="modules/login/FacebookLogin.js"></script>
<script type="text/javascript" src="modules/login/GoogleLogin.js"></script>
<script src="https://plus.google.com/js/client:plusone.js"></script>

<script data-main="modules/login/LoginMain" src="scripts/require.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>NutriEduc - Login</title>

<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

<link rel="icon" href="images/favicons/TodosTamanhos/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="152x152"
	href="images/favicons/TodosTamanhos/favicon-152.png?v=2">
<link rel="apple-touch-icon-precomposed" sizes="120x120"
	href="images/favicons/TodosTamanhos/favicon-120.png?v=2">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="images/favicons/TodosTamanhos/favicon-72.png?v=2">
<link rel="apple-touch-icon-precomposed"
	href="images/favicons/TodosTamanhos/favicon-57.png?v=2">
<link
	href="http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic"
	rel="stylesheet" type="text/css">
<!-- needs images, font... therefore can not be part of ui.css -->
<link rel="stylesheet"
	href="bower_components/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="bower_components/weather-icons/css/weather-icons.min.css">
<!-- end needs images -->

<link href="https://fonts.googleapis.com/css?family=Roboto"
	rel="stylesheet" type="text/css">
<script
	src="https://apis.google.com/js/client:platform.js?onload=render" async
	defer></script>
<link rel="stylesheet" href="styles/ui.css" />
<link rel="stylesheet" href="styles/main.css">
<link rel="stylesheet" href="css/public.css">
<link rel="stylesheet" href="css/custom.css" />
<link rel="stylesheet" href="css/bootstrap-social.css" />
<link rel="stylesheet" href="css/font-awesome.css" />
</head>

<body>
	<!-- Navigation Ends -->

	<!-- Main Container -->

	<style type="text/css">
#customBtn {
	display: inline-block;
	background: #dd4b39;
	color: white;
	border-radius: 3px;
	white-space: nowrap;
}

#customBtn:hover {
	background: #e74b37;
	cursor: hand;
}

span.label {
	font-weight: bold;
}

span.icon {
	background: url('images/public/google-plus-icon.png') transparent 0px
		90% no-repeat;
	display: inline-block;
	vertical-align: middle;
	width: 40px;
	height: 40px;
	cursor: pointer;
}

span.buttonText {
	display: none;
	vertical-align: middle;
	padding-left: 35px;
	padding-right: 35px;
	font-size: 14px;
	font-weight: bold;
	/* Use the Roboto font that is loaded in the <head> */
	font-family: 'Roboto', arial, sans-serif;
	vertical-align: middle;
}
</style>
	<div class="container" align="center">
		<img src="images/public/logo-nutrieduc-public.png" align="center"></img>
		<div id="loginContainer"></div>

		<div class="box">
			<div class="center span4">
				<form id="formLogin" method="post" action="dologin">
					<input type="hidden" id="accessToken" name="accessToken" /> <input
						type="hidden" id="socialMediaProvider" name="socialMediaProvider" />
				</form>

				<%
					if (request.getSession().getAttribute("error") != null) {
				%>
				<div id="app-messages-container" class="custom-error margin-top-10"><%=request.getSession().getAttribute("error")%></div>
				<%
					} else {
				%>
				<div id="app-messages-container" class="custom-error margin-bottom-10"></div>
				<%
					}
				%>
				<form method="post" action="dologin" accept-charset="UTF-8"
					id="formDoLogin">

					<div class="">
						<input type="text" id="email" class="form-control field-40"
							name="email" placeholder="user@email.com" />
					</div>
					<div class="">
						<input type="password" id="password" class="form-control field-40"
							name="password" placeholder="Password" />
					</div>
				</form>
				<div class="row row-login">
					<div class="col-md-5">
						<button type="submit" name="submit" id="btnSubmit"
							class="btn btn-primary button-confirm">Entrar</button>
					</div>
					<div class="col-md-1 text-or">Ou</div>
					<div class="col-md-5">
						<a
							class="btn btn-social-icon btn-facebook custom-button-size no-border margin-left-30"
							id="facebookLoginBtn"><i class="fa fa-facebook margin-top-5 "></i></a>
						<div id="customBtn"
							class="customGPlusSignIn custom-button-size no-border">
							<span class="icon"></span>
						</div>
						<!-- 	<a class="btn btn-social-icon btn-google-plus hide" id="googleLoginBtn"><i
							class="fa fa-google-plus"></i></a> -->
					</div>
				</div>

			</div>
			<section>
			<div class="row row-links-login text-left">
				<div class="col-md-6">
					<a href="createaccount?redirect">Cadastre-se</a>
				</div>
				<div class="col-md-6 text-forgot-password ">
					<a href="forgotpassword?redirect">Esqueceu a Senha?</a>
				</div>
			</div>
			</section>
		</div>

	</div>
	</section>

	<script>
  function render() {
    gapi.signin.render('customBtn', {
      'callback': 'onSignInCallback',
      'clientid': '613071859333-8iubrl5od5gvvrd9k0e6on4ebog8gokj.apps.googleusercontent.com',
      'cookiepolicy': 'single_host_origin',
      'requestvisibleactions': 'http://schema.org/AddAction',
      'scope': 'https://www.googleapis.com/auth/plus.login'
    });
  };
  </script>
</body>
</html>