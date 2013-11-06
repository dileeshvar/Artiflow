<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="style/bubble.css">
<link rel="stylesheet" type="text/css" href="style/style.css">
<title><c:out value="${artifactName} Review Comments"/></title>
</head>
<div class="header">
<jsp:include page="LoggedInheader.jsp"></jsp:include>
</div>

<body>
<table width = "70%">
<col width="30%">
  <col width="70%">
<tr>
  <th></th>
  <th>Comments</th>
  </tr>
  <c:forEach items="${users}" varStatus="loop">
  <tr>
  <td align = "right"><c:out value="${users[loop.index]}"/></td>
  <td align = "center"><p class="triangle-right left"><c:out value="${comments[loop.index]}"/></p></td>
  </tr>
  </c:forEach>
</table>
</body>
</html>