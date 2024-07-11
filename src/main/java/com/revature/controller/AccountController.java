package com.revature.controller;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.revature.entity.Account;
import com.revature.exception.AccountCreationException;
import com.revature.exception.AccountNotFoundException;
import com.revature.service.AccountService;

public class AccountController {

    private Scanner scanner;
    private AccountService accountService;

    public AccountController(Scanner scanner, AccountService accountService){
        this.scanner = scanner;
        this.accountService = accountService;
    }

    public void promptUserForAccountChoice(Map<String,String> controlMap){
        try{
            List<Account> userAccounts = accountService.getAllUserAccounts(Integer.valueOf(controlMap.get("UserId")));
            System.out.println("Please see below for a list of your current accounts. Choose the number for the account you would like view: ");
            for(int i = 0; i < userAccounts.size(); i++){
                System.out.println("%d. %s".formatted(i+1, userAccounts.get(i).getAccountName()));
            }
            System.out.println("b. Go back to Overview");
            String accountChoice = scanner.nextLine();
            if("b".equals(accountChoice)){
               controlMap.remove("Options");
            } else {
                String accountId = accountService.validateAccountChoice(userAccounts, accountChoice);
                controlMap.put("Options", "ChosenAccount");
                controlMap.put("AccountId", accountId);
            }
        }catch(AccountNotFoundException ex){
            System.out.println(ex.getMessage());
            controlMap.remove("Options");
        }
    }

    public void promptUserForAccountInfo(Map<String, String> controlMap) {
        try{
            System.out.println("In order to open a new account at the bank, please answer the following questions: ");
            System.out.println("What would you like to name your new account?");
            String accountName = scanner.nextLine();
            System.out.println("Please enter the starting balance of the account (make sure to format the balance as 00.00):");
            String accountBalance = scanner.nextLine();
            Account newAccount = accountService.createNewAccount(accountName, accountBalance, Integer.valueOf(controlMap.get("UserId")));
            System.out.println("Your new account '%s' has been created with a starting balance of %f!".formatted(newAccount.getAccountName(), newAccount.getBalance())); 
        }catch(AccountCreationException ex){
            System.out.println(ex.getMessage());
        }finally{
            controlMap.remove("Options");
        }
    }

    public void getAccountInfo(Map<String,String> controlMap){
        System.out.println(controlMap.keySet());
    }

}
