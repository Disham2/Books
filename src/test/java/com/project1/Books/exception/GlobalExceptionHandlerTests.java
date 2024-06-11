package com.project1.Books.exception;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GlobalExceptionHandlerTests {
    @Mock
    private WebRequest webRequest;

    @InjectMocks

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void handleBadRequestTest(){
        BadRequestException ex= new BadRequestException("Bad Request");

        ResponseEntity<BaseErrorMsg> responseEntity= globalExceptionHandler.handleBadRequestException(ex, webRequest);

        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(responseEntity.getStatusCode());
        assertThat(HttpStatus.BAD_REQUEST.toString()).isEqualTo(responseEntity.getBody().getCode());
        assertThat("Bad Request").isEqualTo(responseEntity.getBody().getMessage());
        assertThat(HttpStatus.BAD_REQUEST.getReasonPhrase()).isEqualTo(responseEntity.getBody().getMessage());
    }

    @Test
    void handleBookAlreadyPresentTest(){
        BookAlreadyPresentException ex= new BookAlreadyPresentException("Conflict");

        ResponseEntity<BaseErrorMsg> responseEntity= globalExceptionHandler.handleBookAlreadyPresentException(ex, webRequest);

        assertThat(HttpStatus.CONFLICT).isEqualTo(responseEntity.getStatusCode());
        assertThat(HttpStatus.CONFLICT.toString()).isEqualTo(responseEntity.getBody().getCode());
        assertThat("Conflict").isEqualTo(responseEntity.getBody().getMessage());
        assertThat(HttpStatus.CONFLICT.getReasonPhrase()).isEqualTo(responseEntity.getBody().getMessage());
    }

    @Test
    void handleBookNotFoundTest(){
        BookNotFoundException ex= new BookNotFoundException("Not Found");

        ResponseEntity<BaseErrorMsg> responseEntity= globalExceptionHandler.handleBookNotFoundException(ex, webRequest);

        assertThat(HttpStatus.NOT_FOUND).isEqualTo(responseEntity.getStatusCode());
        assertThat(HttpStatus.NOT_FOUND.toString()).isEqualTo(responseEntity.getBody().getCode());
        assertThat("Not Found").isEqualTo(responseEntity.getBody().getMessage());
        assertThat(HttpStatus.NOT_FOUND.getReasonPhrase()).isEqualTo(responseEntity.getBody().getMessage());
    }

    @Test
    void handleUnproccessableEntityTest(){
        UnprocessableEntityException ex= new UnprocessableEntityException("Unprocessable Entity");

        ResponseEntity<BaseErrorMsg> responseEntity= globalExceptionHandler.handleUnprocessableEntityException(ex, webRequest);

        assertThat(HttpStatus.UNPROCESSABLE_ENTITY).isEqualTo(responseEntity.getStatusCode());
        assertThat(HttpStatus.UNPROCESSABLE_ENTITY.toString()).isEqualTo(responseEntity.getBody().getCode());
        assertThat("Unprocessable Entity").isEqualTo(responseEntity.getBody().getMessage());
        assertThat(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()).isEqualTo(responseEntity.getBody().getMessage());
    }


}
