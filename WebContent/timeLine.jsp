<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Artiflow - Time Line</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" href="style/pure_style.css">
<link rel="stylesheet" type="text/css" href="style/cssfonts-min.css">
<script src="scripts/timeline_ajax/simile-ajax-api.js?bundle=true"
	type="text/javascript"></script>
<script src="scripts/timeline_js/timeline-api.js?bundle=true"
	type="text/javascript"></script>
<script type="text/javascript">
	var tl;
	var resizeTimerID = null;
	function onResize() {
	    if (resizeTimerID == null) {
	        resizeTimerID = window.setTimeout(function() {
	            resizeTimerID = null;
	            tl.layout();
	        }, 500);
	    }
	}
	 function onLoad() {
	  var eventSource = new Timeline.DefaultEventSource();
	  var bandInfos = [
	    Timeline.createBandInfo({
	        eventSource:    eventSource,
        	width:          "70%", 
	        intervalUnit:   Timeline.DateTime.MONTH, 
	        intervalPixels: 100,
	    }),
	    Timeline.createBandInfo({
	        eventSource:    eventSource,
	        width:          "30%", 
	        intervalUnit:   Timeline.DateTime.YEAR,
		overview:       true,
	        intervalPixels: 200
	    })	
	  ];
	  bandInfos[1].syncWith = 0;
	  bandInfos[1].highlight = true;
	  tl = Timeline.create(document.getElementById("my-timeline"), bandInfos);
	  Timeline.loadXML("TimelineXMLSource/EventCode.xml", function(xml, url) { eventSource.loadXML(xml, url); });
	}
     </script>
</head>
<div class="header">
	<jsp:include page="LoggedInheader.jsp"></jsp:include>
</div>
<hr>
<body onload="onLoad();" onresize="onResize();">
	<H2>TimeLine View - User Story - Code Perspective</H2>
	<div id="my-timeline" style="height: 350px; border: 1px solid #aaa"></div>
	<noscript>This page uses Javascript to show you a Timeline.
		Please enable Javascript in your browser to see the full page. Thank
		you.</noscript>
</body>
</html>