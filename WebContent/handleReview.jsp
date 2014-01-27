<%@page import="edu.ssn.sase.artiflow.models.Artifact"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="style/bubble.css">
		<link rel="stylesheet" href="style/pure_style.css">
		<link rel="stylesheet" type="text/css" href="style/cssfonts-min.css">
		<link rel="stylesheet" type="text/css" href="scripts/google-code-prettify/prettify.css">
		<script src="scripts/google-code-prettify/run_prettify.js?autoload=true&amp;skin=default&amp;lang=css" defer="defer"></script>
		<script src="scripts/jquery-2.0.3.min.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
			$('#docVersion').change(function() {
			    $('#myFrame').attr('src', $(this).val());
			    $('#code').load($(this).val(), function(responseText, textStatus, XMLHttpRequest){
			    	alert('ffdfs');
			    });
			});			
		});
		</script>
		<title>Artiflow - Handle Review</title>
	</head>
	<div class="header">
		<jsp:include page="LoggedInheader.jsp"></jsp:include>
	</div>
	<body class = "pure-skin-mine">
	<hr>
		<div>
			<h2><strong>Dummy File test</strong></h2>
			<select id="docVersion">
			<option selected="selected">Select Version</option>
			<c:forEach items="${artifactVersion}" varStatus="loop">			
			<option value="<c:out value="${artifacts[loop.index].artifact_name}"/>"><c:out value="${artifactVersion[loop.index].versionNo}"/></option>
			</c:forEach>
			</select>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span8">
				<%ArrayList<Artifact> artifact = (ArrayList<Artifact>)request.getAttribute("artifacts");
				if(artifact.get(0).getArtifact_type().getArtifactTypeId()==1){%>
				<pre class="prettyprint" id="code">
				</pre>
				<%} if(artifact.get(0).getArtifact_type().getArtifactTypeId()!=1){%>
				<c:if test="${not empty artifacts[0].artifact_name}">
				<iframe id="myFrame" width="100%" height="100%" style="border:1px solid Black;" src = "<c:out value="${artifacts[0].artifact_name}"></c:out>">      
				</iframe>
				</c:if> 
				<%} %>
				</div>
				<div class="span4">
					<div class = "topdiv">
						<h2><strong>Review Comments</strong></h2>
						<c:forEach items="${comments}" var="element"> 
							<blockquote class="example-obtuse" title="date here">
							<p>${element.commentValue}</p>
						</blockquote>
						<p>${element.userName}</p>
						</c:forEach>
						<blockquote class="example-obtuse" title="date here">
							<p>It's not what you look at that matters, it's what you see.</p>
						</blockquote>
						<p>Henry David Thoreau</p>
						<blockquote class="example-obtuse-left" title="date here">
							<p>It's not what you look at that matters, it's what you see. It's not what you look at that matters, it's what you see.
							It's not what you look. </p>
						</blockquote>
						<p>Henry , @ 12/11/13, 2:35 PM</p>
					</div>
					<div class = "bottomdiv">
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
									<%boolean isAuthor = (Boolean)request.getAttribute("isAuthor");
									if(!isAuthor){ %>
									<input type="checkbox" value="Sign-off" name="jj" id="jj" />
									<label>Sign-off</label>
									<%}
									%>
									<div id="submit_buttons">
										<button type="reset">Reset</button>
										<button type="submit">Submit</button>
									</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
	    </div>       
	</body>
</html>