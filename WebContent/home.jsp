<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Artiflow - Home</title>
<link rel="stylesheet" href="style/pure_style.css">
<link rel="stylesheet" type="text/css" href="style/cssfonts-min.css">
<script src="scripts/jquery-2.0.3.min.js"></script>
</head>
<div class="header">
	<jsp:include page="LoggedInheader.jsp"></jsp:include>
</div>
<body class="pure-skin-mine">
	<br>
	<br>
	<br>
	<hr>
	<div id="author">
		<h1> Author Dashboard</h1>
		<table border=1>
			<tr>
				<th>Review Id</th>
				<th>Review Description</th>
				<th>Review status</th>
			</tr>
			<c:forEach var="test" items="${author}">
				<tr><td>${test.review_id}</td><td>${test.storyName}:${test.objective}</td><td>${test.status_id}</td></tr>
			</c:forEach>
		</table>
	</div>
	
	<div id="reviewer">
		<h1> Reviewer Dashboard</h1>
		<table border=1>
			<tr>
				<th>Review Id</th>
				<th>Review Description</th>
				<th>Review status</th>
			</tr>
			<c:forEach var="test" items="${reviewer}">
				<tr><td>${test.review_id}</td><td>${test.storyName}:${test.objective}</td><td>${test.status_id}</td></tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>