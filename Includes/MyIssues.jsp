<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>My Issues</h1>
	<c:choose>
		<c:when test="${not empty myIssues}">
		
			<table class="tableWithBorder">
			<tr><th>Reported Date</th><th>Title</th><th></th></tr>
			<c:forEach var="issue" items="${myIssues}">
				<tr>
					<td><c:out value="${issue.reportedDateTime}"/></td>
					<td><c:out value="${issue.title}"/></td>
					<td>
						<form action="${pageContext.request.contextPath}/controller" method="POST">
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

