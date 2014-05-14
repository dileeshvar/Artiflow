<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="scripts/NVD3/src/nv.d3.css" rel="stylesheet" type="text/css">
<meta charset=utf-8">
<title>Artiflow - Line Plus Bar Chart</title>
<link rel="stylesheet" href="style/dropdown.css">
<link rel="stylesheet" href="style/pure_style.css">
<link rel="stylesheet" type="text/css" href="style/cssfonts-min.css">
<script src="scripts/NVD3/lib/d3.v3.js"></script>
<script src="scripts/NVD3/lib/nv.d3.js"></script>
<script src="scripts/NVD3/src/utils.js"></script>
<script src="scripts/NVD3/src/models/axis.js"></script>
<script src="scripts/NVD3/src/tooltip.js"></script>
<script src="scripts/NVD3/src/interactiveLayer.js"></script>
<script src="scripts/NVD3/src/models/legend.js"></script>
<script src="scripts/NVD3/src/models/axis.js"></script>
<script src="scripts/NVD3/src/models/scatter.js"></script>
<script src="scripts/NVD3/src/models/stackedArea.js"></script>
<script src="scripts/NVD3/src/models/stackedAreaChart.js"></script>
<script>
var histcatexplong = ${testData};
var colors = d3.scale.category20();
keyColor = function(d, i) {return colors(d.key)};

var chart;
nv.addGraph(function() {
  chart = nv.models.stackedAreaChart()
               // .width(600).height(500)
                .useInteractiveGuideline(true)
                .x(function(d) { return d[0] })
                .y(function(d) { return d[1] })
                .color(keyColor)
                .transitionDuration(300);
                //.clipEdge(true);

// chart.stacked.scatter.clipVoronoi(false);

  chart.xAxis
      .tickFormat(function(d) { return d3.time.format('%x')(new Date(d)) });

  chart.yAxis
      .tickFormat(d3.format(','));

  d3.select('#chart1')
    .datum(histcatexplong)
    .transition().duration(1000)
    .call(chart)
    // .transition().duration(0)
    .each('start', function() {
        setTimeout(function() {
            d3.selectAll('#chart1 *').each(function() {
              console.log('start',this.__transition__, this)
              // while(this.__transition__)
              if(this.__transition__)
                this.__transition__.duration = 1;
            })
          }, 0)
      })
    // .each('end', function() {
    //         d3.selectAll('#chart1 *').each(function() {
    //           console.log('end', this.__transition__, this)
    //           // while(this.__transition__)
    //           if(this.__transition__)
    //             this.__transition__.duration = 1;
    //         })});

  nv.utils.windowResize(chart.update);

  // chart.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });

  return chart;
});

nv.addGraph(function() {
  var chart = nv.models.stackedAreaChart()
                .x(function(d) { return d[0] })
                .y(function(d) { return d[1] })
                .color(keyColor)
                ;
                //.clipEdge(true);

  chart.xAxis
      .tickFormat(function(d) { return d3.time.format('%x')(new Date(d)) });

  chart.yAxis
      .tickFormat(d3.format(',.2f'));

  d3.select('#chart2')
    .datum(histcatexpshort)
    .transition()
      .call(chart);

  nv.utils.windowResize(chart.update);

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
#chart1, #chart2 {
  height: 500px;
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
				<li class="sub-menu-list"><a href="TimeLineCode">Timeline Perspective</a></li>
				<li class="sub-menu-list"><a href="LineBarServlet">Line-Plus Bar Chart</a></li>
				<li class="sub-menu-list"><a href="CumulativeLineServlet">Cumulative Line Chart</a></li>
				<li class="sub-menu-list"><a href="stackedAreaServlet">Stacked Area Chart</a></li>
			</ul></li>
	</ul>
    </div>
</head>
<body class='pure-skin-mine'>
<H1>Line Plus Bar Chart with options</H1>
<br>
<H4>Note: The chart will be populated only When more than 50 user stories exists</H4>
<form class="pure-form" action="StackedAreaServlet" method="post">
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
  <div>
    <svg id="chart1" class='chart with-transitions'></svg>
  </div>
</body>
</html>
