package com.texas.atm.service;

import com.texas.atm.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto, Integer customerId);
    AccountDto getAccountByAccountId(Integer accountId);
    List<AccountDto> getAll();
    String deleteAccountByAccountId(Integer id);

    String getAccountNumberById(Integer id);
    String getAccountNameByAccountId(Integer accountId);

    String getAccountNumberByCustomerId(Integer customerId);
}