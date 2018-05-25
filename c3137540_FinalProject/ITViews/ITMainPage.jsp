<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>IT Main</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
	</head>
	<body>
		<div id="titleBar">			
			<h1>IT Main</h1>
		</div>

		<!-- Title Nav bar -->
		<div id="titleNavBar">
			Welcome ${userLoggedIn.firstName} ${userLoggedIn.surname}
			<a class="logOff" href="${pageContext.request.contextPath}/SharedViews/Logout.jsp">Log off</a>
		</div>
		
		<!-- Left Nav bar -->
		<jsp:include page="/Includes/Navigation.jsp"/>

		<div id="mainBody">
			<jsp:include page="/Includes/MyIssues.jsp"/>
		</div>
	</body>
</html>