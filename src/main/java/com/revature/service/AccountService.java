package com.revature.service;

import java.util.List;

import com.revature.entity.Account;
import com.revature.exception.AccountNotFoundException;
import com.revature.repository.AccountDao;

public class AccountService {
    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> getAllUserAccounts(Integer userId){
        List<Account> userAccounts = accountDao.getAllUserAccounts(userId);
        if(userAccounts.isEmpty()){
            throw new AccountNotFoundException("You don't have any accounts yet. Please create one to get started.");
        }
        return userAccounts;
    }

    public String validateAccountChoice(List<Account> userAccounts, String accountChoice) {
        try{
            if(Integer.valueOf(accountChoice) > 0 && Integer.valueOf(accountChoice) <= userAccounts.size()){
                return String.valueOf(Integer.valueOf(accountChoice)-1);
            }
            throw new AccountNotFoundException("That was not a valid option. Please pick a number between 1 and %d.".formatted(userAccounts.size()));
        } catch (NumberFormatException ex){
            throw new AccountNotFoundException("That was not a valid option. Please pick a number between 1 and %d.".formatted(userAccounts.size()));
        }
    }

}
