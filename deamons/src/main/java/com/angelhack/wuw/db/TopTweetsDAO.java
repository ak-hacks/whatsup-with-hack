/**
 * 
 */
package com.angelhack.wuw.db;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
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
		LOGGER.debug("Inserting tweet into DB :: " + tweetObj.toString());
		DBConnectionManager.getCollection(TOP_TWEETS_COLLECTION_NAME).insert(tweetObj);
	}
	
	public List<String> getTweets(String topic) throws UnknownHostException {
		BasicDBObject query = new BasicDBObject();
		query.put("topicName", topic);
		DBCursor cursor = DBConnectionManager.getCollection(TOP_TWEETS_COLLECTION_NAME).find(query);
		
		DBObject tweetObject = null;
		if (cursor.size() <= 0) {
			LOGGER.debug("No top tweets for topic :: " + topic);
		}
		List<String> tweets = new ArrayList<String>();
		
		while (cursor.hasNext()) {
			tweetObject = cursor.next();
			tweets.add(tweetObject.toString());
		}
		return tweets;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TopTweetsDAO dao = new TopTweetsDAO();
		try {
			dao.getTweets("Obama");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
