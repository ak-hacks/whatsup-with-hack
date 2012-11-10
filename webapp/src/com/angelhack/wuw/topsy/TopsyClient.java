/**
 * 
 */
package com.angelhack.wuw.topsy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * @author anurag.kapur
 * 
 */
public class TopsyClient {

	private static final Logger LOGGER = Logger.getLogger(TopsyClient.class);
	
	public JSONObject getResponse(String query) {
		
		BufferedReader in = null;
		JSONObject jsonResponse = null;
		
		try {
			URL url = new URL("http://otter.topsy.com/search.json?q="+ URLEncoder.encode(query,"UTF-8") +"ipad&window=dynamic");
			URLConnection connection = url.openConnection();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer responseBuffer = new StringBuffer();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				responseBuffer.append(inputLine);
			}
			
			Object obj = JSONValue.parse(responseBuffer.toString());
			if(obj.getClass().equals(JSONObject.class)) {
				JSONObject jsonObject = (JSONObject)obj;
				jsonResponse = (JSONObject) jsonObject.get("response");
				LOGGER.debug(jsonResponse);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonResponse;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TopsyClient topsy = new TopsyClient();
		topsy.getResponse("ipad");
	}
}
