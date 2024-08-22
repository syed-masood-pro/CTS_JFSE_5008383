package com.bookstore.controller;

import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> books = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) String author) {
        List<Book> filteredBooks;
        if (title != null && author != null) {
            filteredBooks = books.stream()
                    .filter(book -> book.getTitle().equalsIgnoreCase(title)
                            && book.getAuthor().equalsIgnoreCase(author))
                    .collect(Collectors.toList());
        } else if (title != null) {
            filteredBooks = books.stream()
                    .filter(book -> book.getTitle().equalsIgnoreCase(title))
                    .collect(Collectors.toList());
        } else if (author != null) {
            filteredBooks = books.stream()
                    .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                    .collect(Collectors.toList());
        } else {
            filteredBooks = books;
        }
        return ResponseEntity.ok(filteredBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        book.setId((long) (books.size() + 1));
        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setPrice(updatedBook.getPrice());
                    book.setIsbn(updatedBook.getIsbn());
                    return ResponseEntity.ok(book);
                })
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
    }
}
