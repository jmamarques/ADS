package com.ads.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * class that launch an exception in case of invalid format
 **/
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidFormatException extends RuntimeException {
    public InvalidFormatException() {
    }

    /**
     * constructor that launches the message
     * @param message
     */
    public InvalidFormatException(String message) {
        super(message);
    }

    /**
     * constructor that launches the message and cause
     * @param message
     * @param cause
     */
    public InvalidFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor that launches the cause
     * @param cause
     */
    public InvalidFormatException(Throwable cause) {
        super(cause);
    }

    /**
     * constructor that launches the message and cause
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public InvalidFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
