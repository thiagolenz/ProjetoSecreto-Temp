<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>NutriEduc Admin Login</title>
<meta name="viewport" content="width=device-width,initial-scale=1" />

<!-- StyleSheet -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen" />
<link href="css/custom.css" rel="stylesheet" media="screen" />
<link href="css/datepicker.css" rel="stylesheet" media="screen" />
<link href="css/offcanvas.css" rel="stylesheet" media="screen" />
<script type="text/javascript" src="scripts/jquery-2.1.0.js"></script>
<script data-main="modules/login/LoginMain" src="scripts/require.js"></script>
</head>

<body>
	<!-- Navigation Bar -->


	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">NutriEduc Admin</a>
			</div>
		</div>
	</div>
	<!-- Navigation Ends -->

	<!-- Main Container -->
	<section>

	<div class="container" align="center">
		<%
			if (request.getSession().getAttribute("error") != null) {
		%>
			<div class="alert alert-danger"><%=request.getSession().getAttribute("error")%></div>
		<%
			}
		%>
		<div id="loginContainer"></div>
	</div>
	<p class="text-center muted ">&copy; Copyright 2014 - NutriEduc</p>
	</section>

</body>
</html>