package com.revature.service;

import java.util.List;

import com.revature.entity.Account;
import com.revature.exception.AccountCreationException;
import com.revature.exception.AccountNotFoundException;
import com.revature.repository.AccountDao;

public class AccountService {
    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account createNewAccount(String accountName, String accountBalance, Integer accountHolderId){
        if(validateNewAccount(accountName, accountBalance, accountHolderId)){
            Account newAccount = new Account(accountName, Double.valueOf(accountBalance), accountHolderId);
            return accountDao.createAccount(newAccount);
        }
        throw new AccountCreationException("The account could not be created. Please try again.");
    }

    public boolean validateNewAccount(String accountName, String accountBalance, Integer accountHolderId){
        List<Account> userAccounts = accountDao.getAllUserAccounts(accountHolderId);
        if(userAccounts.size() > 0){
            for(Account account: userAccounts){
                if(account.getAccountName().equals(accountName)){
                    throw new AccountCreationException("You already have an account with that name. Please try again.");
                }
            }
        }
        
        if(accountName.isEmpty() || accountName.equals("")){
            throw new AccountCreationException("You need to provide a name for the account. Please try again.");
        }
        try{
            Double balance = Double.valueOf(accountBalance);
            if(balance < 0.0){
                throw new AccountCreationException("You can't open an account with a negative balance. Please try again.");
            }
            return true;

        }catch(NumberFormatException ex){
            throw new AccountCreationException("That isn't a valid balance. Please try again.");
        }
        
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
