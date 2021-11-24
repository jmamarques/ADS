package com.ads.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * JMA - 24/11/2021 23:16
 **/
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidFileException extends RuntimeException {
    public InvalidFileException() {
    }

    public InvalidFileException(String message) {
        super(message);
    }

    public InvalidFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFileException(Throwable cause) {
        super(cause);
    }

    public InvalidFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
