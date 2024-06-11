package com.project1.Books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookAlreadyPresentException extends RuntimeException {
    public BookAlreadyPresentException(String message){
        super(message);
    }

    public BookAlreadyPresentException(String message, Throwable cause){
        super(message, cause);
    }
}
