<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Artiflow - Login</title>
<link rel="stylesheet" type="text/css" href="style/style.css">
</head>
<div class="header">
<jsp:include page="header.jsp"></jsp:include>
</div>
<body>
<br><br><hr>
<form name="loginForm" method="post" action="LoginServlet">
<% if(request.getAttribute("Flag") != null && request.getAttribute("Flag").equals("Error")) { %>
<h4><font color="red"><center>Please give correct login credentials</center></font></h4>
<br>
<br>
<% } %>
<table>
<tr><th>UserName<font color="red">*</font></th><td><input type="text" name="userName"></td></tr>
<tr><th>Password<font color="red">*</font></th><td><input type="password" name="password"></td></tr>
</table>
<br>
<input type="submit" name="submit" value="Login">
</form>
</body>
</html>