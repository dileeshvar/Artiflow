<head>
<link rel="stylesheet" href="style/dropdown.css">
<script type="text/javascript" src="scripts/jquery-2.0.3.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $('#menu a').each(function(index) {
        if(this.href.trim() == window.location){
            $(this).parent().addClass('pure-menu-selected');	
        }
    });
});
</script>
</head>
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