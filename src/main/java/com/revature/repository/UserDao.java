package com.revature.repository;

import java.util.List;

import com.revature.entity.User;

public interface UserDao {
    User createUser(User newUserCredentials);
    List<User> getAllUsers();
    User getUserById(Integer id);
}
