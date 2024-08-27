package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get all books", description = "Retrieve a list of all books.")
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @Operation(summary = "Get a book by ID", description = "Retrieve a book by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book found"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
        @Parameter(description = "ID of the book to be retrieved") @PathVariable Long id) {
        return bookService.getBookById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new book", description = "Add a new book to the library.")
    @PostMapping
    public ResponseEntity<Book> createBook(
        @Parameter(description = "Book to be created") @RequestBody Book book) {
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @Operation(summary = "Delete a book", description = "Remove a book by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Book deleted"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
        @Parameter(description = "ID of the book to be deleted") @PathVariable Long id) {
        return bookService.deleteBook(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
