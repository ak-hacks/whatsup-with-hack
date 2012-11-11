package com.angelhack.wuw.db;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;

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

		jsonObj.put("parentUserId", uid);
		jsonObj.put("accessToken", accessToken);

		DBObject fbUserDbObj = (DBObject) JSON.parse(jsonObj.toString());
		LOGGER.debug("Inserting Facebook user details into DB :: "
				+ jsonObj.toString());
		DBConnectionManager.getCollection(FACEBOOK_USERS).insert(fbUserDbObj);
	}
}
