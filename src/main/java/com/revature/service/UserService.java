package com.revature.service;

import java.util.List;

import com.revature.entity.User;
import com.revature.exception.UserLoginException;
import com.revature.exception.UserRegistrationException;
import com.revature.repository.UserDao;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public User validateNewCredentials(User newUserCredentials){
        String message;
        if(checkUsernamePasswordLength(newUserCredentials)){
            if(checkUsernameIsUnique(newUserCredentials)){
                return userDao.createUser(newUserCredentials);
            }else{
                message = "That username has already been taken";
            }
        } else {
            message = "Usernames and Passwords have to be between 5 and 30 characters long";
        }
        throw new UserRegistrationException("User could not be registered. %s. Please try again.".formatted(message));
    }

    public User checkLoginCredentials(User credentials) {
        for(User user: userDao.getAllUsers()){
            boolean usernameMatches = user.getUsername().equals(credentials.getUsername());
            boolean passwordMatches = user.getPassword().equals(credentials.getPassword());
            if(usernameMatches && passwordMatches){
                return user;
            }
        }
        throw new UserLoginException("Credentials are invalid. Please try again.");
    }

    private boolean checkUsernamePasswordLength(User newUserCredentials){
        boolean usernameIsValid = newUserCredentials.getUsername().length() >= 5 && newUserCredentials.getUsername().length() <= 30;
        boolean passwordIsValid = newUserCredentials.getPassword().length() >= 5 && newUserCredentials.getPassword().length() <= 30;
        return usernameIsValid && passwordIsValid;
    }

    private boolean checkUsernameIsUnique(User newUserCredentials){
        boolean usernameIsUnique = true;
        List<User> users = userDao.getAllUsers();
        for(User user: users){
            if(newUserCredentials.getUsername().equals(user.getUsername())){
                usernameIsUnique = false;
                break;
            }
        }
        return usernameIsUnique;
    }
}
