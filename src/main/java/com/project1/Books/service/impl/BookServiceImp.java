package com.project1.Books.service.impl;

import com.project1.Books.entities.Book;
import com.project1.Books.exception.BookAlreadyPresentException;
import com.project1.Books.exception.BookNotFoundException;
import com.project1.Books.repository.BookRepository;
import com.project1.Books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookServiceImp implements BookService {

    @Autowired
    private BookRepository bookRepository;
    public Optional<Book> getBookById(UUID id){
        Optional<Book> bookPresent= bookRepository.findById(id);
        if (bookPresent.isPresent()){
            return Optional.of(bookPresent.get());
        }
        else throw new BookNotFoundException("No such record found");
    }

    public Book createBook(Book book){
        Book existingBook
                = bookRepository.findByName(book.getName());

        if (existingBook==null){
            return bookRepository.save(book);

        }
        else throw new BookAlreadyPresentException("The Book is already present");
    }

    public List<Book> getAllBooks(){
        List<Book> book= bookRepository.findAll();
        if (book.size()!=0){
            return bookRepository.findAll();
        }
        else throw new BookNotFoundException("No books found");
    }

}
