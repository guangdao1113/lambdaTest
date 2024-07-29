package org.example;

import java.util.Objects;

public class Client {
    private String name;
    private int age;
    private String address;
    private String phone;
    private String email;
    private gender gender;
    private accountType accountType;
    private double bankBalance;

    public Client(String name, int age, String address, String phone, String email, org.example.gender gender, org.example.accountType accountType, double bankBalance) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.accountType = accountType;
        this.bankBalance = bankBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public org.example.gender getGender() {
        return gender;
    }

    public void setGender(org.example.gender gender) {
        this.gender = gender;
    }

    public org.example.accountType getAccountType() {
        return accountType;
    }

    public void setAccountType(org.example.accountType accountType) {
        this.accountType = accountType;
    }

    public double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(double bankBalance) {
        this.bankBalance = bankBalance;
    }

    public void withdraw(double amount) {
        this.bankBalance -= amount;
        this.setBankBalance(this.bankBalance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return age == client.age && Double.compare(bankBalance, client.bankBalance) == 0 && Objects.equals(name, client.name) && Objects.equals(address, client.address) && Objects.equals(phone, client.phone) && Objects.equals(email, client.email) && gender == client.gender && accountType == client.accountType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, address, phone, email, gender, accountType, bankBalance);
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", accountType='" + accountType + '\'' +
                ", bankBalance=" + bankBalance +
                '}';
    }
}