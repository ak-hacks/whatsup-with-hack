/**
 * 
 */
package com.angelhack.wuw.db;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author anurag.kapur
 *
 */
public class TopTweetsDAO {

	private static final Logger LOGGER = Logger.getLogger(TopTweetsDAO.class);
	private static final String TOP_TWEETS_COLLECTION_NAME = "toptweets";
	
	public void insertTweet(String jsonString, String topic, String id) throws UnknownHostException {
		DBObject tweetObj = (DBObject) JSON.parse(jsonString);
		tweetObj.put("topicName", topic);
		tweetObj.put("_id", id);
		LOGGER.info("Inserting tweet into DB :: " + tweetObj.toString());
		DBConnectionManager.getCollection(TOP_TWEETS_COLLECTION_NAME).insert(tweetObj);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
