<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Issue Details</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/mainStyle.css">
	</head>
	<body>
		<div class="fixedMenus">
			<div class="titleBar">			
				<h1>Issue Details</h1>
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
			<table class="tableWithBorder">
				<tr>
					<th>Reported by user: </th><td><c:out value="${currentIssue.userID}"/></td>
				</tr>
				<tr>
					<th>Reported Date: </th><td><c:out value="${currentIssue.reportedDateTime}"/></td>
				</tr>	
				<tr>
					<th>Title: </th><td><c:out value="${currentIssue.title}"/></td>
				</tr>	
				<tr>
					<th>Description: </th><td><c:out value="${currentIssue.description}"/></td>
				</tr>
				<!-- Use ternary operator for optional attributes e.g. ${empty attribute ? true : false} -->
				<tr>
					<th>Resolution Details: </th><td><c:out value="${empty currentIssue.resolutionDetails ? 'Issue unresolved' : currentIssue.resolutionDetails}"/></td>
				</tr>
				<tr>
					<th>Resolved DateTime: </th><td><c:out value="${empty currentIssue.resolvedDateTime ? 'NA' : currentIssue.resolvedDateTime}"/></td>
				</tr>	
				<tr>
					<th>Assigned Technician: </th><td><c:out value="${empty currentIssue.ITStaffID ? 'Not yet assigned' : currentIssue.ITStaffID}"/></td>
				</tr>	
				<tr>
					<th>Status: </th><td><c:out value="${currentIssue.status}"/></td>
				</tr>	
				<tr>
					<th>Category: </th><td><c:out value="${currentIssue.category}"/></td>
				</tr>	
				<tr>
					<th>Sub Category: </th><td><c:out value="${currentIssue.subCategory}"/></td>
				</tr>				 
			</table>
			
			<jsp:include page="/Includes/Comments.jsp"/>
		</div>		
	</body>
</html>