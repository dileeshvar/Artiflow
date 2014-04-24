<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset=utf-8">
<title>Artiflow - Line Plus Bar Chart</title>
<link href="scripts/NVD3/src/nv.d3.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="style/pure_style.css">
<link rel="stylesheet" type="text/css" href="style/cssfonts-min.css">
<script src="scripts/jquery-2.0.3.min.js"></script>
<script src="scripts/NVD3/lib/d3.v3.js"></script>
<script src="scripts/NVD3/lib/nv.d3.js"></script>
<script src="scripts/NVD3/src/utils.js"></script>
<script src="scripts/NVD3/src/tooltip.js"></script>
<script src="scripts/NVD3/src/models/legend.js"></script>
<script src="scripts/NVD3/src/models/axis.js"></script>
<script src="scripts/NVD3/src/models/scatter.js"></script>
<script src="scripts/NVD3/src/models/line.js"></script>
<script src="scripts/NVD3/src/models/historicalBar.js"></script>
<script src="scripts/NVD3/src/models/linePlusBarWithFocusChart.js"></script>
<script src="scripts/jquery-2.0.3.min.js"></script>
<script>
var lval = ${lValue};
var rval = ${rValue};

$(document).ready(function() {
    $("#left")[0].selectedIndex = lval-1;
    $("#right")[0].selectedIndex = rval-1;
    if( $("#left")[0].selectedIndex==  $("#right")[0].selectedIndex){
    	alert("Both Left and Right axis options cannot be same");
    	top.location="/Test_Artiflow/LineBarServlet";
    }

});

var testdata = ${testData}.map(function(series) {
  series.values = series.values.map(function(d) { return {x: d[0], y: d[1] } });
  return series;
});

nv.addGraph(function() {
    var chart = nv.models.linePlusBarWithFocusChart()
        .margin({top: 30, right: 60, bottom: 50, left: 70})
        .x(function(d,i) { return i })
        .color(d3.scale.category10().range());
    chart.xAxis.tickFormat(function(d) {

      var dx = testdata[0].values[d] && testdata[0].values[d].x || 0;
      if (dx > 0) {
          return d3.time.format('%x')(new Date(dx))
      }
      return null;
    });

    chart.x2Axis.tickFormat(function(d) {
      var dx = testdata[0].values[d] && testdata[0].values[d].x || 0;
      return d3.time.format('%x')(new Date(dx))
    });
    
    chart.y1Axis
        .tickFormat(d3.format(',f'));

    chart.y3Axis
        .tickFormat(d3.format(',f'));
        
    chart.y2Axis
        .tickFormat(function(d) { return '' + d3.format(',.2f')(d) });

    chart.y4Axis
        .tickFormat(function(d) { return '' + d3.format(',.2f')(d) });
        
    chart.bars.forceY([0]);
    chart.bars2.forceY([0]);
    //chart.lines.forceY([0]);
    nv.log(testdata);
    d3.select('#chart1 svg')
        .datum(testdata)
        .transition().duration(500)
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

#chart1 svg {
  height: 500px;
  margin: 10px;
  min-width: 100px;
  min-height: 100px;
/*
  Minimum height and width is a good idea to prevent negative SVG dimensions...
  For example width should be =< margin.left + margin.right + 1,
  of course 1 pixel for the entire chart would not be very useful, BUT should not have errors
*/
}

</style>
</head>
<div class="header">
	<jsp:include page="LoggedInheader.jsp"></jsp:include>
</div>
<body class="pure-skin-mine">
<H1>Line Plus Bar Chart with options</H1>
<form class="pure-form" action="LineBarServlet" method="post">
	<fieldset>
			<label for="left">Left Axis Options</label>
			<select  name="left" id="left">
			  <option value="1">No. of Reviews per User Story</option>
			  <option value="2">No. of Comments per User Story</option>
			  <option value="3">Time Duration for completion of user Story</option>
			  <option value="4">No. of versions Per User Story</option>
			  <option value="5">No. Of Artifacts per user Story</option>
			  <option value="6">No. Of Artifact Types per user Story</option>
			</select>

			<label for="right">Right Axis Options</label>
			<select  name="right" id="right">
			  <option value="1">No. of Reviews per User Story</option>
			  <option value="2">No. of Comments per User Story</option>
			  <option value="3">Time Duration for completion of user Story</option>
			  <option value="4">No. of versions Per User Story</option>
			  <option value="5">No. Of Artifacts per user Story</option>
			  <option value="6">No. Of Artifact Types per user Story</option>
			</select>
			 <button type="submit" class="pure-button pure-button-primary">Submit</button>
	</fieldset>
</form>

  <div id="chart1" class='with-3d-shadow with-transitions'>
    <svg> </svg>
  </div>
  </body>
</html>
