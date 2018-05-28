<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login</title>

		<!-- different stylesheet for login page -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/loginStyle.css">
	</head>
	<body>

		<!-- New container div -->
		<div class="loginContainer">
			<form action="${pageContext.request.contextPath}/controller" name="loginForm" method="POST">

				<!-- New heading -->
				<h3>Login</h3>
				<label>Username: </label>

				<!-- Added placeholders to the input boxes -->
				<input type="text" name="userName" placeholder=" Enter Username"> 
			    <label id="optionLabel">Password: </label>
			    <input type="password" name="password" placeholder="Enter Password"> 
			    <input type="submit" name="login" value="Login"/>
			</form>
		</div>
	</body>
</html>