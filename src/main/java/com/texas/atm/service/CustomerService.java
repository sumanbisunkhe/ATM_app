package com.texas.atm.service;

import com.texas.atm.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    void create(CustomerDto customerDto);
    CustomerDto getById(Integer id);
    List<CustomerDto> getAll();
    void deleteById(Integer id);
    CustomerDto updateById(Integer id, CustomerDto customerDto);
    String getCustomerNameById(Integer customerId);
    String findByName(String name);

}
