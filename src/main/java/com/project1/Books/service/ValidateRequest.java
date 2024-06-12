package com.project1.Books.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.Books.dto.PatchDto;
import com.project1.Books.entities.Book;
import com.project1.Books.exception.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ValidateRequest {

    public void verifyReq(Book book){
        var objectMapper= new ObjectMapper();
        List<String> madparams= List.of("name","author");
        Map<String, Object> service = objectMapper.convertValue(book, Map.class);

        if (!service.keySet().containsAll(madparams)) {

            throw new UnprocessableEntityException(String.format("missing mandatory params : %s", madparams));
        }
        if (null==book.getName() || book.getName().trim().isEmpty())
            throw new UnprocessableEntityException("Book name cannot be null or empty");
        if (book.getAuthor()==null)
            throw new UnprocessableEntityException("Author details cannot be null");
        if (book.getAuthor().getName()==null || book.getAuthor().getName().trim().isEmpty())
            throw new UnprocessableEntityException("Author name cannot be null or empty");
        if (book.getAuthor().getCountry()==null || book.getAuthor().getCountry().trim().isEmpty())
            throw new UnprocessableEntityException("Author country cannot be null or empty");
        if(book.getPrice()==null || book.getPrice().describeConstable().isEmpty())
            throw new UnprocessableEntityException("Price cannot be null or empty");
    }

    public void verifyPatch(PatchDto patchDto){
        if (null==patchDto.getOp() || patchDto.getOp().trim().isEmpty())
            throw  new UnprocessableEntityException("Operation cannot be null or empty");
        if (null==patchDto.getPath() || patchDto.getPath().trim().isEmpty())
            throw  new UnprocessableEntityException("Path cannot be null or empty");
        if (null==patchDto.getValue() || patchDto.getValue().describeConstable().isEmpty())
            throw new UnprocessableEntityException("Value cannot be empty or null");

    }
}
