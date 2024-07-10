package com.revature.entity;
import java.util.Objects;

public class Account {
    private Integer id;
    private String accountName;
    private Double balance;
    private Integer accountHolderId;


    public Account() {
    }

    public Account(String accountName, Double balance, Integer accountHolderId) {
        this.accountName = accountName;
        this.balance = balance;
        this.accountHolderId = accountHolderId;
    }

    public Account(Integer id, String accountName, Double balance, Integer accountHolderId) {
        this.id = id;
        this.accountName = accountName;
        this.balance = balance;
        this.accountHolderId = accountHolderId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getBalance() {
        return this.balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getAccountHolderId() {
        return this.accountHolderId;
    }

    public void setAccountHolderId(Integer accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    public Account id(Integer id) {
        setId(id);
        return this;
    }

    public Account accountName(String accountName) {
        setAccountName(accountName);
        return this;
    }

    public Account balance(Double balance) {
        setBalance(balance);
        return this;
    }

    public Account accountHolderId(Integer accountHolderId) {
        setAccountHolderId(accountHolderId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(accountName, account.accountName) && Objects.equals(balance, account.balance) && Objects.equals(accountHolderId, account.accountHolderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountName, balance, accountHolderId);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", balance='" + getBalance() + "'" +
            ", accountHolderId='" + getAccountHolderId() + "'" +
            "}";
    }

}
