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
<title>Welcome</title>
</head>
<body>

	<%
	User user = (User)request.getAttribute("user");
	if(user != null){
	%>
	<h1>User logged in:</h1>
	<div class="row-fluid">
		<div class="col-sm-1">
			<label>Name</label>
		</div>
		<div class="col-sm-1">
			<p><%= user.getUsername() %></p>
		</div>
	</div>
	<div class="row-fluid">
		<div class="col-sm-1">
			<label>Password</label>
		</div>
		<div class="col-sm-1">
			<p><%= user.getPassword() %></p>
		</div>
	</div>
	<%	
	} else{
	%>
	<a class="btn " href='<%= config.getServletContext().getContextPath() + "/login"  %>'>Login</a>	
	<%
	}
	%>
</body>
</html>