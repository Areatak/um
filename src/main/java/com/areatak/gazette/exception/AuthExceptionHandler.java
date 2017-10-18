package com.areatak.gazette.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by alirezaghias on 3/13/2017 AD.
 */
@ControllerAdvice
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ AuthException.class })
    public ResponseEntity<Object> handleAuthException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Not a valid Token", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }


}

