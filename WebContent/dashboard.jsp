<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="style/pure_style.css">
		<link rel="stylesheet" type="text/css" href="style/cssfonts-min.css">
		<script src="scripts/jquery-2.0.3.min.js"></script>
		<title>Reviews Dashboard</title>
	</head>
	<div class="header">
		<jsp:include page="LoggedInheader.jsp"></jsp:include>
	</div>
	<body class = "pure-skin-mine">	
	<ul>
	<c:forEach items="${reviews}" var="rev">
	<li><b>${rev.storyName}</b> - ${rev.objective}
    <c:forEach items="${rev.artifacts}" var="arti">
        <ul>
        	<li><a href="${URL}?a_id=${arti.artifact_id}&r_id=${rev.review_id}&is_a=${isAuthor}"> ${arti.artifactFileName}</a></li>
        </ul>
    </c:forEach>
    </li>
</c:forEach>
	</body>
</html>