package com.bookstore.model;

import lombok.Data;

@Data
public class Book {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private String isbn;
}
