package com.bookstore.dto;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class BookDTO {
    private Long id;
    private String title;

    @JsonProperty("book_author")
    private String author;
    
    private Double price;
    private String isbn;
}
