package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.entity.User;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

public class SqliteUserDao implements UserDao{

    @Override
    public User createUser(User newUserCredentials) {
        String sql = "INSERT INTO user(username,password) VALUES(?,?)";
        try(Connection connection = DatabaseConnector.createConnection()) {
           PreparedStatement preparedStatement = connection.prepareStatement(sql); 
           preparedStatement.setString(1, newUserCredentials.getUsername());
           preparedStatement.setString(2, newUserCredentials.getPassword());
           int result = preparedStatement.executeUpdate();
           if(result == 1){
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                User newUser = new User(resultSet.getInt(1),newUserCredentials.getUsername(),newUserCredentials.getPassword());
                return newUser;
            }
           }
           throw new UserSQLException("User could not be created, please try again");
        } catch (SQLException e) {
            throw new UserSQLException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user";
        try (Connection connection = DatabaseConnector.createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while(result.next()){
                User userRecord = new User();
                userRecord.setId(result.getInt("id"));
                userRecord.setUsername(result.getString("username"));
                userRecord.setPassword(result.getString("password"));
                users.add(userRecord);
            }
            return users;
        }catch(SQLException exception){
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public User getUserById(Integer id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try(Connection connection = DatabaseConnector.createConnection()) {
           PreparedStatement preparedStatement = connection.prepareStatement(sql); 
           preparedStatement.setInt(1, id);
           ResultSet result = preparedStatement.executeQuery();
           while(result.next()){
           User userRecord = new User();
           userRecord.setId(result.getInt("id"));
           userRecord.setUsername(result.getString("username"));
           userRecord.setPassword(result.getString("password"));
           return userRecord;
           }
           throw new UserSQLException("User could not be found, please try again");
        } catch (SQLException e) {
            throw new UserSQLException(e.getMessage());
        }
    }


}
