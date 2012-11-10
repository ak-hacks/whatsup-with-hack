/**
 * Author: marcbaechinger
 * Copyright 2011. Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package ch.mbae.pusher;

import java.net.URL;
import java.util.Map;

/**
 * 
 * @author marcbaechinger
 */
public class PusherResponse {
    private byte[] content;
    private URL url;
    private int responseCode;
    private Map<String,String> headers;

    /**
     * @return the content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * @return the url
     */
    public URL getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * @return the responseCode
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return the headers
     */
    public Map<String,String> getHeaders() {
        return headers;
    }

    /**
     * @param headers the headers to set
     */
    public void setHeaders(Map<String,String> headers) {
        this.headers = headers;
    }
}
