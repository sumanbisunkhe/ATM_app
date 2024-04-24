package com.texas.atm.service.impl;

import com.texas.atm.dto.CustomerDto;
import com.texas.atm.model.Customer;
import com.texas.atm.repo.CustomerRepo;
import com.texas.atm.service.CustomerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepo customerRepo, PasswordEncoder passwordEncoder) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void create(CustomerDto customerDto) {
        // Check if a customer with the same name already exists
        Optional<Customer> existingCustomer = customerRepo.findByName(customerDto.getName());
        if (existingCustomer.isPresent()) {
            throw new RuntimeException("Customer already exists with name: " + customerDto.getName());
        }

        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setPin(customerDto.getPin());
        customer.setContact(customerDto.getContact());
        customer.setAddress(customerDto.getAddress());
        customer.setGender(customerDto.getGender());

        String plainPassword = customerDto.getPassword();
        String encodedPassword = passwordEncoder.encode(plainPassword);
        customer.setPassword(encodedPassword);

        customer.setRole(customerDto.getRole());
        customerRepo.save(customer);
    }


    @Override
    public CustomerDto getById(Integer id) {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            return new CustomerDto(
                    customer.getId(), customer.getName(), customer.getPin(),
                    customer.getContact(), customer.getAddress(),
                    customer.getGender(),customer.getPassword(), customer.getRole()
            );
        } else {
            throw new RuntimeException("Customer with ID " + id + " not found");
        }
    }

    @Override
    public List<CustomerDto> getAll() {
        List<Customer> customerList = customerRepo.findAll();
        return customerList.stream()
                .map(customer -> new CustomerDto(
                        customer.getId(), customer.getName(), customer.getPin(),
                        customer.getContact(), customer.getAddress(),
                        customer.getGender(),customer.getPassword(), customer.getRole()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        if (customerRepo.existsById(id)) {
            customerRepo.deleteById(id);
        } else {
            throw new RuntimeException("Customer with ID " + id + " not found");
        }
    }

    @Override
    public CustomerDto updateById(Integer id, CustomerDto updatedCustomerDto) {
        Optional<Customer> optionalCustomer = customerRepo.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            existingCustomer.setPin(updatedCustomerDto.getPin());
            existingCustomer.setContact(updatedCustomerDto.getContact());
            existingCustomer.setAddress(updatedCustomerDto.getAddress());
            existingCustomer.setPassword(updatedCustomerDto.getPassword());
            Customer updatedCustomer = customerRepo.save(existingCustomer);
            return new CustomerDto(
                    existingCustomer.getId(), existingCustomer.getName(), updatedCustomer.getPin(),
                    updatedCustomer.getContact(), updatedCustomer.getAddress(),
                    existingCustomer.getGender(),updatedCustomer.getPassword(),existingCustomer.getRole()
            );
        } else {
            throw new RuntimeException("Customer with ID " + id + " not found");
        }
    }

    @Override

    public String getCustomerNameById(Integer customerId) {
        // Assuming CustomerRepo has a method to find CustomerDto by Id
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return customer.getName();
    }

    @Override
    public String findByName(String name) {
        Optional<Customer> customerOptional = customerRepo.findByName(name);
        return customerOptional.map(Customer::getName).orElse(null);
    }


}