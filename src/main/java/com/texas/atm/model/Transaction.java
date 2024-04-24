package com.texas.atm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.texas.atm.enums.TransactionType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated ID
    private Integer transactionId;

    @Enumerated(EnumType.STRING) // Persist enum as a string
    @Column(length = 20, nullable = false) // Example length and nullable constraints
    private TransactionType transactionType;

    private BigDecimal amount;
    private LocalDateTime date;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public Transaction(Integer transactionId, TransactionType transactionType, BigDecimal amount, LocalDateTime date, Customer customer) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
        this.customer = customer;
    }

    public Transaction(TransactionType transactionType, BigDecimal amount, LocalDateTime date, Customer customer) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
        this.customer = customer;
    }



    public Transaction(TransactionType deposit, BigDecimal depositedAmount, Date date, Customer customer) {

    }

    public Transaction() {

    }

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}