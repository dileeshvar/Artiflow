<head>
<link rel="stylesheet" href="style/pure_style.css">
<script type="text/javascript" src="script/jquery-2.0.3.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $('#nav a').each(function(index) {
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
    <ul id="nav">
        <li><a href="/Test_Artiflow">Home</a></li>
        <li><a href="InitiateReviewScreenServlet">Author Section</a></li>
        <li><a href="HandleReview">Reviewer Section</a></li>
        <li><a href="TimeLineCode">Product Evolution</a></li>
    </ul>
    </div>