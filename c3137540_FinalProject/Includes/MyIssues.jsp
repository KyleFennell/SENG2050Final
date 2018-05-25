<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>My Issues</h1>
<div id="myIssuesTable">
	<c:choose>
		<c:when test="${not empty myIssues}">
		
			<table>
			<tr><th>Reported Date</th><th>Title</th><th></th></tr>
			<c:forEach var="issue" items="${myIssues}">
				<tr>
					<td><c:out value="${issue.reportedDateTime}"/></td>
					<td><c:out value="${issue.title}"/></td>
					<td><a href="${pageContext.request.contextPath}/controller?id=${issue.issueID}">View Issue</a></td>
				</tr>
			</c:forEach>
			</table>
		</c:when>    
		<c:otherwise>
			You have no allocated issues.
		</c:otherwise>
	</c:choose>
</div>


