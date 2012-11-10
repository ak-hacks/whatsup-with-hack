package ch.mbae.pusher.util;



import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;



/**
 * Static class to send messages to Pusher's REST API.
 * 
 * Please set pusherApplicationId, pusherApplicationKey, pusherApplicationSecret accordingly
 * before sending any request. 
 * 
 * @author Stephan Scheuermann
 * Copyright 2010. Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
public class PusherUtil {

	/**
	 *  Pusher Host name
	 */
	private final static String pusherHost = "api.pusherapp.com";
	
	
	/**
	 * Converts a byte array to a string representation
	 * @param data
	 * @return
	 */
	public static String byteArrayToString(byte[] data){
    	BigInteger bigInteger = new BigInteger(1,data);
    	String hash = bigInteger.toString(16);
    	// Zero pad it
    	while(hash.length() < 32 ){
    	  hash = "0" + hash;
    	}
    	return hash;
	}
	
	/**
	 * Returns a md5 representation of the given string
	 * @param data
	 * @return
	 */
	public static String md5Representation(String data) {
        try {
        	//Get MD5 MessageDigest
        	MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        	byte[] digest = messageDigest.digest(data.getBytes("US-ASCII"));
        	return byteArrayToString(digest);
        } catch (NoSuchAlgorithmException nsae) {
            //We should never come here, because GAE has a MD5 algorithm
        	throw new RuntimeException("No MD5 algorithm");
        } catch (UnsupportedEncodingException e) {
            //We should never come here, because UTF-8 should be available
        	throw new RuntimeException("No UTF-8");
		}	
	}
	
	/**
	 * Returns a HMAC/SHA256 representation of the given string
	 * @param data
	 * @return
	 */
    public static String hmacsha256Representation(String data, String pusherApplicationSecret) {
        try {
            // Create the HMAC/SHA256 key from application secret
            final SecretKeySpec signingKey = new SecretKeySpec( pusherApplicationSecret.getBytes(), "HmacSHA256");

            // Create the message authentication code (MAC)
            final Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            
            //Process and return data
            byte[] digest = mac.doFinal(data.getBytes("UTF-8"));
            digest = mac.doFinal(data.getBytes());
            //Convert to string
            BigInteger bigInteger = new BigInteger(1,digest);
    		return String.format("%0" + (digest.length << 1) + "x", bigInteger);            
        } catch (NoSuchAlgorithmException nsae) {
            //We should never come here, because GAE has HMac SHA256
        	throw new RuntimeException("No HMac SHA256 algorithm");
        } catch (UnsupportedEncodingException e) {
            //We should never come here, because UTF-8 should be available
        	throw new RuntimeException("No UTF-8");
		} catch (InvalidKeyException e) {
			throw new RuntimeException("Invalid key exception while converting to HMac SHA256");
		} 
    }	

    /**
     * Build query string that will be appended to the URI and HMAC/SHA256 encoded
     * @param eventName
     * @param jsonData
     * @return
     */
    public static String buildQuery(String eventName, String jsonData, String socketID, String pusherApplicationKey){
    	StringBuffer buffer = new StringBuffer();
    	//Auth_Key
    	buffer.append("auth_key=");
    	buffer.append(pusherApplicationKey);
    	//Timestamp
    	buffer.append("&auth_timestamp=");
    	buffer.append(System.currentTimeMillis() / 1000);
    	//Auth_version
    	buffer.append("&auth_version=1.0");
    	//MD5 body
    	buffer.append("&body_md5=");
    	buffer.append(md5Representation(jsonData));
    	//Event Name
    	buffer.append("&name=");
    	buffer.append(eventName);
		//Append socket id if set
		if (!socketID.isEmpty()) {
			buffer.append("&socket_id=");
			buffer.append(socketID);
		}
		//Return content of buffer
		return buffer.toString();
    }
    
    /**
     * Build path of the URI that is also required for Authentication
     * @return
     */
    public static String buildURIPath(String channelName, String pusherApplicationId){
    	StringBuffer buffer = new StringBuffer();
    	//Application ID
    	buffer.append("/apps/");
    	buffer.append(pusherApplicationId);
    	//Channel name
    	buffer.append("/channels/");
    	buffer.append(channelName);
    	//Event
    	buffer.append("/events");    	
		//Return content of buffer
		return buffer.toString();    	
    }
    
    /**
     * Build authentication signature to assure that our event is recognized by Pusher
     * @param uriPath
     * @param query
     * @return
     */
    public static String buildAuthenticationSignature(String uriPath, String query, String pusherApplicationSecret){
    	StringBuffer buffer = new StringBuffer();
    	//request method
    	buffer.append("POST\n");
    	//URI Path
    	buffer.append(uriPath);
    	buffer.append("\n");
    	//Query string
    	buffer.append(query);
    	//Encode data
    	String h = buffer.toString();
    	return hmacsha256Representation(h, pusherApplicationSecret);    	
    }
    
    /**
     * Build URI where request is send to
     * @param uriPath
     * @param query
     * @param signature
     * @return
     */
    public static URL buildURI(String uriPath, String query, String signature){
    	StringBuilder buffer = new StringBuilder();
    	//Protocol
    	buffer.append("http://");
    	//Host
    	buffer.append(pusherHost);
    	//URI Path
    	buffer.append(uriPath);
    	//Query string
    	buffer.append("?");
    	buffer.append(query);
    	//Authentication signature
    	buffer.append("&auth_signature=");
    	buffer.append(signature);
    	//Build URI
    	try {
			return new URL(buffer.toString());
		} catch (MalformedURLException e) {
			throw new RuntimeException("Malformed URI");
		}
    }
    

}
