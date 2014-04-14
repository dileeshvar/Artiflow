<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Artiflow - Initiate Review</title>
<link rel="stylesheet" href="style/pure_style.css">
<link rel="stylesheet" href="style/dragdrop.css" type="text/css"
	media="screen">
<link rel="stylesheet" type="text/css" href="style/cssfonts-min.css">
<script src="scripts/jquery-2.0.3.min.js"></script>
<script src="scripts/initiatereview.js"></script>
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
<body class="pure-skin-mine">
	<hr>
	<form class="pure-form pure-form-aligned" name="initDialogForm"
		method="post" action="InitiateReviewServlet"
		enctype="multipart/form-data" style="padding-left: 70px;">
		<fieldset>
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
							.equalsIgnoreCase("Error")) {
			%>
			<h4>
				<font color="red">Something went wrong. Please try again</font>
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
			<div class="pure-control-group">
				<label for="name">Project Name</label> <select name="projectName"
					placeholder="Select a Project" required class="pure-input-1-4">
					<c:forEach var="test" items="${Project_List}">
						<option>${test}</option>
					</c:forEach>
				</select>
			</div>
			<div class="pure-control-group">
				<label for="story">Story Name</label> <input type="text"
					name="storyname" placeholder="Story Name" required
					class="pure-input-1-4">
			</div>
			<div class="pure-control-group">
				<label for="objective">Objective of Review</label>
				<textarea name="objective" class="pure-input-1-4" rows="4" cols="50"
					required></textarea>
			</div>
			<div class="pure-control-group">
				<label for="reviewers">Reviewers Name</label> <select
					name="mainReviewers" multiple="multiple" required
					class="pure-input-1-4">
					<option value="" selected="selected" onclick="disableInOther()">None</option>
					<c:forEach var="test" items="${Reviewers}">
						<option class="list_reviewers" onclick="disableInOther()">${test}</option>
					</c:forEach>
				</select>
			</div>
			<div class="pure-control-group">
				<label for="optionalReviewers">Optional Reviewers Name</label> <select
					name="optReviewers" multiple="multiple" class="pure-input-1-4">
					<option value="" selected="selected" onclick="disableInOther()">None</option>
					<c:forEach var="test" items="${Reviewers}">
						<option class="list_optReviewers" onclick="disableInOther()">${test}</option>
					</c:forEach>
				</select>
			</div>
			<div class="pure-control-group">
				<label for="artifactType">Artifact type</label> <select
					name="artifactType" required class="pure-input-1-4">
					<option value="" selected="selected">Choose an option</option>
					<c:forEach var="test" items="${Artifact_Type}">
						<option>${test}</option>
					</c:forEach>
				</select>
			</div>
			<div class="pure-control-group">

				<label for="uploadFile">Upload Files</label>
				<div class="pure-controls">
					<fieldset class="pure-group">
						<p>
							<input id="files-upload" type="file" class="pure-button" multiple>
						</p>
						OR
						<p id="drop-area">
							<span class="drop-instructions">or drag and drop files
								here</span> <span class="drop-over">Drop files here!</span>
						</p>
						<button id="ShowProgress" class="pure-button"
							style="font-size: 85%;">Show Progress of upload</button>
						<button id="HideProgress" class="pure-button"
							style="font-size: 85%;">Hide Progress of upload</button>
						<div id="UploadProgress">
							<ul id="file-list">
								<li id="noItems" class="no-items">(no files uploaded yet)</li>
							</ul>
						</div>
						<script type="text/javascript" src="scripts/dragdrop.js"></script>
					</fieldset>
				</div>
			</div>
			<div class="pure-controls">
				<input type="submit" name="submit" value="Initiate review"
					class="pure-button pure-button-primary">&nbsp;&nbsp;<input
					type="reset" name="cancel" value="Cancel"
					class="pure-button pure-button-primary">
			</div>
			<fieldset>
	</form>
</body>
</html>