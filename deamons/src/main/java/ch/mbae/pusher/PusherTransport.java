/**
 * Author: Stephan Scheuermann, marcbaechinger
 * Copyright 2011. Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package ch.mbae.pusher;

import java.net.URL;

/**
 * Interface to encapsulate doing the http transport by a given http transport implementation
 * as GAE URLFetchService, Jakarta Commons HttpClient or whatever ever.
 */
public interface PusherTransport {
    PusherResponse fetch(URL url, String jsonData) throws PusherTransportException;
}
