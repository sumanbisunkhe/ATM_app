package com.texas.atm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class AccountDto {
    private Integer id;
    private String accountNumber;
    @NotNull(message = "Account Type Cannot Be Null.")
    private String accountType;
    @NotNull(message = "Balance Cannot Be Null.")
    @Positive(message = "Balance Cannot Be Negative.")
    private BigDecimal balance;


    // Constructors, getters, and setters
    public AccountDto() {
    }

    public AccountDto(String accountNumber, String accountType, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}