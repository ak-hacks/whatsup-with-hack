/**
 * 
 */
package com.angelhack.wuw.twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * @author anurag.kapur
 *
 */
public class FirehoseStream {

	private static final String PROTECTED_RESOURCE_URL = "https://stream.twitter.com/1.1/statuses/filter.json";
	private static final Logger LOGGER = Logger.getLogger(FirehoseStream.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OAuthService service = new ServiceBuilder().provider(TwitterApi.class)
				.apiKey("8irCJ4Im007PvChUyaWxQ")
				.apiSecret("5fea0b3mLWMWEYJ0KA48GkppnsvBFKRrd9VwTiJWY")
				.build();

		Token accessToken = new Token(
				"14757382-LS4cHhQSjt5AogsTL4YpIPuYuDJPlvjQI3hKIGgnI",
				"OCzWvUfIKb7wYUlxZYYi6Muct4ry51cNeVG0aDk4");
		
		OAuthRequest request = new OAuthRequest(Verb.POST,
				PROTECTED_RESOURCE_URL);
		request.addPayload("track=Obama");
		service.signRequest(accessToken, request);
		
		Response response = request.send();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getStream(), "UTF-8"));
			while (true) {
				String line = br.readLine();
				if (line != null && line.length() > 0) {
					LOGGER.debug(line);
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}