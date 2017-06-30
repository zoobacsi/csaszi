<%@include file="init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	 --><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
	
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<title>Login</title>
</head>
<body>
	<h1> Log in! </h1>
	<%
	if(request.getAttribute("error") != null && request.getAttribute("error").equals("login")){
	%>
	<div class="bg-danger">Wrong username / password!</div>
	<%
	}
	%>
	<form method="post" action="login">
			<span>
				<input type="text" name="login" placeholder="name">
			</span>
			<span>
				<input type="text" name="pass" placeholder="password">
			</span>
			<input type="submit" name="submit" value="Login">
	</form>
	<h3> Registration!</h3>
		<input type="button" name="Registration" value="Sign up"></input>
</body>
</html>