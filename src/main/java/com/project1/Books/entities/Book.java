package com.project1.Books.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Author author;

//    public Book(UUID id, String name, Author author) {
//        this.id = id;
//        this.name = name;
//        this.author=author;
//    }

}
