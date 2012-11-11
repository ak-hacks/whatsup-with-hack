package com.angelhack.wuw.db;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.restfb.types.User;

public class FacebookPostsDAO {

	private static final Logger LOGGER = Logger
			.getLogger(FacebookPostsDAO.class);
	private static final String FACEBOOK_POSTS = "fbposts";
	private static final String FACEBOOK_USERS = "fbusers";

	private static final String MY_APP_ID = new String("259523980837415");

	private static final String MY_APP_SECRET = new String(
			"4091215d61a657016071bcbf66a5992a");

	public void insertFacebookPost(String statusId, String message, long time,
			String uid, String parentUid) {
		DBObject userObject = null;

		if (message != null) {

			BasicDBObject query = new BasicDBObject();
			query.put("_id", parentUid);

			try {
				DBCursor cursor = DBConnectionManager.getCollection(
						FACEBOOK_USERS).find(query);
				if (cursor.size() <= 0) {
					LOGGER.debug("No users found");
				}

				while (cursor.hasNext()) {
					userObject = cursor.next();
				}

				cursor.close();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (userObject != null) {

				try {

					List<String> tags = Arrays.asList(message.split(" "));

					JsonArray tagsJsonArray = new JsonArray(tags);
					JsonObject jsonObj = new JsonObject();

					jsonObj.put("_id", statusId);
					jsonObj.put("message", message);
					jsonObj.put("time", new Long(time));
					jsonObj.put("tags", tagsJsonArray);

					jsonObj.put("uid", uid);
					jsonObj.put("parentUid", parentUid);

					DBObject fbUserDbObj = (DBObject) JSON.parse(jsonObj
							.toString());
					LOGGER.debug("Inserting Facebook posts into DB :: "
							+ jsonObj.toString());

					DBConnectionManager.getCollection(FACEBOOK_POSTS).insert(
							fbUserDbObj);
				} catch (UnknownHostException e) {
					LOGGER.error("Exception occurred during insertion of data");
					e.printStackTrace();
				}
			}
		}
	}

	public List<DBObject> getFilteredFacebookPosts(String topic, String uid) {
		BasicDBObject query = new BasicDBObject();
		query.put("tags", topic);
		query.put("parentUid", uid);
		List<DBObject> posts = new ArrayList<DBObject>();

		try {
			DBCursor cursor = DBConnectionManager.getCollection(FACEBOOK_POSTS)
					.find(query);
			DBObject filteredPostObject = null;
			if (cursor.size() <= 0) {
				LOGGER.debug("No posts found");
			}

			while (cursor.hasNext()) {
				filteredPostObject = cursor.next();
				posts.add(filteredPostObject);
			}

			cursor.close();
		} catch (UnknownHostException e) {
			LOGGER.error("Exception occurred during retrieval of filtered data");
			e.printStackTrace();
		}

		return posts;
	}
}
