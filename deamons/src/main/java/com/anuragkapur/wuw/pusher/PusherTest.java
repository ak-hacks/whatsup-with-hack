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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PusherChannel pusherCh = new PusherChannel("test_channel", "31406", "255928ee415a950c8d77",
				"a22831d92f2ca5db7399", new HttpClientPusherTransport());
		try {
			pusherCh.pushEvent("my_event", "{'foo':'bar'}");
		} catch (PusherTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
