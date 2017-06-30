
<%@include file="init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="head.jsp" %>
	<title><%= LanguageUtil.get("login.head-title") %></title>
</head>
<body>
	<h1><%= LanguageUtil.get("login.title") %></h1>
	<%
	if(request.getAttribute("error") != null && request.getAttribute("error").equals("login")){
	%>
	<div class="bg-danger"><%= LanguageUtil.get("login.err-wrong-user-pass") %></div>
	<%
	}
	%>
	<form method="post" action="login">
			<span>
				<input type="text" name="login" placeholder="<%= LanguageUtil.get("login.name-placeholder") %>">
			</span>
			<span>
				<input type="password" name="pass" placeholder="<%= LanguageUtil.get("login.password-placeholder") %>">
			</span>
			<input type="submit" name="submit" value="<%= LanguageUtil.get("login.submit-button") %>">
	</form>
	<h3> Registration!</h3>
		<input type="button" name="Registration" value="<%= LanguageUtil.get("login.registration-button") %>"></input>
</body>
</html>