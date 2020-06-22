package com.codegym.service.Impl;

import com.codegym.model.Account;
import com.codegym.repository.AccountRepository;
import com.codegym.service.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AccountService implements Service<Account> {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public void save(Account object) {
        accountRepository.save(object);
    }

    @Override
    public Account findById(long id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
        accountRepository.deleteById(id);
    }
}
