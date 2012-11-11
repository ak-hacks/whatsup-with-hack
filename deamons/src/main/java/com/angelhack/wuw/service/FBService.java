/**
 * 
 */
package com.angelhack.wuw.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.restfb.types.User;

/**
 * @author anurag.kapur
 * 
 */
public class FBService {

	private static final Logger LOGGER = Logger.getLogger(FBService.class);

	private static final String MY_APP_ID = new String("259523980837415");

	private static final String MY_APP_SECRET = new String(
			"4091215d61a657016071bcbf66a5992a");

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
						+ uid + ") ";
				Connection<User> myFriends = facebookClient.fetchConnection(uid
						+ "/friends", User.class);
				List<com.angelhack.wuw.model.StatusMessage> lists = facebookClient
						.executeQuery(query,
								com.angelhack.wuw.model.StatusMessage.class);

				List<User> friendList = myFriends != null ? myFriends.getData()
						: null;

				Map<String, String> friendNames = new HashMap<String, String>();

				if (friendList != null)
					for (User user : friendList) {
						System.out.println(user);
						friendNames.put(user.getId(), user.getName());
					}

				System.out.println(lists.size());

				FacebookPostsDAO facebookPostsDAO = new FacebookPostsDAO();
				for (com.angelhack.wuw.model.StatusMessage statusMessage : lists) {

					if (statusMessage.getMessage() != null) {
						String from = friendNames.get(statusMessage.getUid()) != null ? friendNames
								.get(statusMessage.getUid()) : statusMessage
								.getUid();

						facebookPostsDAO.insertFacebookPost(
								statusMessage.getStatusId(),
								statusMessage.getMessage(),
								statusMessage.getTime(), from, uid);

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
		fbService.insertPosts();
	}
}
