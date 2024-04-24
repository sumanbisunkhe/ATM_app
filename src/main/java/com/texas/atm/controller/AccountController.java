package com.texas.atm.controller;

import com.texas.atm.dto.AccountDto;
import com.texas.atm.service.AccountService;
import com.texas.atm.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService; // Declare CustomerService

    public AccountController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }


    @PostMapping("/create/{customerId}")
    public ResponseEntity<Map<String, Object>> createAccount( @Valid @RequestBody AccountDto accountDto, @PathVariable Integer customerId) {
        // Assuming you have a method to retrieve the customer's name based on the customerId
        String customerName = customerService.getCustomerNameById(customerId);

        AccountDto createdAccount = accountService.createAccount(accountDto, customerId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", customerName + ", Your Account is Successfully Created....");
        response.put("account", createdAccount);
        return ResponseEntity.ok(response);
    }



    @GetMapping("/get/account_id/{id}")
    public ResponseEntity<Map<String, Object>> getAccountById( @PathVariable("id") Integer accountId) {
        AccountDto accountDto = accountService.getAccountByAccountId(accountId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Account Fetched Successfully...");
        String accountName = accountService.getAccountNameByAccountId(accountId);
        response.put("Account Name",accountName);
        response.put("account", accountDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/all")
    public ResponseEntity<Map<String, Object>> getAllAccounts() {
        List<AccountDto> accountDtoList = accountService.getAll();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Accounts List Fetched Successfully...");
        response.put("accounts", accountDtoList);
        return ResponseEntity.ok(response);
    }



    @DeleteMapping("/delete/account_id/{id}")
    public ResponseEntity<Map<String, Object>> deleteAccount(@PathVariable("id") Integer accountId) {
        // Retrieve the account details before deletion
        AccountDto deletedAccount = accountService.getAccountByAccountId(accountId);

        // Retrieve the account name before deletion
        String accountName = accountService.getAccountNameByAccountId(accountId);

        // Attempt to delete the account and get the deletion message
        String deletionMessage = accountService.deleteAccountByAccountId(accountId);

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("message", deletionMessage);
        response.put("Account Name", accountName);
        response.put("Account Number", deletedAccount.getAccountNumber());

        // Return the response with status code 200 (OK)
        return ResponseEntity.ok(response);
    }



}
