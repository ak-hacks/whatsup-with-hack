package com.angelhack.wuw.db;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.restfb.json.JsonObject;

public class FacebookUsersDAO {

	private static final Logger LOGGER = Logger
			.getLogger(FacebookUsersDAO.class);
	private static final String FACEBOOK_USERS = "fbusers";

	public void insertFacebookUser(String uid, String accessToken)
			throws UnknownHostException {

		JsonObject jsonObj = new JsonObject();

		jsonObj.put("_id", uid);
		jsonObj.put("accessToken", accessToken);

		DBObject fbUserDbObj = (DBObject) JSON.parse(jsonObj.toString());
		LOGGER.debug("Inserting Facebook user details into DB :: "
				+ jsonObj.toString());
		DBConnectionManager.getCollection(FACEBOOK_USERS).insert(fbUserDbObj);
	}
	

	public List<DBObject> getFacebookUsers() {
		BasicDBObject query = new BasicDBObject();
		List<DBObject> users = new ArrayList<DBObject>();

		try {
			DBCursor cursor = DBConnectionManager.getCollection(FACEBOOK_USERS)
					.find(query);
			DBObject userObject = null;
			if (cursor.size() <= 0) {
				LOGGER.debug("No users found");
			}

			while (cursor.hasNext()) {
				userObject = cursor.next();
				users.add(userObject);
			}

			cursor.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}
}
