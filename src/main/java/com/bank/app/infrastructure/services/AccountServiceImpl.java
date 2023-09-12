package com.bank.app.infrastructure.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.repository.AccountRepository;
import com.bank.app.usecase.account.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Account createAccount(Account account) {
        return this.accountRepository.insert(account);
    }

    @Override
    public void insertMany(Iterable<Account> account) {
        this.accountRepository.saveAll(account);
    }

    @Override
    public void deleteById(String id) {
        this.accountRepository.deleteById(id);
    }

    @Override
    public Account getAccountById(String id) {
        Optional<Account> client = this.accountRepository.findById(id);
        return client.isPresent() ? client.get() : null;
    }

    @Override
    public List<Account> getAll() {
        return this.accountRepository.findAll();
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }
    @Override
    public Account findByIdAfterActive(String id) {
        Optional<Account> account = this.accountRepository.findById(id);
        if (account.isPresent()) {
            Account update = account.get();
            update.setIsActive(true);
            this.accountRepository.save(update);
            return update;
        }
        return null;
    }
}
