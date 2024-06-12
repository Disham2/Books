package com.project1.Books.service.impl;

import com.project1.Books.entities.Author;
import com.project1.Books.entities.Book;
import com.project1.Books.repository.AuthorRepository;
import com.project1.Books.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AuthorServiceImp implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Object[]> findBooksByAuthor(String name){
        List<Object[]> authors=authorRepository.findAuthorByName(name);
        return authors;
    }
}
