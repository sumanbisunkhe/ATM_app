package com.texas.atm.controller;

import com.texas.atm.dto.TransactionDto;
import com.texas.atm.repo.AccountRepo;
import com.texas.atm.repo.CustomerRepo;
import com.texas.atm.service.AccountService;
import com.texas.atm.service.CustomerService;
import com.texas.atm.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final CustomerRepo customerRepo;
    private final AccountService accountService;

    public TransactionController(TransactionService transactionService, CustomerRepo customerRepo, CustomerService customerService, AccountService accountService, AccountRepo accountRepo, AccountService accountService1) {
        this.transactionService = transactionService;
        this.customerRepo = customerRepo;
        this.accountService = accountService1;
    }

    @GetMapping("/check-balance/customerId/{customerId}/{pin}")
    public ResponseEntity<Map<String, Object>> checkBalance(@PathVariable Integer customerId, @PathVariable Integer pin) {
        BigDecimal balance = transactionService.checkBalance(customerId, pin);
        String name = customerRepo.findById(customerId).get().getName();
        String accountNumber = accountService.getAccountNumberByCustomerId(customerId);
        // Construct the response body
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("Account Number", accountNumber);
        responseBody.put("Account Name", name);
        responseBody.put("balance", balance);
        // Return the response with a 200 OK status
        return ResponseEntity.ok(responseBody);
    }







    @PostMapping("/deposit/customerId/{customerId}/{pin}")
    public ResponseEntity<Map<String, BigDecimal>> deposit( @RequestBody TransactionDto transactionDto,
                                                           @PathVariable Integer customerId,
                                                           @PathVariable Integer pin) {
        Map<String, BigDecimal> depositedTransaction = transactionService.deposit(transactionDto, customerId, pin);
        return ResponseEntity.ok(depositedTransaction);
    }

    @PostMapping("/withdraw/customerId/{customerId}/{pin}")
    public ResponseEntity<Map<String, BigDecimal>> withdraw( @RequestBody TransactionDto transactionDto,
                                                            @PathVariable Integer customerId,
                                                            @PathVariable Integer pin) {
        Map<String, BigDecimal> withdrawnTransaction = transactionService.withdraw(transactionDto, customerId, pin);
        return ResponseEntity.ok(withdrawnTransaction);
    }
    @GetMapping("/history")
    public ResponseEntity<List<TransactionDto>> getTransactionHistory() {
        List<TransactionDto> transactionHistory = transactionService.getTransactionHistory();
        return ResponseEntity.ok(transactionHistory);
    }

    @GetMapping("/customer_id/{customerId}")
    public ResponseEntity<List<TransactionDto>> getTransactionHistoryByCustomerId(@PathVariable("customerId") Integer customerId) {
        // Retrieve the transaction history for the given customer ID
        List<TransactionDto> transactionDtoList = transactionService.getTransactionHistoryByCustomerId(customerId);

        // Check if the transaction history is empty
        if (transactionDtoList.isEmpty()) {
            // Return a 404 Not Found response if the transaction history is empty
            return ResponseEntity.notFound().build();
        } else {
            // Return the transaction history with a 200 OK response
            return ResponseEntity.ok(transactionDtoList);
        }
    }


}
