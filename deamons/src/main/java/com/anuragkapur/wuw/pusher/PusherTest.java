/**
 * 
 */
package com.anuragkapur.wuw.pusher;

import ch.mbae.pusher.PusherChannel;
import ch.mbae.pusher.PusherTransportException;
import ch.mbae.pusher.transport.HttpClientPusherTransport;

/**
 * @author anurag.kapur
 * 
 */
public class PusherTest {

	private static String activityContent = "{\"imageUrl\":\"https://si0.twimg.com/profile_images/2409294832/image_normal.jpg\",\"name\":\"Anurag Kapur\",\"message\":\"And I am awesome too!\",\"time\":\"Nov 10, 1743\"}";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PusherChannel pusherCh = new PusherChannel("Obama", "31406", "255928ee415a950c8d77",
				"a22831d92f2ca5db7399", new HttpClientPusherTransport());
		try {
			pusherCh.pushEvent("activity", activityContent);
		} catch (PusherTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
