package com.revature.controller;

import java.util.Map;
import java.util.Scanner;

import com.revature.entity.User;
import com.revature.exception.UserLoginException;
import com.revature.exception.UserRegistrationException;
import com.revature.service.UserService;

public class UserController {

    private Scanner scanner;
    private UserService userService;

    public UserController(Scanner scanner, UserService userService){
        this.scanner = scanner;
        this.userService = userService;
    }

    public void promptUserForService(Map<String,String> controlMap){
        System.out.println("Welcome to the Bank!");
        System.out.println("Please choose one of the following options in order to use our banking services:");
        System.out.println("1. Register an account");
        System.out.println("2. Login");
        System.out.println("q. Quit");
        try{
            String userAction = scanner.nextLine();
            switch (userAction) {
                case "1":
                    registerNewUser();
                    break;
                    case "2":
                    User loggedInUser = login();
                    controlMap.put("UserId", loggedInUser.getId().toString());
                    controlMap.put("UserName", loggedInUser.getUsername());
                    break;           
                    case "q":
                    quitApp(controlMap);
            }
        }catch(UserLoginException | UserRegistrationException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void promptLoggedInUserForService(Map<String,String> controlMap){
        System.out.println("Welcome %s!".formatted(controlMap.get("UserName")));
        System.out.println("What would you like to do?");
        System.out.println("1. Create an account");
        System.out.println("2. View my accounts");
        System.out.println("3. Logout");
        System.out.println("q. Quit");
        String userChoice = scanner.nextLine();
        switch (userChoice) {
            case "1":
            controlMap.put("Options", "CreateAccount");
            break;
            case "2":
            controlMap.put("Options", "ViewAccounts");
            break;        
            case "3":
            logout(controlMap);
            break;    
            case "q":
            quitApp(controlMap);
        }
    }

    public void registerNewUser(){
        User newUserCredentials = getUserCredentials();
        User newUser = userService.validateNewCredentials(newUserCredentials);
        System.out.println("A new account has been created for %s. Please login to continue.".formatted(newUser.getUsername()));
    }

    public User login(){
        return userService.checkLoginCredentials(getUserCredentials());

    }

    public void logout(Map<String,String> controlMap){
        controlMap.remove("UserId");
        controlMap.remove("UserName");
        controlMap.remove("Options");
        System.out.println("You have been logged out.");
    }

    public User getUserCredentials(){
        String newUsername;
        String newPassword;

        System.out.print("Please enter a username: ");
        newUsername = scanner.nextLine();
        System.out.print("Please enter a password: ");
        newPassword = scanner.nextLine();
        return new User(newUsername, newPassword);
    }

    public void quitApp(Map<String,String> controlMap){
        if(controlMap.containsKey("UserId")){
            logout(controlMap);
        }
        System.out.println("Thank you for trusting us with your banking services. We hope you have a wonderful day!");
        controlMap.put("Session Loop", "false");
    }

}
