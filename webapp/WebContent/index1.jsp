<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
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
      .container-narrow > hr {
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
      .marketing p + h4 {
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
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
  </head>

  <body>
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
                    accessTokenElem.value = response.authResponse.accessToken; 
                    document.getElementById('wuwForm').submit();
                } else {

                }
            });

        };

        function login() {
            FB.login(function(response) {
                                if (response.authResponse) {
                                    var uidElem = document
                                            .getElementById('uid');
                                    uidElem.value = response.authResponse.userID;

                                     var accessTokenElem = document
                                            .getElementById('accessToken');
                                    accessTokenElem.value = response.authResponse.accessToken; 
                                    document.getElementById('wuwForm').submit();
                                    
                                } else {

                                }
                            },
                            {
                                scope : 'user_activities,user_interests,user_likes,user_events,friends_activities,friends_interests,friends_likes,friends_events,user_actions.video,user_actions.news,user_actions.music,friends_actions.video,friends_actions.news, friends_actions.music,read_stream'
                            });
            /* if (FB.getAuthResponse()) {

                document.getElementById('wuwForm').submit();
                
            }    */
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

		<form method="POST" action='FacebookUsers' id="wuwForm">
			<input type="hidden" id="uid" name="uid"> <input
				type="hidden" id="accessToken" name="accessToken">
			<div class="jumbotron">
				<h1>Trending On What's Up With Today</h1>
				<p class="lead">Login with Facebook and then checkout a topic
					from the list below</p>
				<a class="btn btn-large btn-success" href="#" onclick="login()">Login
					With Facebook</a>
			</div>
		</form>

		<hr>

      <div class="row-fluid marketing">
        <div class="span6">
          <h4><a href="topic.jsp?t=Obama">Obama</a></h4>
        </div>

        <div class="span6">
          <h4><a href="topic.jsp?t=Romney">Romney</a></h4>
        </div>
      </div>
      <div class="row-fluid marketing">
        <div class="span6">
          <h4><a href="topic.jsp?t=AngelHack">Angel Hack</a></h4>
        </div>

        <div class="span6">
          <h4>...</h4>
        </div>
      </div>
      
      <hr>

	<div class="footer">
		<p>&copy; What's Up With... Developed at Angel Hack, London, November 2012. Team Members: <a href="https://twitter.com/anuragkapur">Anurag Kapur</a> and <a href="https://twitter.com/v0idnnull">Abhinav Gupta</a></p>
	</div>

    </div> <!-- /container -->

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