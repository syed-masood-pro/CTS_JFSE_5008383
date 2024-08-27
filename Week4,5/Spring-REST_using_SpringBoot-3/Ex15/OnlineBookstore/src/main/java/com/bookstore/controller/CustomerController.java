package com.example.bookstore.controller;

import com.example.bookstore.model.Customer;
import com.example.bookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Get all customers", description = "Retrieve a list of all customers.")
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @Operation(summary = "Get a customer by ID", description = "Retrieve a customer by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer found"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(
        @Parameter(description = "ID of the customer to be retrieved") @PathVariable Long id) {
        return customerService.getCustomerById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new customer", description = "Add a new customer to the system.")
    @PostMapping
    public ResponseEntity<Customer> createCustomer(
        @Parameter(description = "Customer to be created") @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.saveCustomer(customer));
    }

    @Operation(summary = "Delete a customer", description = "Remove a customer by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Customer deleted"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
        @Parameter(description = "ID of the customer to be deleted") @PathVariable Long id) {
        return customerService.deleteCustomer(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
