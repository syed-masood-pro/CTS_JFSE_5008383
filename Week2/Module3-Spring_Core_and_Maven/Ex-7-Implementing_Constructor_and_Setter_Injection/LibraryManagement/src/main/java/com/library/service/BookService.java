package com.library.service;

import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private BookRepository bookRepository;

    // Default constructor 
    public BookService() {
        System.out.println("Default constructor called");
    }

    // Constructor for dependency injection
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("Constructor injection: " + bookRepository);
    }

    // Setter for dependency injection
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("Setter injection: " + bookRepository);
    }

    public void performService() {
        System.out.println("Performing service with book repository: " + bookRepository);
        bookRepository.performRepositoryAction();
    }
}
