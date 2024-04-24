package com.texas.atm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.texas.atm.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)

public class TransactionDto {
    private Integer transactionId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime date;
    private CustomerDto customer; // Change this to CustomerDto

    // Constructors
    public TransactionDto() {
    }

    // Constructor without customer
    public TransactionDto(Integer transactionId, TransactionType transactionType, BigDecimal amount, LocalDateTime date) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
    }

    // Constructor with customer
    public TransactionDto(Integer transactionId, TransactionType transactionType, BigDecimal amount, LocalDateTime date, CustomerDto customer) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
        this.customer = customer;
    }

    // Getters and setters
    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }
}
