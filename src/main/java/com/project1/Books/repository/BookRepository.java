package com.project1.Books.repository;

import com.project1.Books.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findById(UUID id);
    Book findByName(String name);

    List<Book> findAll();
}
