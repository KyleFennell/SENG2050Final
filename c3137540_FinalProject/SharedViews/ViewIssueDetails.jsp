<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Issue Details</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
	</head>
	<body>
		<div id="titleBar">			
			<h1>Issue Details</h1>
		</div>
		
		<!-- Title Nav bar -->
		<div id="titleNavBar">
			Welcome ${userLoggedIn.firstName} ${userLoggedIn.surname}
			<a class="logOff" href="${pageContext.request.contextPath}/SharedViews/Logout.jsp">Log off</a>
		</div>
		
		<!-- Left Nav bar -->
		<jsp:include page="/Includes/Navigation.jsp"/>

		<div id="mainBody">
			<table>
				<tr>
					<td>Reported by user: </td><td><c:out value="${currentIssue.userID}"/></td>
				</tr>
				<tr>
					<td>Reported Date: </td><td><c:out value="${currentIssue.reportedDateTime}"/></td>
				</tr>	
				<tr>
					<td>Title: </td><td><c:out value="${currentIssue.title}"/></td>
				</tr>	
				<tr>
					<td>Description: </td><td><c:out value="${currentIssue.description}"/></td>
				</tr>
				<!-- Use ternary operator for optional attributes e.g. ${empty attribute ? true : false} -->
				<tr>
					<td>Resolution Details: </td><td><c:out value="${empty currentIssue.resolutionDetails ? 'Issue unresolved' : currentIssue.resolutionDetails}"/></td>
				</tr>
				<tr>
					<td>Resolved DateTime: </td><td><c:out value="${empty currentIssue.resolvedDateTime ? 'NA' : currentIssue.resolvedDateTime}"/></td>
				</tr>	
				<tr>
					<td>Assigned Technician: </td><td><c:out value="${empty currentIssue.ITStaffID ? 'Not yet assigned' : currentIssue.ITStaffID}"/></td>
				</tr>	
				<tr>
					<td>Status: </td><td><c:out value="${currentIssue.status}"/></td>
				</tr>	
				<tr>
					<td>Category: </td><td><c:out value="${currentIssue.category}"/></td>
				</tr>	
				<tr>
					<td>Sub Category: </td><td><c:out value="${currentIssue.subCategory}"/></td>
				</tr>				 
			</table>
			
			<h2>Comments:</h2>
			<c:choose>
				<c:when test="${not empty currentIssue.comments}">
					<table>
					<tr><th>Comment left by</th><th>Comment</th></tr>
					<c:forEach var="comment" items="${currentIssue.comments}">
						<tr>
							<td>User: <c:out value="${comment.userID}"/></td><td><c:out value="${comment.commentValue}"/></td>
						</tr>
					</c:forEach>
					</table>
				</c:when>    
				<c:otherwise>
					Issue has no comments.
				</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>