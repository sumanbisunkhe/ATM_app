package com.texas.atm.controller;

import com.texas.atm.dto.CustomerDto;
import com.texas.atm.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> saveCustomer( @RequestBody CustomerDto customerDto) {
        customerService.create(customerDto);
        return ResponseEntity.ok(
                Map.of("message", "Customer created Successfully..")
        );
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Map<String, Object>> fetchUserById(@PathVariable("id") Integer id) {
        CustomerDto data = customerService.getById(id);
        return ResponseEntity.ok(
                Map.of("message", "Customer Data Fetched Successfully..", "data", data)
        );
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> fetchAllUsers() {
        List<CustomerDto> data = customerService.getAll();
        return ResponseEntity.ok(
                Map.of("message", "Customer List Fetched Successfully..", "data", data)
        );
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Map<String, String>> deleteUserById(@PathVariable("id") Integer id) {
        customerService.deleteById(id);
        return ResponseEntity.ok(
                Map.of("message", "Customer Deleted Successfully.")
        );
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<Map<String, Object>> updateCustomerById(@PathVariable("id") Integer id, @RequestBody CustomerDto updatedCustomerDto) {
        CustomerDto updatedCustomer = customerService.updateById(id, updatedCustomerDto);
        return ResponseEntity.ok(
                Map.of("message", "Customer Updated Successfully..", "data", updatedCustomer)
        );
    }
}
