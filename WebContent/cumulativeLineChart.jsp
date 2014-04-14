<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<<html>
<head>
<link href="scripts/NVD3/src/nv.d3.css" rel="stylesheet" type="text/css">
<meta charset=utf-8">
<title>Artiflow - Cumulative Line Chart</title>
<link rel="stylesheet" href="style/dropdown.css">
<link rel="stylesheet" href="style/pure_style.css">
<link rel="stylesheet" type="text/css" href="style/cssfonts-min.css">
<script src="scripts/NVD3/lib/d3.v3.js"></script>
<script src="scripts/NVD3/lib/nv.d3.js"></script>
<script src="scripts/NVD3/src/tooltip.js"></script>
<script src="scripts/NVD3/src/utils.js"></script>
<script src="scripts/NVD3/src/interactiveLayer.js"></script>
<script src="scripts/NVD3/src/models/legend.js"></script>
<script src="scripts/NVD3/src/models/axis.js"></script>
<script src="scripts/NVD3/src/models/scatter.js"></script>
<script src="scripts/NVD3/src/models/line.js"></script>
<script src="scripts/NVD3/src/models/cumulativeLineChart.js"></script>
<script src="scripts/jquery-2.0.3.min.js"></script>
<script>
var yval = ${yValue};
$(document).ready(function() {
    $("#left")[0].selectedIndex = yval-1;
});

// Wrapping in nv.addGraph allows for '0 timeout render', stores rendered charts in nv.graphs, and may do more in the future... it's NOT required
var data = ${testData};
nv.addGraph(function() {  
   var chart = nv.models.cumulativeLineChart()
             .useInteractiveGuideline(true)
             .x(function(d) { return d[0] })
             .y(function(d) { return d[1]})
             .color(d3.scale.category10().range())
             .average(function(d) { return d.mean/100; })
             .transitionDuration(300)
             .clipVoronoi(false);

   chart.xAxis
      .tickFormat(function(d) {
          return d3.time.format('%m/%d/%y')(new Date(d))
        });

  chart.yAxis
      .tickFormat(d3.format(','));

  d3.select('#chart1 svg')
      .datum(data)
      .call(chart);

  //TODO: Figure out a good way to do this automatically
  nv.utils.windowResize(chart.update);

  chart.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });

  return chart;
});
</script>
<style>
body {
  overflow-y:scroll;
}

text {
  font: 12px sans-serif;
}
.chart svg {
  height: 450px;
  min-width: 100px;
  min-height: 100px;
/*
  margin: 50px;
  Minimum height and width is a good idea to prevent negative SVG dimensions...
  For example width should be =< margin.left + margin.right + 1,
  of course 1 pixel for the entire chart would not be very useful, BUT should not have errors
*/
}

</style>
<H1>Artiflow - Review made Easy</H1>
<div align="right">
<h2><a href="LogoutServlet"><button style = "font-size: 60%;" class="pure-button">Logout</button></a></h2>
</div>
<div class="pure-menu pure-menu-open pure-menu-horizontal">
    <ul id="menu">
		<li class="menu-list"><a href="LoginServlet">Home</a></li>
		<li class="menu-list"><a href="#">Author Section</a>
			<ul class="sub-menu">
				<li class="sub-menu-list"><a href="InitiateReviewScreenServlet">Initiate Review</a></li>
				<li class="sub-menu-list"><a href="ReviewManagerAuthor">Initiate Review Dashboard</a></li>
			</ul></li>
		<li class="menu-list"><a href="#">Reviewer Section</a>
			<ul class="sub-menu">
				<li class="sub-menu-list"><a href="ReviewManagerReviewer">Handle Review</a></li>
			</ul></li>
		<li class="menu-list"><a href="#">Product Evolution</a>
			<ul class="sub-menu">
				<li class="sub-menu-list"><a href="TimeLineCode">Code Perspective</a></li>
				<li class="sub-menu-list"><a href="TimeLineCode">User Perspective</a></li>
				<li class="sub-menu-list"><a href="TimeLineCode">Dynamic Perspective</a></li>
			</ul></li>
	</ul>
    </div>
</head>
<body class="pure-skin-mine">
<H1>Line Plus Bar Chart with options</H1>
<form class="pure-form" action="CumulativeLineServlet" method="post">
	<fieldset>
			<label for="left">Left Axis Options</label>
			<select  name="left" id="left">
			  <option value="1">Time Duration for completion of user Story</option>
			  <option value="2">No. of Comments per User Story</option>
			  <option value="3">No. of versions Per User Story</option>
			</select>
			<button type="submit" class="pure-button pure-button-primary">Submit</button>
			</fieldset>
			</form>
  <div id="chart1" class='chart with-transitions'>
    <svg></svg>
  </div>
</body>
</html>