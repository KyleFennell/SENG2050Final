<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="leftNavBar">
	<c:choose>
		<c:when test="${userLoggedIn.isStaff()}">	
		<ul>
			<li><a href="${pageContext.request.contextPath}/ITViews/ITMainPage.jsp">IT Main</a></li>
			<li><a href="${pageContext.request.contextPath}/ITViews/AllIssues.jsp">All Issues</a></li>
			<li><a href="${pageContext.request.contextPath}/ITViews/UnassignedIssues.jsp">Unassigned Issues</a></li>
			<li><a href="${pageContext.request.contextPath}/SharedViews/KBArticles.jsp">Knowledge Base Articles</a></li>
			<li><a href="${pageContext.request.contextPath}/SharedViews/KBSearch.jsp">Search Knowledge Base</a></li>
		</ul>
		</c:when>    
		<c:otherwise>
			<ul>
				<li><a href="${pageContext.request.contextPath}/UserViews/UserMainPage.jsp">User Main</a></li>
				<li><a href="${pageContext.request.contextPath}/UserViews/ReportIssue.jsp">Report Issue</a></li>
				<li><a href="${pageContext.request.contextPath}/SharedViews/KBArticles.jsp">Knowledge Base Articles</a></li>
				<li><a href="${pageContext.request.contextPath}/SharedViews/KBSearch.jsp">Search Knowledge Base</a></li>
			</ul>
		</c:otherwise>
	</c:choose>
</div>