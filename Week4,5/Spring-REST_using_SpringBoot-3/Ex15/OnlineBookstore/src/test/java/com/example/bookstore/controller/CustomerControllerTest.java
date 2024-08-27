package com.example.bookstore.controller;

import com.example.bookstore.model.Customer;
import com.example.bookstore.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testGetCustomerById() throws Exception {
        Customer customer = new Customer(1L, "Customer Name", "customer@example.com");
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Customer Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("customer@example.com"));
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer(1L, "Customer Name", "customer@example.com");
        when(customerService.saveCustomer(customer)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType("application/json")
                .content("{\"name\":\"Customer Name\",\"email\":\"customer@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Customer Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("customer@example.com"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
