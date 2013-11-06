<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Artiflow - Initiate Review</title>
<link rel="stylesheet" type="text/css" href="style/style.css">
</head>
<div class="header">
<jsp:include page="LoggedInheader.jsp"></jsp:include>
</div>
<body>
<br><br><hr>
<form name="initDialogForm" method="post" action="initiateReviewServlet" enctype="multipart/form-data">
<% if(request.getAttribute("Error")!= null) {%>
<h4><font color="red">Needed Attributes not filled in</font></h4>
<br>
<br>
<% } %>
<table>
<tr><th>Project Name<font color="red">*</font></th>
<td>
<select name="projectName">
<option value="Project1">Project1</option>
</select>
</td></tr>
<tr><th>Story Name<font color="red">*</font></th><td><input type="text" name="storyname"></td></tr>
<tr><th>Objective of Review<font color="red">*</font></th><td><textarea name="objective"></textarea></td></tr>
<tr><th>Reviewers Name<font color="red">*</font></th>
<td>
<select name="mainReviewers">
<option value="Reviewer">Reviewer</option>
<option value="Author">Author</option>
</select>
</td></tr>
<tr><th>Optional Reviewers Name<font color="red">*</font></th>
<td>
<select name="optReviewers">
<option value="">None</option>
</select>
</td></tr>
<tr><th>Upload File<font color="red">*</font></th><td><input type="file" name="artifact"></td>
<td><input type="button" name="addArtifact" value="Add" onclick="addMoreFiles()"></tr>
<tr><th>Artifact type<font color="red">*</font></th>
<td>
<select name="artifactType">
<option value="Code">Code</option>
<option value="Product Backlog">Product Backlog</option>
<option value="Sprint Backlog">Sprint Backlog</option>
</select>
</td></tr>
</table>
<br>
<br>
<input type="submit" name="submit" value="Initiate review">
<input type="reset" name="cancel" value="Cancel">
</form>
</body>
</html>