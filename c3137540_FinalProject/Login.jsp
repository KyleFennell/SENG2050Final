<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/loginStyle.css">
	</head>
	<body>
		<div class="loginContainer">
			<form action="${pageContext.request.contextPath}/controller" name="loginForm" method="POST">
				<h3>Login</h3>
				<label>Username: </label>

				<input type="text" name="userName" placeholder=" Enter Username"> 
			    <label id="optionLabel">Password: </label>
			    <input type="password" name="password" placeholder="Enter Password"> 
			    <input type="submit" name="login" value="Login"/>
			</form>
		</div>
	</body>
</html>