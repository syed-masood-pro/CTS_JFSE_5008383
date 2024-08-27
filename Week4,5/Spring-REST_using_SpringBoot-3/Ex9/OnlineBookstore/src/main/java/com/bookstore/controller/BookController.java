package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BookResource> getBook(@PathVariable Long id) {
        Book book = bookService.findById(id);
        BookResource resource = new BookResource(book);
        resource.add(linkTo(methodOn(BookController.class).getBook(id)).withSelfRel());
        resource.add(linkTo(methodOn(BookController.class).getAllBooks()).withRel("all-books"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<List<BookResource>> getAllBooks() {
        List<Book> books = bookService.findAll();
        List<BookResource> resources = books.stream()
                .map(BookResource::new)
                .collect(Collectors.toList());
        resources.forEach(resource -> resource.add(linkTo(methodOn(BookController.class).getBook(resource.getId())).withSelfRel()));
        return ResponseEntity.ok(resources);
    }
}
