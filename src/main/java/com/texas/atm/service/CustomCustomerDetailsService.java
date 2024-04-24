package com.texas.atm.service;

import com.texas.atm.model.Customer;
import com.texas.atm.repo.CustomerRepo;
import com.texas.atm.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomCustomerDetailsService implements UserDetailsService {
    private final CustomerRepo customerRepo;

    public CustomCustomerDetailsService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + name));

        return new org.springframework.security.core.userdetails.User(
                customer.getName(),
                customer.getPassword(),
                getAuthorities(customer.getRole())
        );
    }

    public Set<SimpleGrantedAuthority> getAuthorities(Role role){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return authorities;
    }
}