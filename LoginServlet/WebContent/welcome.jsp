<%@include file="init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="head.jsp" %>
	<title>Welcome</title>
</head>
<body>

	<%
	User user = (User)request.getAttribute("user");
	if(user != null){
	%>
	<h1>User logged in:</h1>
	<div class="row">
		<div class="col-sm-1">
			<label>Name</label>
		</div>
		<div class="col-sm-1">
			<p><%= user.getUsername() %></p>
		</div>
	</div>
	<div class="row">
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