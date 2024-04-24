package com.texas.atm.service;

import com.texas.atm.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TransactionService {
    BigDecimal checkBalance(Integer customerId, Integer pin);
    Map<String, BigDecimal> deposit(TransactionDto transactionDto, Integer customerId, Integer pin);
    Map<String, BigDecimal> withdraw(TransactionDto transactionDto, Integer customerId, Integer pin);
    List<TransactionDto> getTransactionHistory();
    List<TransactionDto> getTransactionHistoryByCustomerId(Integer customerId);
}