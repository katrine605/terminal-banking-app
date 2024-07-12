package com.revature.controller;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.revature.entity.Account;
import com.revature.exception.AccountCreationException;
import com.revature.exception.AccountNotFoundException;
import com.revature.exception.AccountUpdateException;
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
            System.out.println("\nPlease see below for a list of your current accounts. Choose the number for the account you would like view: ");
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
            System.out.println("\nIn order to open a new account at the bank, please answer the following questions: ");
            System.out.println("What would you like to name your new account?");
            String accountName = scanner.nextLine();
            System.out.println("Please enter the starting balance of the account:");
            String accountBalance = scanner.nextLine();
            Account newAccount = accountService.createNewAccount(accountName, accountBalance, Integer.valueOf(controlMap.get("UserId")));
            System.out.println("Your new account '%s' has been created with a starting balance of $%.2f!".formatted(newAccount.getAccountName(), newAccount.getBalance())); 
            controlMap.put("Options", "ChosenAccount");
            controlMap.put("AccountId", String.valueOf(newAccount.getId()));
        }catch(AccountCreationException ex){
            System.out.println(ex.getMessage());
            controlMap.remove("Options");
        }
    }

    public void promptUserForAccountAction(Map<String,String> controlMap){
        try{
            Account currentAccount = accountService.getAccountById(Integer.valueOf(controlMap.get("AccountId")));
            System.out.println("\nPlease see below for the account information:");
            System.out.println("Account Name: %s\nAccount Balance: $%.2f".formatted(currentAccount.getAccountName(), currentAccount.getBalance()));
            System.out.println("\nHow would you like to proceed:");
            System.out.println("1. Deposit money into account");
            System.out.println("2. Withdraw money from account");
            System.out.println("3. Close account");
            System.out.println("b. Go back to Overview");
            String accountChoice = scanner.nextLine();
            if("b".equals(accountChoice)){
            controlMap.remove("Options");
            controlMap.remove("AccountId");
            } else {
                if("1".equals(accountChoice) || "2".equals(accountChoice)){
                    String action = "1".equals(accountChoice) ? "deposit" : "withdraw";
                    System.out.println("\nHow much money would you like to %s?".formatted(action));
                    String balance = scanner.nextLine();
                    Account updatedAccount = accountService.updateAccountBalance(currentAccount.getId(), balance, action);
                    System.out.println("The balance of the account has been updated to $%.2f".formatted(updatedAccount.getBalance()));
                } else if("3".equals(accountChoice)){
                    if(currentAccount.getBalance() > 0){
                        throw new AccountUpdateException("Please make sure all money has been withdrawn before closing the account.");
                    } else {
                        accountService.deleteAccount(currentAccount);
                        controlMap.remove("AccountId");
                        controlMap.put("Options", "ViewAccounts");
                    }
                }
                 else {
                    throw new AccountUpdateException("That was not a valid choice. Please try again.");
                }
            }
        }catch(AccountUpdateException ex){
            System.out.println(ex.getMessage());
        }
    }
}   
