<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Knowledge Base</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/mainStyle.css">
		<script src="${pageContext.request.contextPath}/static/Main.js"></script>
	</head>
	<body>
		<div class="fixedMenus">
			<div class="titleBar">			
				<h1>Knowledge Base</h1>
			</div>
			
			<div class="titlenav">
				<a href="javascript:void(0);" style="font-size:15px;" class="icon" onclick="responsiveLeftNav()">&#9776;</a>	
				Welcome ${userLoggedIn.firstName} ${userLoggedIn.surname}
				<a class="logOff" href="${pageContext.request.contextPath}/controller">Log off</a>
			</div>
			

			<div class="leftnav" id="leftNavBar">
				<jsp:include page="/Includes/Navigation.jsp" />
			</div>

			<div class="rightColumn">
				<jsp:include page="/Includes/Maintenance.jsp" />
			</div>
		</div>	
		
		<div class="main" id="mainBody">
			<form action="${pageContext.request.contextPath}/controller" name="sortArticles" method="POST">
				Sort by category: 
				<select name="categories">
					<option value="noneSelected" selected="selected">--- Select a category ---</option>
					<c:forEach items="${categoryList}" var="cat">
						<option value="${cat.category}">${cat.category}</option>
				</c:forEach>
				</select>
				<br/><br/>
				
				Sort by Sub category: 
				<select name="subCategories">
					<option value="noneSelected" selected="selected">--- Select a sub category ---</option>
					<c:forEach items="${categoryList}" var="cat">
							<c:forEach items="${cat.subCategories}" var="subCat">
								<option value="${subCat}">${subCat}</option>
							</c:forEach>
					</c:forEach>
				</select>
				<br/><br/>
				<input type="submit" name="filterArticle" value="Filter Articles" />
				<input type="submit" name="resetList" value="Reset" />
			</form>
			<br/>
			<c:choose>
				<c:when test="${not empty kbArticlesList}">
					<display:table name="sessionScope.kbArticlesList" id="commentTable" keepStatus="true" class="displayTagTable" pagesize="10"> <!-- Name must match the name of the session param that you set the list you'd like to display e.g. session.setAttribute( "currentCommentList", currentCommentList); -->
					    <display:column title="Resolved Date & Time" property="resolvedDateTime" format="{0,date,dd-MMMM-yyyy',' hh:mm a}"/>
					    <display:column title="Category" property="category" /> 
					    <display:column title="Sub Category" property="subCategory" />
					    <display:column title="Title" property="title" />
						<display:column title="">
							<form action="${pageContext.request.contextPath}/controller" name="articleForm" method="POST">
								<input type="hidden" name="articleID" value="${commentTable.issueID}" />
								<input type="submit" value="View Article" />
							</form>
						</display:column>
					    <!-- Table properties also located in WEB-INF/displaytag.properties file-->
					    <display:setProperty name="paging.banner.placement" value="bottom" />
					    <display:setProperty name="basic.msg.empty_list" value="No comments found." />
					</display:table>
				</c:when>    
				<c:otherwise>
					<p class="redText">No knowledge base articles found.</p>
				</c:otherwise>
			</c:choose>	
		</div>		
	</body>
</html>