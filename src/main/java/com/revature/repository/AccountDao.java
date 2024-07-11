package com.revature.repository;

import java.util.List;

import com.revature.entity.Account;

public interface AccountDao {
    Account createAccount(Account newAccount);
    List<Account> getAllUserAccounts(Integer accountHolderId);
    Account getAccountById(Integer accountId);
    Account updateAccountBalance(Account account);
} 