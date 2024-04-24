package com.texas.atm.service.impl;

import com.texas.atm.dto.AccountDto;
import com.texas.atm.model.Account;
import com.texas.atm.model.Customer;
import com.texas.atm.repo.AccountRepo;
import com.texas.atm.repo.CustomerRepo;
import com.texas.atm.service.AccountService;
import com.texas.atm.utils.Utility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final CustomerRepo customerRepo;

    public AccountServiceImpl(AccountRepo accountRepo, CustomerRepo customerRepo) {
        this.accountRepo = accountRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto, Integer customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Check if the customer already has an account
        List<Account> existingAccounts = customer.getAccounts();
        if (existingAccounts != null && !existingAccounts.isEmpty()) {
            throw new RuntimeException("Customer already has an account");
        }

        Account account = new Account();
        account.setAccountNumber(Utility.generateAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBalance(accountDto.getBalance());
        account.setCustomer(customer);

        Account savedAccount = accountRepo.save(account);

        // Update the customer's account list
        List<Account> updatedAccounts = new ArrayList<>();
        updatedAccounts.add(savedAccount);
        customer.setAccounts(updatedAccounts);
        customerRepo.save(customer);

        return convertToDto(savedAccount);
    }


    @Override
    public AccountDto getAccountByAccountId(Integer accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return convertToDto(account);
    }

    @Override
    public List<AccountDto> getAll() {
        List<Account> accounts = accountRepo.findAll();
        return accounts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteAccountByAccountId(Integer accountId) {
        Optional<Account> accountOptional = accountRepo.findById(accountId);
        if (accountOptional.isPresent()) {
            accountRepo.deleteById(accountId);
            return "Account Deleted Successfully";
        } else {
            return "Account not found";
        }
    }



    @Override
    public String getAccountNumberById(Integer id) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getAccountNumber();
    }

    @Override
    public String getAccountNameByAccountId(Integer accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Retrieve the customer associated with the account
        Customer customer = account.getCustomer();

        // Return the customer's name
        return customer.getName();
    }

    @Override
    public String getAccountNumberByCustomerId(Integer customerId) {
        // Retrieve the customer by ID
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Retrieve the customer's accounts
        List<Account> accounts = customer.getAccounts();

        // Check if the customer has accounts
        if (accounts != null && !accounts.isEmpty()) {
            // Assuming the customer has only one account, get the first account
            Account account = accounts.get(0);
            return account.getAccountNumber();
        } else {
            throw new RuntimeException("Customer does not have any accounts");
        }
    }



    private AccountDto convertToDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBalance(account.getBalance());
        return accountDto;
    }

}