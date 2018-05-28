<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="${pageContext.request.contextPath}/controller" method="POST">
	<h2>Issue Actions</h2>

	<c:choose>
		<c:when test="${currentIssue.status != 'Resolved'}">
			<!-- Issue status drop down list -->
			Issue status: 
			<select name="issueStatus">
				<option value="noneSelected" selected="selected">--- Select a status ---</option>
				<c:forEach items="${statusList}" var="status">
					<option value="${status}">${status}</option>
				</c:forEach>
			</select>
			<br /><br />
			<c:choose>
				<c:when test="${sessionScope.userLoggedIn.isStaff()}">
					<!-- Assign technician drop down list -->
					Assign Technician: 
					<select name="assignedStaff">
						<option value="0" selected="selected">--- Select a staff member ---</option>
						<c:forEach items="${staffList}" var="staff">
							<option value="${staff.userID}">${staff.firstName} ${staff.surname}</option>
						</c:forEach>
					</select>
					<br /><br />
				</c:when>
			</c:choose>
			<input type="submit" value="Update Issue" />
		</c:when>
		<c:otherwise>
			<p class="redText"><p class="redText">Issue has been resolved. No actions currently available.</p></p>
		</c:otherwise>
	</c:choose>
</form>