package com.ads.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * JMA - 24/11/2021 22:46
 **/
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidFormatException extends RuntimeException {
    public InvalidFormatException() {
    }

    public InvalidFormatException(String message) {
        super(message);
    }

    public InvalidFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFormatException(Throwable cause) {
        super(cause);
    }

    public InvalidFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
