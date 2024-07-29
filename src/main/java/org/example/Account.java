package org.example;

public class Account {
    private int id;
    private accountType accountType;
    private double limit;
    private accountLevel accountLevel;

    public Account(int id, accountType accountType) {
        this.id = id;
        this.accountType = accountType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public accountType getAccountType() {
        return accountType;
    }

    public void setAccountType(accountType accountType) {
        this.accountType = accountType;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public org.example.accountLevel getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(org.example.accountLevel accountLevel) {
        this.accountLevel = accountLevel;
    }
}
