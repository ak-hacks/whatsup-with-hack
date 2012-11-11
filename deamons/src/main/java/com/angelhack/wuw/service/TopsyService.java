/**
 * 
 */
package com.angelhack.wuw.service;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ch.mbae.pusher.PusherChannel;
import ch.mbae.pusher.PusherTransportException;
import ch.mbae.pusher.transport.HttpClientPusherTransport;

import com.angelhack.wuw.db.TopTweetsDAO;
import com.angelhack.wuw.topsy.TopsyClient;

/**
 * 
 * @author anurag.kapur
 */
public class TopsyService {

	private static String[] topics = { "Obama", "Romney", "AngelHack" };
	private static final String PUSHER_APP_ID = "31406";
	private static final String PUSHER_KEY = "255928ee415a950c8d77";
	private static final String PUSHER_SECRET = "a22831d92f2ca5db7399";

	private static void doTopsyPusher() {
		for (String topic : topics) {
			PusherChannel pusherCh = new PusherChannel(topic, PUSHER_APP_ID, PUSHER_KEY, PUSHER_SECRET,
					new HttpClientPusherTransport());
			TopTweetsDAO topTweetsDao = new TopTweetsDAO();
			try {
				List<String> tweets = topTweetsDao.getTweets(topic);
				Iterator<String> iterator = tweets.iterator();
				while (iterator.hasNext()) {
					String tweet = (String) iterator.next();
					try {
						pusherCh.pushEvent("activity", tweet);
						Thread.sleep(1000);
					} catch (PusherTransportException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void doTopsyQuery() {
		for (String topic : topics) {
			TopsyClient topsyClient = new TopsyClient();
			JSONObject jsonResponse = topsyClient.getResponse(topic);

			TopsyService topsyService = new TopsyService();
			topsyService.processTopsyResponse(jsonResponse, topic);
		}
	}

	private void processTopsyResponse(JSONObject jsonResponse, String topic) {
		JSONArray topTweets = (JSONArray) jsonResponse.get("list");
		@SuppressWarnings("unchecked")
		ListIterator<JSONObject> iterator = topTweets.listIterator();
		TopTweetsDAO topTweetsDao = new TopTweetsDAO();

		int count = 0;

		while (iterator.hasNext()) {
			count++;
			JSONObject jsonObject = (JSONObject) iterator.next();
			try {
				topTweetsDao.insertTweet(jsonObject.toJSONString(), topic, topic + count);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Topsy query
		ScheduledExecutorService topsyQueryExecutor = Executors.newSingleThreadScheduledExecutor();

		Runnable topsyQueryTask = new Runnable() {
			public void run() {
				// Invoke method(s) to do the work
				doTopsyQuery();
			}
		};
		topsyQueryExecutor.scheduleWithFixedDelay(topsyQueryTask, 0, 10, TimeUnit.MINUTES);

		// Topsy pusher
		ScheduledExecutorService topsyPusherExecutor = Executors.newSingleThreadScheduledExecutor();

		Runnable topsyPusherTask = new Runnable() {
			public void run() {
				// Invoke method(s) to do the work
				doTopsyPusher();
			}
		};
		topsyPusherExecutor.scheduleWithFixedDelay(topsyPusherTask, 0, 60, TimeUnit.SECONDS);

	}
}