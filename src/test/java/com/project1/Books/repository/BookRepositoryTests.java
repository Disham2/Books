package com.project1.Books.repository;

import com.project1.Books.entities.Author;
import com.project1.Books.entities.Book;
import com.project1.Books.repository.BookRepository;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    Book book;

    Author author;
    UUID uid;

    @BeforeEach
    public void setUp() throws Exception {
        author = new Author(UUID.randomUUID(), "George", "India");
        uid=UUID.randomUUID();
        book=new Book(uid, "Java", 250, author);
        bookRepository.save(book);
        System.out.println(book.toString());
    }

    @AfterEach
    public void tearDown() throws Exception {
        book=null;
        bookRepository.deleteAll();

    }

    @Test
    public void testFindByName_Success(){

        System.out.println(uid);
        System.out.println(uid.getClass().getName());
        Optional<Book> bk= Optional.ofNullable(bookRepository.findByName("Java"));
        assertThat(bk.isPresent()).isTrue();
//        assertThat(bk.get().getId()).isEqualTo(uid);
        assertThat(bk.get().getName()).isEqualTo("Java");

    }

    @Test
    public void testFindByName_Failure(){

        System.out.println(uid);
        System.out.println(uid.getClass().getName());
        Optional<Book> bk= Optional.ofNullable(bookRepository.findByName("Mern"));
        assertThat(bk.isPresent()).isFalse();
//        assertThat(bk.get().getId()).isEqualTo(uid);
//        assertThat(bk.get().getName()).isEqualTo("Java");

    }
}
