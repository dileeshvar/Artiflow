<%@page import="java.io.File"%>
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
<script src="scripts/google-code-prettify/run_prettify.js?autoload=true&amp;skin=default&amp;lang=css" defer="defer"></script>
<title>Artiflow - Handle Review</title>
</head>
<div class="header">
<jsp:include page="LoggedInheader.jsp"></jsp:include>
</div>
<body>
 <div style="margin-left : 5%;">
<% String filePath = (String)request.getAttribute("artifacts");
File artifact = new File(filePath);
out.print("<h2><strong> File Name: "+artifact.getName()+"</strong></h2>");
%>
</div>
 <div style="width:65%; height :85%;float: left; position: fixed; overflow-x: auto; overflow-y: auto; border:solid;">
        <pre class="prettyprint">
			<c:import var="data" url="file:///${artifacts}"/>
			<c:out value="${fn:trim(data)}"/>
		</pre>
</div>
<div style="position:relative; width:34%; height:85%; float : right; border:solid; overflow-x: auto; overflow-y: auto;  clear: none; display: inline-block; ">
      <div style="overflow-x: auto; overflow-y: auto; width:90%; height:70%; position:relative;" >
           <h2><strong>Review Comments</strong></h2>
		<c:forEach items="${comments}" var="element"> 
			<p class="triangle-right left">${element.commentValue}</p>
		</c:forEach>
      </div>
      
      <div style="position:relative; bottom:0; height: 30%; width:80%;">
	<form method="post" name="handle" id="handle" action="HandleReview">
		<fieldset style="width:100%">
			<legend><strong>Comments Section</strong></legend>
			<dl>
				<dt>
					<label for="comments">Comments</label>
				</dt>
				<dd><textarea cols="50" rows="5" name="comments" id="comments" style="width:100%">
</textarea></dd>
			</dl>
<input type="checkbox" value="Sign-off" name="jj" id="jj" />
							<label>Sign-off</label>
			<div id="submit_buttons">
				<button type="reset">Reset</button>
				<button type="submit">Submit</button>
			</div>
				</fieldset>
	</form>
</div>
</div>       
</body>
</html>