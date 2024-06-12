package com.project1.Books.repository;

import com.project1.Books.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    @Query(value="SELECT * FROM author a INNER JOIN book b ON b.author_aid=a.aid WHERE a.name= :authorName", nativeQuery = true)
    List<Object[]> findAuthorByName(@Param("authorName") String authorName);
}
