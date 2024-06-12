package com.project1.Books.controllers;

import com.project1.Books.entities.Book;
import com.project1.Books.exception.BookNotFoundException;
import com.project1.Books.service.impl.AuthorServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AuthorController {
    @Autowired
    private AuthorServiceImp authorServiceImp;

    @GetMapping("/author/{name}")
    public ResponseEntity<List<Object[]>> getBookByAuthor(@PathVariable ("name") String authorName){
        log.info("Patch request recieved");
        List<Object[]> authorAndBook= authorServiceImp.findBooksByAuthor(authorName);
        if (authorAndBook.isEmpty())
            throw new BookNotFoundException("No book by this author");
        return ResponseEntity.status(HttpStatus.OK).body(authorAndBook);

    }



}
