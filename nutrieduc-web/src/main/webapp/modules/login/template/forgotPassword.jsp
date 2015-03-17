<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:fb="http://www.facebook.com/2008/fbml" xml:lang="en" lang="en">
<head>
<script type="text/javascript" src="scripts/jquery-2.1.0.js"></script>
 
<script data-main="modules/login/ForgotPasswordMain" src="scripts/require.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>NutriEduc - Create Account</title>

<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
<link
	href="http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic"
	rel="stylesheet" type="text/css">
<!-- needs images, font... therefore can not be part of ui.css -->
<link rel="stylesheet"
	href="bower_components/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="bower_components/weather-icons/css/weather-icons.min.css">
<!-- end needs images -->

<link rel="stylesheet" href="styles/ui.css" />
<link rel="stylesheet" href="styles/main.css">
<link rel="stylesheet" href="css/custom.css" />
<link rel="stylesheet" href="css/bootstrap-social.css" />
<link rel="stylesheet" href="css/font-awesome.css" />
<link rel="stylesheet" href="css/public.css">
</head>

<body>

	<div class="container" align="center">
	<img src="images/public/logo-nutrieduc-public.png" align="center"></img>
		<%
			if (request.getSession().getAttribute("error") != null) {
		%>
		<div class="alert alert-danger"><%=request.getSession().getAttribute("error")%></div>
		<%
			}
		%>
		<div id="app-messages-container"></div>

		<div id="createAccountContainer"></div>
	</div>
 
</body>
</html>