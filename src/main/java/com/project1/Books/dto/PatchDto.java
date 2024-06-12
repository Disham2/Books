package com.project1.Books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchDto {

    private String op;
    private String path;
    private Integer value;
}
