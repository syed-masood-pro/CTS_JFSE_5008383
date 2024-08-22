package com.bookstore.controller;

import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final List<Customer> customers = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customer.setId((long) (customers.size() + 1));
        customers.add(customer);
        return ResponseEntity.status(201).body(customer);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("profilePicture") MultipartFile profilePicture) {

        Customer customer = new Customer();
        customer.setId((long) (customers.size() + 1));
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customers.add(customer);

        return ResponseEntity.status(201).body("Customer registered successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
    }
}
