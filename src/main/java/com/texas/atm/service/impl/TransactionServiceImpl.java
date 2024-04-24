package com.texas.atm.service.impl;

import com.texas.atm.dto.CustomerDto;
import com.texas.atm.dto.TransactionDto;
import com.texas.atm.enums.TransactionType;
import com.texas.atm.model.Account;
import com.texas.atm.model.Customer;
import com.texas.atm.model.Transaction;
import com.texas.atm.repo.AccountRepo;
import com.texas.atm.repo.CustomerRepo;
import com.texas.atm.repo.TransactionRepo;
import com.texas.atm.service.TransactionService;
import com.texas.atm.utils.Utility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final CustomerRepo customerRepo;
    private final TransactionRepo transactionRepo;
    private final AccountRepo accountRepo;

    public TransactionServiceImpl(AccountRepo accountRepo, CustomerRepo customerRepo, TransactionRepo transactionRepo) {
        this.customerRepo = customerRepo;
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal checkBalance(Integer customerId, Integer pin) {
        // Retrieve the customer by ID
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        // Check if the PIN provided matches the customer's PIN
        if (!customer.getPin().equals(pin)) {
            throw new RuntimeException("Invalid PIN");
        }

        // Retrieve the customer's account
        List<Account> accounts = customer.getAccounts();
        if (accounts == null || accounts.isEmpty()) {
            throw new NoSuchElementException("Customer does not have an account");
        }

        // Assuming the customer has only one account, get the first account
        Account account = accounts.get(0);

        // Return the account balance
        return account.getBalance();
    }




    @Override
    @Transactional
    public Map<String, BigDecimal> deposit(TransactionDto transactionDto, Integer customerId, Integer pin) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!customer.getPin().equals(pin)) {
            throw new RuntimeException("Invalid PIN");
        }

        List<Account> accounts = customer.getAccounts();
        if (accounts == null || accounts.isEmpty()) {
            throw new NoSuchElementException("Customer does not have any accounts");
        }
        Account account = accounts.get(0);

        BigDecimal depositedAmount = transactionDto.getAmount();
        BigDecimal newBalance = account.getBalance().add(depositedAmount);
        account.setBalance(newBalance);

        // Use LocalDateTime.now() to get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        Transaction transaction = new Transaction(TransactionType.DEPOSIT,
                depositedAmount, currentDateTime, customer); // Use LocalDateTime.now() directly
        transactionRepo.save(transaction);

        Map<String, BigDecimal> result = new HashMap<>();
        result.put("New Balance", newBalance);
        result.put("Deposited Amount", depositedAmount);
        return result;
    }



    @Override
    @Transactional
    public Map<String, BigDecimal> withdraw(TransactionDto transactionDto, Integer customerId, Integer pin) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!customer.getPin().equals(pin)) {
            throw new RuntimeException("Invalid PIN");
        }

        List<Account> accounts = customer.getAccounts();
        if (accounts == null || accounts.isEmpty()) {
            throw new NoSuchElementException("Customer does not have any accounts");
        }
        Account account = accounts.get(0);

        BigDecimal withdrawalAmount = transactionDto.getAmount();
        BigDecimal newBalance = account.getBalance().subtract(withdrawalAmount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(newBalance);

        Transaction transaction = new Transaction(TransactionType.WITHDRAW,
                withdrawalAmount,  LocalDateTime.now(), customer); // Use LocalDate.now() instead of new Date()
        transactionRepo.save(transaction);

        Map<String, BigDecimal> result = new HashMap<>();
        result.put("New Balance", newBalance);
        result.put("Withdrawn Amount", withdrawalAmount);
        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> getTransactionHistory() {
        List<Transaction> transactions = transactionRepo.findAll();
        List<TransactionDto> transactionDtoList = new LinkedList<>();
        for (Transaction transaction : transactions) {
            TransactionDto transactionDto = convertToDto(transaction);
            // Set the customer to null to exclude it from the DTO
            transactionDto.setCustomer(null);
            transactionDtoList.add(transactionDto);
        }
        return transactionDtoList;
    }


    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> getTransactionHistoryByCustomerId(Integer customerId) {
        List<Transaction> transactions = transactionRepo.findByCustomerId(customerId);
        List<TransactionDto> transactionDtoList = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionDto transactionDto = convertToDto(transaction);
            // Set the customer to null to exclude it from the DTO
            transactionDto.setCustomer(null);
            transactionDtoList.add(transactionDto);
        }

        return transactionDtoList;
    }




    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionId(transaction.getTransactionId());
        transactionDto.setTransactionType(transaction.getTransactionType());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setDate(transaction.getDate());
        // Create a CustomerDto object and set it
        CustomerDto customerDto = new CustomerDto(/* Populate with appropriate fields */);
        transactionDto.setCustomer(customerDto);
        return transactionDto;
    }

}