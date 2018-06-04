<!DOCTYPE>
<html>
<head>
	<meta charset="UTF-8">
	<title>Welcome</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/loginStyle.css">
</head>
<body>
	<div class="loginContainer">
		<h3>Welcome to the IT services portal</h3>
		<p>To login please <a href="${pageContext.request.contextPath}/Login.jsp">click here</a> </p>
	</div>
	
	<%session.invalidate();%>
</body>
</html>