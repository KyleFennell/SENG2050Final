<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
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