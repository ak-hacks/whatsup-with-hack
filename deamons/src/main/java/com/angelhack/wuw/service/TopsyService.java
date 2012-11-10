/**
 * 
 */
package com.angelhack.wuw.service;

import java.net.UnknownHostException;
import java.util.ListIterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.angelhack.wuw.db.TopTweetsDAO;
import com.angelhack.wuw.topsy.TopsyClient;

/**
 * 
 * @author anurag.kapur
 */
public class TopsyService {
	
	private static void doPeriodicWork() {
		String[] topics = {"Obama","Romney", "Angel Hack"};
		
		for (String topic : topics) {
			TopsyClient topsyClient = new TopsyClient();
			JSONObject jsonResponse = topsyClient.getResponse(topic);
			
			TopsyService topsyService = new TopsyService();
			topsyService.processTopsyResponse(jsonResponse, topic);
		}
	}

	private void processTopsyResponse(JSONObject jsonResponse, String topic) {
		JSONArray topTweets = (JSONArray)jsonResponse.get("list");
		@SuppressWarnings("unchecked")
		ListIterator<JSONObject> iterator = topTweets.listIterator();
		TopTweetsDAO topTweetsDao = new TopTweetsDAO();
		
		int count = 0;
		
		while (iterator.hasNext()) {
			count ++;
			JSONObject jsonObject = (JSONObject) iterator.next();
			try {
				topTweetsDao.insertTweet(jsonObject.toJSONString(), topic, topic+count);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors
				.newSingleThreadScheduledExecutor();

		Runnable periodicTask = new Runnable() {
			public void run() {
				// Invoke method(s) to do the work
				doPeriodicWork();
			}
		};
		
		executor.scheduleWithFixedDelay(periodicTask, 0, 3, TimeUnit.SECONDS);
	}
}