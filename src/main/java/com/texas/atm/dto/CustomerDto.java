package com.texas.atm.dto;

import com.texas.atm.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CustomerDto {
    private Integer id;

    @NotNull(message = "Name Cannot Be Null.")
    private String name;
    @NotNull(message = "Pin Cannot Be Null.")
    private Integer pin;
    @NotNull(message = "Contact Cannot Be Null.")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String contact;
    @NotNull(message = "Address Cannot Be Null.")
    private String address;
    @NotNull(message = "Gender Cannot Be Null.")
    private String gender;

    private String password;

    private Role role;

    // Constructors, getters, and setters
    public CustomerDto() {
    }

    public CustomerDto(Integer id, String name, Integer pin, String contact, String address, String gender, String password, Role role) {
        this.id = id;
        this.name = name;
        this.pin = pin;
        this.contact = contact;
        this.address = address;
        this.gender = gender;
        this.password = password;
        this.role = role;
    }

    public CustomerDto(Integer id, String name, Integer pin, String contact, String address, String gender, Role role) {
        this.id = id;
        this.name = name;
        this.pin = pin;
        this.contact = contact;
        this.address = address;
        this.gender = gender;
        this.role = role;
    }


    public CustomerDto(String name, Integer pin, String contact, String address, String password) {
        this.name = name;
        this.pin = pin;
        this.contact = contact;
        this.address = address;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}