<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Unassigned Issues</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
	</head>
	<body>
		
		<div id="titleBar">			
			<h1>Unassigned Issues</h1>
		</div>

		<!-- Title Nav bar -->
		<div id="titleNavBar">
			Welcome ${userLoggedIn.firstName} ${userLoggedIn.surname}
			<a class="logOff" href="${pageContext.request.contextPath}/SharedViews/Logout.jsp">Log off</a>
		</div>

		<!-- Left Nav bar -->
		<jsp:include page="/Includes/Navigation.jsp"/>

		<div id="mainBody">
		<h1>Unassigned Issues</h1>
		
		</div>
	</body>
</html>