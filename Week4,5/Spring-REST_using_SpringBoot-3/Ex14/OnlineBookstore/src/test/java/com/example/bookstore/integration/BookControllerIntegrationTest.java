package com.example.bookstore.integration;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    public void testCreateBook() throws Exception {
        String bookJson = "{\"title\":\"The Great Book\",\"author\":\"John Doe\"}";

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The Great Book"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("John Doe"));
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = bookRepository.save(new Book(null, "The Great Book", "John Doe"));

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The Great Book"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("John Doe"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Book book = bookRepository.save(new Book(null, "The Great Book", "John Doe"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/" + book.getId()))
                .andExpect(status().isNoContent());
    }
}
