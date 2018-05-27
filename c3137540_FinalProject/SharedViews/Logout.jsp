<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Logout</title>
	</head>
	<body>
		<p>You have been logged out. Log back in? <a href="${pageContext.request.contextPath}/Login.jsp">Login</a></p>
		<%session.invalidate();%>
	</body>
</html>