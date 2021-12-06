package com.ads.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * class that launch an exception in case of invalid file
 **/
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidFileException extends RuntimeException {
    public InvalidFileException() {
    }

    /**
     * constructor that launches the message
     * @param message
     */
    public InvalidFileException(String message) {
        super(message);
    }

    /**
     * constructor that launches the message and cause
     * @param message
     * @param cause
     */
    public InvalidFileException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor that launches the cause
     * @param cause
     */
    public InvalidFileException(Throwable cause) {
        super(cause);
    }

    /**
     * constructor that launches the message and cause
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public InvalidFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
