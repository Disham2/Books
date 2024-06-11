package com.project1.Books.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID aid;

    @NonNull
    private String name;

    @NonNull
    private String country;

    @OneToOne(mappedBy = "author")
    @JsonBackReference
    private Book book;

    public Author(UUID aid, String name, String country){
        this.aid=aid;
        this.name=name;
        this.country=country;
    }
}
