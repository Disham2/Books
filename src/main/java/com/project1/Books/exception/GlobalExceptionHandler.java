package com.project1.Books.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<BaseErrorMsg> handleBadRequestException(BadRequestException ex, WebRequest webRequest){
        log.error("Something is not eight");
        var rMsg= new BaseErrorMsg(
                ""+HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rMsg);
    }

    @ExceptionHandler({BookAlreadyPresentException.class})
    public ResponseEntity<BaseErrorMsg> handleBookAlreadyPresentException(BookAlreadyPresentException ex,
                                                                   WebRequest webRequest){
        log.error("Book already exists");
        var rMsg= new BaseErrorMsg(
                ""+HttpStatus.CONFLICT,
                ex.getMessage(),
                "Book already exists in db"
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(rMsg);
    }

    @ExceptionHandler({BookNotFoundException.class})
    public ResponseEntity<BaseErrorMsg> handleBookNotFoundException(BookNotFoundException ex,
                                                                          WebRequest webRequest){
        log.error("Book not found");
        var rMsg= new BaseErrorMsg(
                ""+HttpStatus.NOT_FOUND,
                ex.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rMsg);
    }

    @ExceptionHandler({UnprocessableEntityException.class})
    public ResponseEntity<BaseErrorMsg> handleUnprocessableEntityException(UnprocessableEntityException ex,
                                                                    WebRequest webRequest){
        log.error("Unprocessable Exception encountered");
        var rMsg= new BaseErrorMsg(
                ""+HttpStatus.UNPROCESSABLE_ENTITY,
                ex.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(rMsg);
    }
}
