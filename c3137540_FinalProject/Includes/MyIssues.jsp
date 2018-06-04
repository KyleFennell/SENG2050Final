<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<h1>My Issues</h1>
	<c:choose>
		<c:when test="${not empty myIssues}">
			<table class="tableWithBorder">
			<tr><th>Reported Date</th><th>Title</th><th>Status</th><th></th></tr>
			<c:forEach var="issue" items="${myIssues}">
				<tr>
					<td><fmt:formatDate type = "both" dateStyle = "medium" timeStyle = "short" value = "${issue.reportedDateTime}" /></td>
					<td><c:out value="${issue.title}"/></td>
					<td><c:out value="${issue.status}"/></td>
					<td>
						<form action="${pageContext.request.contextPath}/controller" name="issueForm" method="POST">
							<input type="hidden" name="issueID" value="${issue.issueID}" />
							<input type="submit" value="View Issue" />
						</form>
					</td>
				</tr>
			</c:forEach>
			</table>
		</c:when>    
		<c:otherwise>
			You have no allocated issues.
		</c:otherwise>
	</c:choose>


