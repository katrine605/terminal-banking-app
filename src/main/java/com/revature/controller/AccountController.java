package com.revature.controller;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.revature.entity.Account;
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
            if(accountChoice.equals("b")){
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

    public void getAccountInfo(Map<String,String> controlMap){
        System.out.println(controlMap.keySet());
    }
}
