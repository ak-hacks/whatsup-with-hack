<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
				
			} 	 */
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
	<form method="POST" action='FacebookUsers' id="wuwForm">
		<p>
			<input type="hidden" id="uid" name="uid"> <input
				type="hidden" id="accessToken" name="accessToken"> <input
				type="button" value="Login using Facebook" id="submitForm"
				onclick="login()" />
		</p>
	</form>
</body>
</html>