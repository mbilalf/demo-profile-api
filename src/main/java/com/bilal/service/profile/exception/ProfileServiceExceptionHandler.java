package com.bilal.service.profile.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProfileServiceExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<JsonExceptionResponse> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(
                JsonExceptionResponse.builder().code(HttpStatus.NOT_FOUND.value()).message(e.getMessage()).build(),
                getDefaultHeader(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProfileServiceException.class)
    public ResponseEntity<JsonExceptionResponse> handleProfileServiceException(ProfileServiceException e) {
        return new ResponseEntity<>(
                JsonExceptionResponse.builder().code(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build(),
                getDefaultHeader(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonExceptionResponse> handleAllException(Exception e) {
        return new ResponseEntity<>(
                JsonExceptionResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(e.getMessage()).build(),
                getDefaultHeader(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //TODO: Can be moved into a util
    private HttpHeaders getDefaultHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }
}
