/**
 * Author: marcbaechinger
 * Copyright 2011. Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package ch.mbae.pusher;

/**
 * 
 * @author marcbaechinger
 */
public class PusherTransportException extends Exception {

    public PusherTransportException(String msg, Throwable th) {
        super(msg, th);
    }
    public PusherTransportException(Throwable th) {
        super(th);
    }
    public PusherTransportException(String msg) {
        super(msg);
    }
}
