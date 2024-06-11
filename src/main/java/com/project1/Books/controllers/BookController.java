package com.project1.Books.controllers;

import com.project1.Books.entities.Book;
import com.project1.Books.exception.BookAlreadyPresentException;
import com.project1.Books.exception.BookNotFoundException;
import com.project1.Books.service.BookService;
import com.project1.Books.service.ValidateRequest;
import com.project1.Books.service.impl.BookServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class BookController extends BadRequestException {

    @Autowired
    private BookServiceImp bookService;

    @Autowired
    private ValidateRequest validateReq;

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book){

            log.info("post request recieved");
            validateReq.verifyReq(book);
            Book createdBook = bookService.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);

    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getOneBook(@PathVariable UUID id) {
            Optional<Book> bookOptional = bookService.getBookById(id);
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                return ResponseEntity.status(HttpStatus.OK).body(book);
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks(){
        List<Book> book=bookService.getAllBooks();
        if (book.isEmpty())
            throw new BookNotFoundException("No books found");
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }
    }


