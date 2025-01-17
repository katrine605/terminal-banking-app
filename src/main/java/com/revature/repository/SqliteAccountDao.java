package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.entity.Account;
import com.revature.exception.AccountSQLException;
import com.revature.utility.DatabaseConnector;

public class SqliteAccountDao implements AccountDao {

    @Override
    public Account createAccount(Account newAccountInfo) {
       String sql = "INSERT INTO account(accountName,balance,accountHolderId) VALUES(?,?,?)";
        try(Connection connection = DatabaseConnector.createConnection()) {
           PreparedStatement preparedStatement = connection.prepareStatement(sql); 
           preparedStatement.setString(1, newAccountInfo.getAccountName());
           preparedStatement.setDouble(2, newAccountInfo.getBalance());
           preparedStatement.setInt(3, newAccountInfo.getAccountHolderId());
           int result = preparedStatement.executeUpdate();
           if(result == 1){
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                Account newAccount = new Account(resultSet.getInt(1),newAccountInfo.getAccountName(),newAccountInfo.getBalance(),newAccountInfo.getAccountHolderId());
                return newAccount;
            }
           }
           throw new AccountSQLException("Account could not be created, please try again");
        } catch (SQLException e) {
            throw new AccountSQLException(e.getMessage());
        }
    }

    @Override
    public List<Account> getAllUserAccounts(Integer accountHolderId) {
        String sql = "SELECT * FROM account WHERE accountHolderId = ?";
        try(Connection connection = DatabaseConnector.createConnection()) {
           PreparedStatement preparedStatement = connection.prepareStatement(sql); 
           preparedStatement.setInt(1, accountHolderId);
           ResultSet result = preparedStatement.executeQuery();
           List<Account> userAccounts = new ArrayList<>();
            while(result.next()){
                Account account = new Account();
                account.setId(result.getInt("id"));
                account.setAccountName(result.getString("accountName"));
                account.setBalance(result.getDouble("balance"));
                account.setAccountHolderId(result.getInt("accountHolderId"));
                userAccounts.add(account);
            }
            return userAccounts;
        } catch (SQLException e) {
            throw new AccountSQLException(e.getMessage());
        }
    }

    @Override
    public Account getAccountById(Integer accountId) {
        String sql = "SELECT * FROM account WHERE id = ?";
        try(Connection connection = DatabaseConnector.createConnection()) {
           PreparedStatement preparedStatement = connection.prepareStatement(sql); 
           preparedStatement.setInt(1, accountId);
           ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                Account account = new Account();
                account.setId(result.getInt("id"));
                account.setAccountName(result.getString("accountName"));
                account.setBalance(result.getDouble("balance"));
                account.setAccountHolderId(result.getInt("accountHolderId"));
                return account;
            }
            throw new AccountSQLException("Account could not be found, please try again");
        } catch (SQLException e) {
            throw new AccountSQLException(e.getMessage());
        }
    }

    @Override
    public Account updateAccountBalance(Account accountToBeUpdated) {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        try(Connection connection = DatabaseConnector.createConnection()) {
           PreparedStatement preparedStatement = connection.prepareStatement(sql); 
           preparedStatement.setDouble(1, accountToBeUpdated.getBalance());
           preparedStatement.setInt(2, accountToBeUpdated.getId());
           int result = preparedStatement.executeUpdate();
           if(result == 1){
            return accountToBeUpdated;
           }
           throw new AccountSQLException("Balance could not be updated, please try again");
        } catch (SQLException e) {
            throw new AccountSQLException(e.getMessage());
        }
    }

    @Override
    public void deleteAccount(Account accountToBeDeleted) {
        String sql = "DELETE FROM account WHERE id = ?";
        try(Connection connection = DatabaseConnector.createConnection()) {
           PreparedStatement preparedStatement = connection.prepareStatement(sql); 
           preparedStatement.setInt(1, accountToBeDeleted.getId());
           int result = preparedStatement.executeUpdate();
           if(result == 1){
            System.out.println("The %s account has been closed".formatted(accountToBeDeleted.getAccountName()));
           }else{
            throw new AccountSQLException("Account could not be closed, please try again");
           }
        } catch (SQLException e) {
            throw new AccountSQLException(e.getMessage());
        }
    }

}
