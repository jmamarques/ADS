package com.ads.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * JMA - 09/12/2021 19:53
 **/
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidFormException extends RuntimeException {
    public InvalidFormException() {
    }

    public InvalidFormException(String message) {
        super(message);
    }

    public InvalidFormException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFormException(Throwable cause) {
        super(cause);
    }

    public InvalidFormException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
