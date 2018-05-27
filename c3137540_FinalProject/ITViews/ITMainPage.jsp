<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>IT Main</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/mainStyle.css">
	</head>
	<body>
		<div class="fixedMenus">
			<div class="titleBar">			
				<h1>IT Main</h1>
			</div>
			<div class="titlenav">
				Welcome ${userLoggedIn.firstName} ${userLoggedIn.surname}
				<a class="logOff" href="${pageContext.request.contextPath}/SharedViews/Logout.jsp">Log off</a>
				<a href="javascript:void(0);" style="font-size:15px;" class="icon" onclick="responsiveLeftNav()">&#9776;</a>	
			</div>
			<script>
				function responsiveLeftNav() 
				{
				    var x = document.getElementById("leftNavBar");
				    if (x.className === "leftnav") {
				        x.className += " responsive";
				    } else {
				        x.className = "leftnav";
				    }
				}
			</script>

			<!-- Left Nav bar -->
			<div class="leftnav" id="leftNavBar">
				<jsp:include page="/Includes/Navigation.jsp" />
			</div>

			<div class="rightColumn">
				<table>
					<tr>
						<td>I don't know what we want over here yet</td>
					</tr>
					<tr>
						<td>so this is a place holder</td>
					</tr>
				</table>
			</div>
		</div>	
		<div class="main" id="mainBody">
			<jsp:include page="/Includes/MyIssues.jsp" />
		</div>		
	</body>
</html>