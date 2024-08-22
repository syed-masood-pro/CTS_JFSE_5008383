package com.bookstore.controller;

import com.bookstore.dto.CustomerDTO;
import com.bookstore.mapper.CustomerMapper;
import com.bookstore.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final List<Customer> customers = new ArrayList<>();
    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId((long) (customers.size() + 1));
        customers.add(customer);
        return ResponseEntity.status(201).body(customerMapper.customerToCustomerDTO(customer));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerDTOs);
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
}
