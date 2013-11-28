<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Artiflow - Initiate Review</title>
<link rel="stylesheet" type="text/css" href="style/style.css">
<link rel="stylesheet" href="style/dragdrop.css" type="text/css"
	media="screen">
<script src="scripts/jquery-2.0.3.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#UploadProgress").ready(function() {
			$("#UploadProgress").hide();
			$("#HideProgress").attr("disabled", true);
		});
		$("#ShowProgress").click(function() {
			$("#UploadProgress").show();
			$("#ShowProgress").attr("disabled", true);
			$("#HideProgress").attr("disabled", false);
		});
		$("#HideProgress").click(function() {
			$("#ShowProgress").attr("disabled", false);
			$("#HideProgress").attr("disabled", true);
			$("#UploadProgress").hide();
		});
	});
</script>
<script src="scripts/deleteFile.js"></script>
</head>
<div class="header">
	<jsp:include page="LoggedInheader.jsp"></jsp:include>
</div>
<body>
	<br>
	<br>
	<hr>
	<form name="initDialogForm" method="post"
		action="InitiateReviewServlet" enctype="multipart/form-data">
		<%
			if (request.getAttribute("Status") != null
					&& request.getAttribute("Status").toString()
							.equalsIgnoreCase("Failure")) {
		%>
		<h4>
			<font color="red">Needed Attributes not filled in</font>
		</h4>
		<br> <br>
		<%
			} else if (request.getAttribute("Status") != null
					&& request.getAttribute("Status").toString()
							.equalsIgnoreCase("Success")) {
		%>
		<h4>
			<font color="green">Review Initiated Successfully</font>
		</h4>
		<br> <br>
		<%
			}
		%>
		<table>
			<tr>
				<th>Project Name<font color="red">*</font></th>
				<td><select name="projectName">
						<c:forEach var="test" items="${Project_List}">
							<option>${test}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th>Story Name<font color="red">*</font></th>
				<td><input type="text" name="storyname"></td>
			</tr>
			<tr>
				<th>Objective of Review<font color="red">*</font></th>
				<td><textarea name="objective"></textarea></td>
			</tr>
			<tr>
				<th>Reviewers Name<font color="red">*</font></th>
				<td><select name="mainReviewers" multiple="multiple">
						<option value="" selected="selected">None</option>
						<c:forEach var="test" items="${Reviewers}">
							<option>${test}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th>Optional Reviewers Name</th>
				<td><select name="optReviewers" multiple="multiple">
						<option value="" selected="selected">None</option>
						<c:forEach var="test" items="${Reviewers}">
							<option>${test}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th>Upload File<font color="red">*</font></th>
				<td><p>
						<input id="files-upload" type="file" multiple>
					</p>OR
					<p id="drop-area">
						<span class="drop-instructions">or drag and drop files here</span>
						<span class="drop-over">Drop files here!</span>
					</p>
					<button id="ShowProgress">Show Progress of upload</button>
					<button id="HideProgress">Hide Progress of upload</button>
					<div id="UploadProgress">
						<ul id="file-list">
							<li class="no-items">(no files uploaded yet)</li>
						</ul>
					</div>
					<script type="text/javascript" src="scripts/dragdrop.js"></script>
				</td>
			</tr>
			<tr>
				<th>Artifact type<font color="red">*</font></th>
				<td><select name="artifactType">
						<option value="Code">Code</option>
						<option value="Product Backlog">Product Backlog</option>
						<option value="Sprint Backlog">Sprint Backlog</option>
				</select></td>
			</tr>
		</table>
		<input type="submit" name="submit" value="Initiate review"><input
			type="reset" name="cancel" value="Cancel">
	</form>
</body>
</html>