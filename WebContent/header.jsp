<head>
<link rel="stylesheet" href="style/pure_style.css">
<script type="text/javascript" src="script/jquery-2.0.3.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $('#nav a').each(function(index) {
        if(this.href.trim() == window.location)
            $(this).addClass("pure-menu-selected");
    });
});
</script>
</head>
<H1> Artiflow - Review made Easy</H1>
<div class="pure-menu pure-menu-open pure-menu-horizontal">
    <ul id="nav">
        <li class= "pure-menu-selected"><a href="login.jsp">Home</a></li>
        <li><a href="#">Author Section</a></li>
        <li><a href="#">Reviewer Section</a></li>
        <li><a href="#">Product Evolution</a></li>
    </ul>
</div>