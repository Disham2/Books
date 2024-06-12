package com.project1.Books.service.Impl;


import com.project1.Books.entities.Author;
import com.project1.Books.entities.Book;
import com.project1.Books.exception.BookAlreadyPresentException;
import com.project1.Books.exception.BookNotFoundException;
import com.project1.Books.repository.BookRepository;
import com.project1.Books.service.impl.BookServiceImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class BookServiceImpTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImp bookServiceImp;

    Book book;
    Book book2;
    Author author=new Author(UUID.randomUUID(),"Tayne", "France");
    UUID bookId=UUID.randomUUID();

    Author author2=new Author(UUID.randomUUID(),"Jim", "Kenya");
    UUID bookId2=UUID.randomUUID();
    UUID bid=UUID.randomUUID();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        book = new Book(bookId, "ML", 145, author);

        book2 = new Book(bookId2, "IOT", 199, author2);

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    public void testGetBookById_Success(){


        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));

        Optional<Book> res= bookServiceImp.getBookById(bookId);
        assertThat(res.isPresent()).isTrue();
        assertThat(res.get().getName()).isEqualTo("ML");
        assertThat(res.get().getId()).isEqualTo(bookId);
    }

    @Test
    public void testGetBookById_Failure(){

//        when(bookRepository.findById(bid)).thenReturn(Optional.empty());
        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
         Exception exception = assertThrows(BookNotFoundException.class, () -> {
             bookServiceImp.getBookById(bid);
         });

         assertThat("No such record found").isEqualTo(exception.getMessage());
    }

    @Test
    public void testCreateBook_Success(){
        when(bookRepository.findByName(book.getName())).thenReturn(null);
        when(bookRepository.save(book)).thenReturn(book);

        Book res= bookServiceImp.createBook(book);
        assertThat(res.getName()).isEqualTo("ML");
    }

    @Test
    public void testCreateBook_Failure(){

        when(bookRepository.findByName(book.getName())).thenReturn(book);

        Exception exception = assertThrows(BookAlreadyPresentException.class, () -> {
            bookServiceImp.createBook(book);
        });


        assertThat("The Book is already present").isEqualTo(exception.getMessage());
    }

    @Test
    public void testGetAllBooks_Success(){
        List<Book> resBook= new ArrayList<>();
        resBook.add(book);
        resBook.add(book2);

        when(bookRepository.findAll()).thenReturn(resBook);

//        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> res= bookServiceImp.getAllBooks();
        assertThat(res.size()).isNotZero();
    }

    @Test
    public void testGetAllBooks_Failure(){
        List<Book> books= new ArrayList<>();


        when(bookRepository.findAll()).thenReturn(books);

        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookServiceImp.getAllBooks();
        });

        assertThat("No books found").isEqualTo(exception.getMessage());
    }
}
