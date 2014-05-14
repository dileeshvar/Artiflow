<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>Artiflow - Home</title>
<link href="scripts/NVD3/src/nv.d3.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="style/pure_style.css">
<link rel="stylesheet" type="text/css" href="style/cssfonts-min.css">
<script src="scripts/jquery-2.0.3.min.js"></script>
<script src="scripts/NVD3/lib/d3.v3.js"></script>
<script src="scripts/NVD3/lib/fisheye.js"></script>
<script src="scripts/NVD3/lib/nv.d3.js"></script>
<script src="scripts/NVD3/src/tooltip.js"></script>
<script src="scripts/NVD3/src/utils.js"></script>
<script src="scripts/NVD3/src/models/legend.js"></script>
<script src="scripts/NVD3/src/models/axis.js"></script>
<script src="scripts/NVD3/src/models/distribution.js"></script>
<script src="scripts/NVD3/src/models/scatter.js"></script>
<script src="scripts/NVD3/src/models/scatterPlusLineChart.js"></script>
<script>
//Format A
var chart;
nv.addGraph(function() {
  chart = nv.models.scatterPlusLineChart()
                .showDistX(true)
                .showDistY(true)
                .transitionDuration(300)
                .color(d3.scale.category10().range());

  chart.xAxis.tickFormat(d3.format('.02f'))
  chart.yAxis.tickFormat(d3.format('.02f'))

  d3.select('#test1 svg')
      .datum(nv.log(randomData(4,40)))
      .call(chart);

  nv.utils.windowResize(chart.update);

  chart.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });

  return chart;
});


function randomData(groups, points) { //# groups,# points per group
  var data = [],
      shapes = ['circle', 'cross', 'triangle-up', 'triangle-down', 'diamond', 'square'],
      random = d3.random.normal();

  for (i = 0; i < groups; i++) {
    data.push({
      key: 'Group ' + i,
      values: [],
      slope: Math.random() - .01,
      intercept: Math.random() - .5
    });

    for (j = 0; j < points; j++) {
      data[i].values.push({
        x: random(), 
        y: random(), 
        size: Math.random(), 
        shape: shapes[j % 6]
      });
    }
  }

  return data;
}
</script>
<style>

body {
  overflow-y:scroll;
  margin: 0;
  padding: 0;
}

svg {
  overflow: hidden;
}

div {
  border: 0;
  margin: 0;
}

/*
#offsetDiv {
  margin-left: 100px;
  margin-top: 100px;
}
*/


#test1 {
  margin: 0;
}

#test1 svg {
  height: 500px;
}

</style>
</head>

<body class='with-3d-shadow with-transitions'>

<div id="offsetDiv">
  <div id="test1" class="chartWrap">
    <svg></svg>
  </div>
</div>
</body>
<html>
