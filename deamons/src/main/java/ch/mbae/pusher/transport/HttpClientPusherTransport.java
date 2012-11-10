/**
 * Author: marcbaechinger
 * Copyright 2011. Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package ch.mbae.pusher.transport;

import ch.mbae.pusher.PusherTransport;
import ch.mbae.pusher.PusherResponse;
import ch.mbae.pusher.PusherTransportException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

/**
 * Implementation of PusherTransport using Jakarta HttpClient
 * @author marcbaechinger
 */
public class HttpClientPusherTransport implements PusherTransport {
    
    DefaultHttpClient httpClient;
        
    /**
     * create a  http transportn using a single http client
     * for all requests
     */
    public HttpClientPusherTransport() {
        ClientConnectionManager cm = new ThreadSafeClientConnManager();

        this.httpClient =  new DefaultHttpClient(cm);
        this.httpClient.getParams().setParameter(
                CoreProtocolPNames.PROTOCOL_VERSION, 
                HttpVersion.HTTP_1_1); // set default to HTTP 1.1
        this.httpClient.getParams().setParameter(
                CoreProtocolPNames.HTTP_CONTENT_CHARSET, 
                "UTF-8");
    }
    
    public PusherResponse fetch(URL url, String jsonData) throws PusherTransportException {
        PusherResponse response = new PusherResponse();
        try {
            HttpPost post = new HttpPost(url.toURI());
            
            post.addHeader("Content-Type", "application/json");
            post.setEntity(new StringEntity(jsonData));
            // executr post request
            HttpResponse httpResponse = this.httpClient.execute(post);
            
            // get the content
            response.setContent(EntityUtils.toByteArray(httpResponse.getEntity()));
            // extract and set headers
            response.setHeaders(this.extractHeaders(httpResponse));
            // set http status
            response.setResponseCode(httpResponse.getStatusLine().getStatusCode());
            
        } catch (URISyntaxException ex) {
            throw new PusherTransportException("bad uri syntax", ex);
        } catch (ClientProtocolException ex) {
            throw new PusherTransportException("bad client protocol", ex);
        } catch (IOException ex) {
            throw new PusherTransportException("i/o failed", ex);
        }
        
        return response;
    }

    private Map<String,String> extractHeaders(HttpResponse httpResponse) {
        
        Map<String, String> headers = new HashMap<String, String>();
        for (Header header : httpResponse.getAllHeaders()) {
            headers.put(header.getName(), header.getValue());
        }
        
        return headers;
    }
}
