package com.revature;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.revature.controller.AccountController;
import com.revature.controller.UserController;
import com.revature.repository.AccountDao;
import com.revature.repository.SqliteAccountDao;
import com.revature.repository.SqliteUserDao;
import com.revature.repository.UserDao;
import com.revature.service.AccountService;
import com.revature.service.UserService;

public class App 
{
    public static void main( String[] args )
    {
        try(Scanner scanner = new Scanner(System.in)){
            UserDao userDao = new SqliteUserDao();
            AccountDao accountDao = new SqliteAccountDao();
            UserService userService = new UserService(userDao);
            AccountService accountService = new AccountService(accountDao);
            UserController userController = new UserController(scanner, userService);
            AccountController accountController = new AccountController(scanner, accountService);
            Map<String, String> controlMap = new HashMap<>(); 
            controlMap.put("Session Loop", "true");
            userController.showLogo(false);
            while(Boolean.parseBoolean(controlMap.get("Session Loop"))){
                userController.promptUserForService(controlMap);
                while(controlMap.containsKey("UserId")&& controlMap.containsKey("UserName")){
                    userController.promptLoggedInUserForService(controlMap);
                    if(controlMap.containsKey("Options")){
                        while("ViewAccounts".equals(controlMap.get("Options"))){
                            accountController.promptUserForAccountChoice(controlMap);
                        }
                        while("ChosenAccount".equals(controlMap.get("Options"))){
                            accountController.promptUserForAccountAction(controlMap);
                        }
                        while("CreateAccount".equals(controlMap.get("Options"))){
                            accountController.promptUserForAccountInfo(controlMap);
                    }
                    }
                }
            }
           
        }
    }
}
