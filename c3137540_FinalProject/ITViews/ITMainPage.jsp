<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>IT Main</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/mainStyle.css">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<link rel="stylesheet" href="/resources/demos/style.css">
		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<script src="${pageContext.request.contextPath}/static/Main.js"></script>
	</head>
	<body>
		<%  //Clear session errorMessage when page first loads
			HttpSession userSession = request.getSession();
			userSession.setAttribute("errorMessage", "");
		%>
		<div class="fixedMenus">
			<div class="titleBar">			
				<h1>IT Main</h1>
			</div>
			<div class="titlenav">
				<a href="javascript:void(0);" style="font-size:15px;" class="icon" onclick="responsiveLeftNav()">&#9776;</a>	
				Welcome ${userLoggedIn.firstName} ${userLoggedIn.surname}
				<a class="logOff" href="${pageContext.request.contextPath}/controller">Log off</a>
			</div>
			
			<!-- Left Nav bar -->
			<div class="leftnav" id="leftNavBar">
				<jsp:include page="/Includes/Navigation.jsp" />
			</div>

			<div class="rightColumn">
				<jsp:include page="/Includes/Maintenance.jsp" />
			</div>
		</div>	
	<div class="main" id="mainBody">
		<jsp:include page="/Includes/MyIssues.jsp" />
	</div>		
	</body>
</html>