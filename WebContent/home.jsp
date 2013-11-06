<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Artiflow - Home</title>
<link rel="stylesheet" type="text/css" href="style/style.css">
<script type="text/javascript" src="scripts/initiatereview.js">
window.name = 'home';
</script>
</head>
<div class="header">
<jsp:include page="header.jsp"></jsp:include>
</div>
<body>
<br>
<br>
<br>
<hr>
<input type="button" name="initiateReviewButton" value="Initiate new review" onclick="showForm()"></input>
<div id="initForm" style="display: none;">
<jsp:include page="initDialogForm.jsp"></jsp:include>
</div>
<table border=1>
<tr>
<th>SNo</th>
<th>Review Description</th>
<th>Initiated By</th>
<th>Reviewer</th>
<th>Review status</th>
</tr>
</table>
</body>
</html>