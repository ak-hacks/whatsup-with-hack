/**
 * 
 */
package com.angelhack.wuw.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.angelhack.wuw.db.FacebookPostsDAO;
import com.angelhack.wuw.db.FacebookUsersDAO;
import com.angelhack.wuw.model.StatusMessage;
import com.mongodb.DBObject;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;

/**
 * @author anurag.kapur
 * 
 */
public class FBService {

	private static final Logger LOGGER = Logger.getLogger(FBService.class);

	private static final String MY_APP_ID = new String("259523980837415");

	private static final String MY_APP_SECRET = new String(
			"4091215d61a657016071bcbf66a5992a");

	public List<DBObject> insertPostsForUser() {
		FacebookUsersDAO dao = new FacebookUsersDAO();
		List<DBObject> users = dao.getFacebookUsers();
		for (DBObject dbObject : users) {
			insertPosts((Integer) dbObject.get("_id"),
					(String) dbObject.get("accessToken"));
		}
		return users;
	}

	public void insertPosts(Integer uid, String accessTokenString) {

		if (accessTokenString == null || uid == null
				|| "".equals(accessTokenString.trim())) {

			LOGGER.error("Uh oh, uid or the access token is not available");

		} else {

			// Tells Facebook to extend the lifetime of MY_ACCESS_TOKEN.
			// Facebook may return the same token or a new one.
			AccessToken accessToken = new DefaultFacebookClient()
					.obtainExtendedAccessToken(MY_APP_ID, MY_APP_SECRET,
							accessTokenString);

			FacebookClient facebookClient = new DefaultFacebookClient(
					accessToken.getAccessToken());

			try {

				long time = System.currentTimeMillis() - (14 * 24 * 60 * 60);
				String query = "SELECT status_id, message, time FROM status WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 ="
						+ uid.intValue() + " and time > " + time + ") ";
				Connection<StatusMessage> friendsStatusMessages = facebookClient
						.fetchConnection("fql", StatusMessage.class,
								Parameter.with("q", query));

				FacebookPostsDAO facebookPostsDAO = new FacebookPostsDAO();
				for (List<StatusMessage> statusMessages : friendsStatusMessages) {
					for (StatusMessage statusMessage : statusMessages) {

						if (statusMessage.getMessage() != null) {
							facebookPostsDAO.insertFacebookPost(
									statusMessage.getStatusId(),
									statusMessage.getMessage(),
									statusMessage.getTime());
						}

					}
				}

			} catch (FacebookException ex) {
				LOGGER.error("Error: Couldn't connect to Facebook ", ex);

			}
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FBService fbService = new FBService();
		fbService.insertPostsForUser();
	}
}
