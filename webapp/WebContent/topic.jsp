<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.angelhack.wuw.topsy.TopsyClient" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Whats Up With...</title>

<!-- Le styles -->
<link href="assets/css/bootstrap.css" rel="stylesheet">
<link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
<link href="assets/css/activity-streams.css"rel="stylesheet" type="text/css" />
<style type="text/css">
body {
	padding-top: 20px;
	padding-bottom: 40px;
}

/* Custom container */
.container-narrow {
	margin: 0 auto;
	max-width: 900px;
}

.container-narrow>hr {
	margin: 15px 0;
}

/* Main marketing message and sign up button */
.jumbotron {
	margin: 10px 0;
	text-align: center;
}

.jumbotron h1 {
	font-size: 35px;
	line-height: 1;
}

.jumbotron .btn {
	font-size: 21px;
	padding: 14px 24px;
}

/* Supporting marketing content */
.marketing {
	margin: 20px 0;
}

.marketing p+h4 {
	margin-top: 28px;
}
</style>

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<script src="http://js.pusherapp.com/1.10/pusher.min.js"></script>
<script src="assets/js/PusherActivityStreamer.js"></script>
<script src="assets/js/ExampleActivities.js"></script>    
<script>
  $(function() {
    
    var pusher = new Pusher('255928ee415a950c8d77')
    var activityChannel = pusher.subscribe('${param.t}');
    var activityMonitor = new PusherActivityStreamer(activityChannel, "#activity_stream_example");
    
    //var examples = new ExampleActivities(activityMonitor, pusher);
  });
</script>

<!-- Fav and touch icons -->
<link rel="shortcut icon" href="../assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="assets/ico/apple-touch-icon-57-precomposed.png">
</head>

<body>
	<div class="container-narrow">

		<div class="masthead">
			<ul class="nav nav-pills pull-right">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="#">About</a></li>
				<li><a href="#">Contact</a></li>
			</ul>
			<h3 class="muted">What's Up With...</h3>
		</div>
		<hr>
		<div class="jumbotron">
			<h1>${param.t}</h1>
		</div>
		<hr>
		<div class="row-fluid marketing">
			<div class="span6">
				<h4>Popular Tweets</h4>
				<ul id="activity_stream_example" class="activity-stream"></ul>
			</div>
			<div class="span6">
				<h4>Subheading</h4>
				<p>Donec id elit non mi porta gravida at eget metus. Maecenas
					faucibus mollis interdum.</p>

				<h4>Subheading</h4>
				<p>Morbi leo risus, porta ac consectetur ac, vestibulum at eros.
					Cras mattis consectetur purus sit amet fermentum.</p>

				<h4>Subheading</h4>
				<p>Maecenas sed diam eget risus varius blandit sit amet non
					magna.</p>
			</div>
		</div>
		<div class="row-fluid marketing">
			<div class="span6">
				<h4>Live Tweets</h4>
				<ul id="activity_stream_example" class="activity-stream"></ul>
			</div>
			<div class="span6">
				<h4>Subheading</h4>
				<p>Donec id elit non mi porta gravida at eget metus. Maecenas
					faucibus mollis interdum.</p>

				<h4>Subheading</h4>
				<p>Morbi leo risus, porta ac consectetur ac, vestibulum at eros.
					Cras mattis consectetur purus sit amet fermentum.</p>

				<h4>Subheading</h4>
				<p>Maecenas sed diam eget risus varius blandit sit amet non
					magna.</p>
			</div>
		</div>
		<div class="footer">
			<p>&copy; What's Up With... Developed at Angel Hack, London, November 2012.</p>
		</div>
	</div>
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="assets/js/jquery.js"></script>
	<script src="assets/js/bootstrap-transition.js"></script>
	<script src="assets/js/bootstrap-alert.js"></script>
	<script src="assets/js/bootstrap-modal.js"></script>
	<script src="assets/js/bootstrap-dropdown.js"></script>
	<script src="assets/js/bootstrap-scrollspy.js"></script>
	<script src="assets/js/bootstrap-tab.js"></script>
	<script src="assets/js/bootstrap-tooltip.js"></script>
	<script src="assets/js/bootstrap-popover.js"></script>
	<script src="assets/js/bootstrap-button.js"></script>
	<script src="assets/js/bootstrap-collapse.js"></script>
	<script src="assets/js/bootstrap-carousel.js"></script>
	<script src="assets/js/bootstrap-typeahead.js"></script>
</body>
</html>

<%
TopsyClient topsy = new TopsyClient();
topsy.getResponse(request.getParameter("t"));
%>