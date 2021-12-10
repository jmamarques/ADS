package com.ads.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * JMA - 10/12/2021 01:07
 **/
@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class InvalidAlgorithmException extends RuntimeException {
    public InvalidAlgorithmException() {
    }

    public InvalidAlgorithmException(String message) {
        super(message);
    }

    public InvalidAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAlgorithmException(Throwable cause) {
        super(cause);
    }

    public InvalidAlgorithmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
