package com.angelhack.wuw.db;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

public class FacebookPostsDAO {

	private static final Logger LOGGER = Logger
			.getLogger(FacebookPostsDAO.class);
	private static final String FACEBOOK_POSTS = "fbposts";

	public void insertFacebookPost(int statusId, String message, long time) {

		if (message != null) {

			List<String> tags = Arrays.asList(message.split(" "));

			JsonArray tagsJsonArray = new JsonArray();

			tagsJsonArray.put(tags);
			JsonObject jsonObj = new JsonObject();

			jsonObj.put("_id", new Integer(statusId));
			jsonObj.put("message", message);
			jsonObj.put("time", new Long(time));
			jsonObj.put("tags", tagsJsonArray);

			DBObject fbUserDbObj = (DBObject) JSON.parse(jsonObj.toString());
			LOGGER.debug("Inserting Facebook posts into DB :: "
					+ jsonObj.toString());
			try {
				DBConnectionManager.getCollection(FACEBOOK_POSTS).insert(
						fbUserDbObj);
			} catch (UnknownHostException e) {
				LOGGER.error("Exception occurred during insertion of data");
				e.printStackTrace();
			}
		}
	}

	public List<DBObject> getFilteredFacebookPosts() {
		BasicDBObject query = new BasicDBObject();
		List<DBObject> posts = new ArrayList<DBObject>();

		try {
			DBCursor cursor = DBConnectionManager.getCollection(FACEBOOK_POSTS)
					.find(query);
			DBObject filteredPostObject = null;
			if (cursor.size() <= 0) {
				LOGGER.debug("No users found");
			}

			while (cursor.hasNext()) {
				filteredPostObject = cursor.next();
				posts.add(filteredPostObject);
			}

			cursor.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return posts;
	}
}
