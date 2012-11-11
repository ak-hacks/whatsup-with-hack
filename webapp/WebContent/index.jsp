<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.angelhack.wuw.google.*"%>
<%@ page import="com.sun.syndication.feed.synd.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="java.io.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>What's Up With...</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="assets/css/bootstrap.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 20px;
	padding-bottom: 40px;
}

/* Custom container */
.container-narrow {
	margin: 0 auto;
	max-width: 700px;
}

.container-narrow>hr {
	margin: 30px 0;
}

/* Main marketing message and sign up button */
.jumbotron {
	margin: 60px 0;
	text-align: center;
}

.jumbotron h1 {
	font-size: 72px;
	line-height: 1;
}

.jumbotron .btn {
	font-size: 21px;
	padding: 14px 24px;
}

/* Supporting marketing content */
.marketing {
	margin: 60px 0;
}

.marketing p+h4 {
	margin-top: 28px;
}
</style>
<link href="assets/css/bootstrap-responsive.css" rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Fav and touch icons -->
<link rel="shortcut icon" href="assets/ico/favicon.ico">
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
    <div id="fb-root"></div>
	<script>
		// Additional JS functions here
		window.fbAsyncInit = function() {
			FB.init({
				appId : '259523980837415', // App ID
				channelUrl : 'channel.html', // Channel File

				status : true, // check login status
				cookie : true, // enable cookies to allow the server to access the session
				xfbml : true, // parse XFBML
				oauth : true
			});

			// Additional init code here

			FB.getLoginStatus(function(response) {
				if (response.status === 'connected') {

					var uidElem = document.getElementById('uid');
					uidElem.value = response.authResponse.userID;

					var accessTokenElem = document
							.getElementById('accessToken');
					
					if (accessTokenElem != null) {
					accessTokenElem.value = response.authResponse.accessToken;
					document.getElementById('wuwForm').submit();
					}else{}
					
				} else {

				}
			});

		};

		function login() {
			FB
					.login(
							function(response) {
								if (response.authResponse) {
									var uidElem = document
											.getElementById('uid');
									uidElem.value = response.authResponse.userID;

									var accessTokenElem = document
											.getElementById('accessToken');
					                 if (accessTokenElem != null) {
					                     accessTokenElem.value = response.authResponse.accessToken;
					                     document.getElementById('wuwForm').submit();
					                     }else {}

								} else {

								}
							},
							{
								scope : 'user_activities,user_interests,user_likes,user_events,friends_activities,friends_interests,friends_likes,friends_events,user_actions.video,user_actions.news,user_actions.music,friends_actions.video,friends_actions.news, friends_actions.music,read_stream'
							});

		}


		function pushData(str) {
			document.getElementById('t').value = str;
			document.getElementById('fbForm').submit();
			//$('#fbPostsForm').submit(function(){alert('In the getposts method');});
		}

		// Load the SDK Asynchronously
		(function(d) {
			var js, id = 'facebook-jssdk', ref = d
					.getElementsByTagName('script')[0];
			if (d.getElementById(id)) {
				return;
			}
			js = d.createElement('script');
			js.id = id;
			js.async = true;
			js.src = "//connect.facebook.net/en_US/all.js";
			ref.parentNode.insertBefore(js, ref);
		}(document));
	</script>
	<div class="container-narrow">

		<div class="masthead">
			<ul class="nav nav-pills pull-right">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="#">About</a></li>
				<li><a href="#">Contact</a></li>
			</ul>
			<h3 class="muted">What's Up With</h3>
		</div>

		<hr>
		<div class="jumbotron">
			<h1>Trending On What's Up With Today</h1>
			<c:if test="${param.loggedIn ne 'yes'}">
				<form method="POST" action='FacebookUsers' id="wuwForm">
					<input type="hidden" id="uid" name="uid"> <input
						type="hidden" id="accessToken" name="accessToken">

					<p class="lead">Login with Facebook and then checkout a topic
						from the list below</p>
					<a class="btn btn-large btn-success" href="#" onclick="login()">Login
						With Facebook</a>

				</form>
			</c:if>
		</div>
		<hr>

		<div class="row-fluid marketing">
			<div class="span6">
				<h4>
					<a href="#" onclick="pushData('Obama')">Obama</a>
				</h4>
			</div>

			<div class="span6">
				<h4>
					<a href="#" onclick="pushData('Romney')">Romney</a>
				</h4>
			</div>
		</div>
		<div class="row-fluid marketing">
			<div class="span6">
				<h4>
					<a href="#" onclick="pushData('AngelHack')">Angel
						Hack</a>
				</h4>
			</div>

			<div class="span6">
				<h4>...</h4>
			</div>
			<form method="POST" action='FacebookPosts' id="fbForm">
				<input type="hidden" id="uid" name="uid" />
				<input type="hidden" id="t" name="t" />
			</form>

		</div>

<!-- 


<div class="row-fluid marketing">
            <div class="span6">
                <h4>
                    <a href="topic.jsp?t=Obama" onclick="pushData('Obama')">Obama</a>
                </h4>
            </div>

            <div class="span6">
                <h4>
                    <a href="topic.jsp?t=Romney" onclick="pushData('Romney')">Romney</a>
                </h4>
            </div>
        </div>
        <div class="row-fluid marketing">
            <div class="span6">
                <h4>
                    <a href="topic.jsp?t=AngelHack" onclick="pushData('AngelHack')">Angel
                        Hack</a>
                </h4>
            </div>

            <div class="span6">
                <h4>...</h4>
            </div>
            <form method="POST" action='FacebookPosts' id="fbForm">
                <input type="hidden" id="uid" name="uid" />
            </form>

        </div>
 -->

		<hr>

		<div class="footer">
			<p>
				&copy; What's Up With... Developed at Angel Hack, London, November
				2012. Team Members: <a href="https://twitter.com/anuragkapur">Anurag
					Kapur</a> and <a href="https://twitter.com/v0idnnull">Abhinav Gupta</a>
			</p>
		</div>

	</div>
	<!-- /container -->

</body>

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
</html>