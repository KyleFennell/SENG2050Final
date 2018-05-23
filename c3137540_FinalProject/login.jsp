<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/controller" name="loginForm" method="POST">
		<label>User name: </label>
		<input type="text" name="userName"> 
	    <label id="optionLabel">Password: </label>
	    <input type="password" name="password"> 
	    <input type="submit" name="login" value="Login"/>
	</form>
</body>
</html>