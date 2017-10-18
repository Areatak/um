package com.areatak.gazette.exception;

/**
 * Created by alirezaghias on 3/13/2017 AD.
 */
public class AuthException extends Exception {
    public AuthException() {
        super("Not a valid token");
    }
}
