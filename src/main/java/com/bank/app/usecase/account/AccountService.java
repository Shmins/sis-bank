package com.bank.app.usecase.account;

import java.util.List;

import com.bank.app.entity.client.model.Account;

public interface AccountService {
    Account createAccount(Account account);
    void insertMany(Iterable<Account> account);
    void deleteById(String id);
    Account getAccountById(String id);
    List<Account> getAll();
    Account updateAccount(Account account);
    Account findByIdAfterActive(String id);
}
