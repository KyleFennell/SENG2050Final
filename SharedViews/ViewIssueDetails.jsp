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
				<jsp:include page="/Includes/UpdateIssueForm.jsp"/>
			</div>
		</div>	
		
		<div class="main" id="mainBody">
		<h2>Reported Issue Details:</h2>
			<p><label class="boldText">Issue title: </label>${currentIssue.title} </p>
			<p><label class="boldText">Description: </label> ${currentIssue.description}</p>
			
			<table class="tableWithBorder">
				<tr>
					<th>Reported Date: </th><td><c:out value="${currentIssue.reportedDateTime}"/></td>
					<th>Assigned Technician: </th><td><c:out value="${empty currentIssue.ITStaffID ? '-' : currentIssue.ITStaffID}"/></td>
				</tr>	
				<tr>
					<th>Reported by userID: </th><td><c:out value="${currentIssue.userID}"/></td>
					<th>Reported by userName: </th><td><c:out value="${reportedUserName}"/></td>	
				</tr>
				<!-- Use ternary operator for optional attributes e.g. ${empty attribute ? true : false} -->
				<tr>
					<th>Category: </th><td><c:out value="${currentIssue.category}"/></td>
					<th>Sub Category: </th><td><c:out value="${currentIssue.subCategory}"/></td>
				</tr>
				<tr>
					<th>Status: </th><td><c:out value="${currentIssue.status}"/></td>
					<th>Resolved DateTime: </th><td><c:out value="${empty currentIssue.resolvedDateTime ? '-' : currentIssue.resolvedDateTime}"/></td>
				</tr>	
				<tr>
					<th>Resolution Details: </th><td colspan="3"><c:out value="${empty currentIssue.resolutionDetails ? '-' : currentIssue.resolutionDetails}"/></td>		
				</tr>
			</table>
			<c:choose>
				<c:when test="${sessionScope.errorMessage == 'Success! Your comment has been added.'}">
					<p class="greenText">${errorMessage}</p> 
				</c:when>
				<c:otherwise>
					<p class="redText">${errorMessage}</p>
				</c:otherwise>
			</c:choose>

			<jsp:include page="/Includes/Comments.jsp"/>
		</div>		
	</body>
</html>