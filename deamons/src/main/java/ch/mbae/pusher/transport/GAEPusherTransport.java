package ch.mbae.pusher.transport;

import ch.mbae.pusher.PusherTransportException;
import ch.mbae.pusher.PusherTransport;
import ch.mbae.pusher.PusherResponse;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Transport implementation for GAE.
 * 
 * @author Stephan Scheuermann
 * Copyright 2010. Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
public class GAEPusherTransport implements PusherTransport {

    private static final Logger LOGGER = Logger.getLogger(GAEPusherTransport.class);
            
    public PusherResponse fetch(URL url, String jsonData) throws PusherTransportException {
        //Create Google APP Engine Fetch URL service and request
        URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();
        HTTPRequest request = new HTTPRequest(url, HTTPMethod.POST);
        request.addHeader(new HTTPHeader("Content-Type", "application/json"));
        request.setPayload(jsonData.getBytes());

        //Start request
        try {
                HTTPResponse httpResponse = urlFetchService.fetch(request);
                PusherResponse response = new PusherResponse();
                response.setContent(httpResponse.getContent());
                response.setResponseCode(httpResponse.getResponseCode());
                response.setHeaders(this.extractHeaders(httpResponse));
                
                return response;
        } catch (IOException e) {
            throw new PusherTransportException("exception while POSTing payload using GAE transport", e);
        }
    }

    /**
     * copies GAE headers to string map
     * 
     * @param httpResponse the GAU response
     * @return a <code>Map<String,String></code> containing the http headers
     */
    private Map<String, String> extractHeaders(HTTPResponse httpResponse) {
        Map<String,String> headers = new HashMap<String, String>();
        for (HTTPHeader header : httpResponse.getHeaders()) {
            headers.put(header.getName(), header.getValue());
        }
        return headers;
    }
    
}
