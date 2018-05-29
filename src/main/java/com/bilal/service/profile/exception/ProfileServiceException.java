package com.bilal.service.profile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProfileServiceException extends RuntimeException {

    public ProfileServiceException() {
        super();
    }

    public ProfileServiceException(String message) {
        super(message);
    }
}
