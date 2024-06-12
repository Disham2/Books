package com.project1.Books.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.project1.Books.entities.Author;
import com.project1.Books.entities.Book;
import com.project1.Books.exception.UnprocessableEntityException;
import com.project1.Books.service.ValidateRequest;
import com.project1.Books.service.impl.BookServiceImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImp bookServiceImp;

    @MockBean
    private ValidateRequest validateRequest;

    @InjectMocks
    private BookController bookController;

    ObjectMapper mapper;

    Author author=new Author(UUID.randomUUID(),"Tayne", "France");
    UUID bookId=UUID.randomUUID();

    Author author2=new Author(UUID.randomUUID(),"Jim", "Kenya");
    UUID bookId2=UUID.randomUUID();
    Book book1;
    Book book2;
    List<Book> bookList= new ArrayList<>();

    @BeforeEach
    void setUp() {
        book1= new Book(bookId, "Pega", 200,  author);
        book2= new Book(bookId2, "rust", 541,  author2);

        bookList.add(book1);
        bookList.add(book2);

        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testGetOneBook_Success() throws Exception {
        when(bookServiceImp.getBookById(bookId)).thenReturn(Optional.of(book1));
        this.mockMvc.perform(get("/books/{id}", bookId)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(book1.getId().toString()))
                .andExpect(jsonPath("$.name").value(book1.getName()));
    }

    @Test
    void testGetOneBook_Failure() throws Exception {
        when(bookServiceImp.getBookById(bookId)).thenReturn(Optional.ofNullable(book1));
        this.mockMvc.perform(get("/books/{id}", UUID.randomUUID())).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void testGetBooks_Success() throws Exception {
        when(bookServiceImp.getAllBooks()).thenReturn(bookList);
        this.mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(book1.getId().toString()))
                .andExpect(jsonPath("$[0].name").value(book1.getName()))
                .andExpect(jsonPath("$[0].author.country").value(book1.getAuthor().getCountry()));
    }

    @Test
    void testGetBooks_Failure() throws Exception {
        List<Book> emptyList= new ArrayList<>();
//        bookList.remove(book1); bookList.remove(book2);
        when(bookServiceImp.getAllBooks()).thenReturn(emptyList);
        this.mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void testAddBook() throws Exception {
        when(bookServiceImp.createBook(any(Book.class))).thenReturn(book1);


        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(book1);

        this.mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.author.country").value(book1.getAuthor().getCountry()));
    }

    @Test
    void testAddBook_Failure() throws Exception {
        doThrow(new UnprocessableEntityException("Validation failed")).when(validateRequest).verifyReq(any(Book.class));

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(book1);

        this.mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isUnprocessableEntity());
    }
    
}
