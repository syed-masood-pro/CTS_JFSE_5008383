package com.example.bookstore.integration;

import com.example.bookstore.model.Customer;
import com.example.bookstore.repository.CustomerRepository;
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
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateCustomer() throws Exception {
        String customerJson = "{\"name\":\"Jane Doe\",\"email\":\"jane.doe@example.com\"}";

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jane Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("jane.doe@example.com"));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customer customer = customerRepository.save(new Customer(null, "Jane Doe", "jane.doe@example.com"));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + customer.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jane Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("jane.doe@example.com"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Customer customer = customerRepository.save(new Customer(null, "Jane Doe", "jane.doe@example.com"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/" + customer.getId()))
                .andExpect(status().isNoContent());
    }
}
