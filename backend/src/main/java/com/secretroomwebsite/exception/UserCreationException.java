package com.secretroomwebsite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class UserCreationException extends RuntimeException {
    public UserCreationException(String message) {
        super(message);
    }
}
