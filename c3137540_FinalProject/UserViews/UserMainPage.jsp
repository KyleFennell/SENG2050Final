<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>User Main</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/mainStyle.css">
		<script src="${pageContext.request.contextPath}/static/Main.js"></script>
	</head>
	<body>
		<div class="fixedMenus">
			<div class="titleBar">			
				<h1>User Main</h1>
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