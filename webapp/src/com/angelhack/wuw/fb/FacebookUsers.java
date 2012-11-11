package com.angelhack.wuw.fb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.angelhack.wuw.db.FacebookPostsDAO;
import com.angelhack.wuw.db.FacebookUsersDAO;
import com.mongodb.DBObject;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;


/**
 * Servlet implementation class FacebookUsers
 */
@WebServlet("/FacebookUsers")
public class FacebookUsers extends HttpServlet {
	
	private static final String MY_APP_ID = new String("259523980837415");

	private static final String MY_APP_SECRET = new String(
			"4091215d61a657016071bcbf66a5992a");

	private static final Logger LOGGER = Logger
			.getLogger(FacebookUsers.class);

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -4165097819968116835L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FacebookUsers() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// empty method
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		PrintWriter servletOutput = res.getWriter();

		String uid = req.getParameter("uid");
		String accessTokenString = req.getParameter("accessToken");
		

		if (accessTokenString == null || uid == null
				|| "".equals(accessTokenString.trim()) || "".equals(uid.trim())) {

			System.err.println("Uh oh, access token is not available");
			servletOutput.println("Uh oh, something's wrong :(");

		} else {

			FacebookUsersDAO facebookUsersDAO = new FacebookUsersDAO();
			facebookUsersDAO.insertFacebookUser(uid, accessTokenString);
		}
		
		res.sendRedirect("/wuw/index.jsp?loggedIn=yes");
		servletOutput.close();
	}
	
	public List<DBObject> insertPosts() {
		FacebookUsersDAO dao = new FacebookUsersDAO();
		List<DBObject> users = dao.getFacebookUsers();
		for (DBObject dbObject : users) {
			insertPostsForUser((String) dbObject.get("_id"),
					(String) dbObject.get("accessToken"));
		}
		return users;
	}

	public void insertPostsForUser(String uid, String accessTokenString) {

		if (accessTokenString == null || uid == null
				|| "".equals(accessTokenString.trim())) {

			LOGGER.error("Uh oh, uid or the access token is not available");

		} else {

			try {
				// Tells Facebook to extend the lifetime of MY_ACCESS_TOKEN.
				// Facebook may return the same token or a new one.
				AccessToken accessToken = new DefaultFacebookClient()
						.obtainExtendedAccessToken(MY_APP_ID, MY_APP_SECRET,
								accessTokenString);

				FacebookClient facebookClient = new DefaultFacebookClient(
						accessToken.getAccessToken());

				String query = "SELECT status_id, message, time, uid FROM status WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 ="
						+ uid  + ") ";
//				Connection<StatusMessage> friendsStatusMessages = facebookClient
//						.fetchConnection("fql", StatusMessage.class,
//								Parameter.with("q", query));
//				System.out.println(friendsStatusMessages.getData().size());
				
				List<com.angelhack.wuw.model.StatusMessage> lists =  facebookClient
						.executeQuery(query, com.angelhack.wuw.model.StatusMessage.class);	
				
				System.out.println(lists.size());

				FacebookPostsDAO facebookPostsDAO = new FacebookPostsDAO();
					for (com.angelhack.wuw.model.StatusMessage statusMessage : lists) {

						if (statusMessage.getMessage() != null) {
							facebookPostsDAO.insertFacebookPost(
									statusMessage.getStatusId(),
									statusMessage.getMessage(),
									statusMessage.getTime(), statusMessage.getUid(), uid);
							
						}

				}

			} catch (FacebookException ex) {
				LOGGER.error("Error: Couldn't connect to Facebook ", ex);

			}
		}

	}

}
