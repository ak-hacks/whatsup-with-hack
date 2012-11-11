<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.angelhack.wuw.google.*" %>
<%@ page import="com.sun.syndication.feed.synd.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.io.*" %>
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
				<h4>Popular On Twittersphere</h4>
				<ul id="activity_stream_example" class="activity-stream"></ul>
			</div>
			<div class="span6">
				<h4>Expert Views</h4>
				<%
				NewsSearch search = new NewsSearch();
				List results = search.getResults(request.getParameter("t"));
				int count = 0;
				for (Iterator i = results.iterator(); i.hasNext();) {
					count ++;
					if(count > 6) {
						break;
					}
					SyndEntry entry = (SyndEntry) i.next();
					String title = entry.getTitle();
					String source = title.substring(title.indexOf(" - "));
					%>
					<p>
					<%=entry.getTitle()%>
					<a href="<%=entry.getLink()%>"><%=source%></a>
					</p>
					<%
				}
				%>
			</div>
		</div>
		<div class="row-fluid marketing">
			<div class="span6">
				<h4>Interesting Statistics</h4>
				<%
				String stats = "";
				try{
					URL url = new URL("http://otter.topsy.com/searchcount.json?q="+ URLEncoder.encode(request.getParameter("t"),"UTF-8"));
					URLConnection connection = url.openConnection();
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					StringBuffer responseBuffer = new StringBuffer();
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						responseBuffer.append(inputLine);
					}
					stats = responseBuffer.toString();
				}catch(Exception e) {
					out.println("Something went wrong :(");
				}
				
				%>
				<p><%--= stats--%></p>
			</div>
			<div class="span6">
			
				<h4>What your friends on Facebook say...</h4>
				
				<%
				session.getAttribute("statusMessageList");
				
				
				%>
				<c:forEach var="statusMessage" items="${sessionScope.statusMessageList}">
				
				    <p>
                       <a href='#'> <c:out value="${statusMessage.uid}" /></a> says <c:out value="${statusMessage.message}" />
                    </p>
				</c:forEach>
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