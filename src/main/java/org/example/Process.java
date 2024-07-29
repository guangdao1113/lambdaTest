package org.example;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Process {
    public static void main(String[] args) {
        Process process = new Process();
        process.create(() -> new Client("Sean", 35, "vancouver", "123-456-7890", "sean@gmail.com", gender.MALE,
                accountType.PERSONAL, 2315.30), m -> 5000 > m.getBankBalance(), m -> {
            Account account = new Account(1, accountType.PERSONAL);
            account.setAccountLevel(accountLevel.SILVER);
            account.setLimit(1000);
            return account;
        }, f -> System.out.println("Customer Sean's account level is " + f.getAccountLevel() + " and his daily limit is " + f.getLimit()));

        double amount = 900;
        process.withdraw(() -> new Client("penny", 36, "vancouver", "123-456-7891", "sean@gmail.com", gender.FEMALE,
                accountType.PERSONAL, 1100), m -> {boolean b; m.withdraw(amount); b = m.getBankBalance() >= 0; return b;}, m -> amount <= m.getLimit(),
                m -> {
            Account account = new Account(2, accountType.PERSONAL);
            account.setAccountLevel(accountLevel.SILVER);
            account.setLimit(1000);
            return account;
        }, f -> System.out.println(" and his account limit is " + (f.getLimit() - amount)),
                f -> System.out.print("Customer " +  f.getName() + "s balance is " + (f.getBankBalance())));
    }

    void create(Supplier<Client> userInfo, Predicate<Client> filter, Function<Client, Account> account, Consumer<Account> bankSystem) {
        Client client = userInfo.get();
        if (filter.test(client)) {
            Account userAccount = account.apply(client);
            bankSystem.accept(userAccount);
        }
    }

    void withdraw(Supplier<Client> user, Predicate<Client> checkBalance, Predicate<Account> checkLimit, Function<Client, Account> cashMachine, Consumer<Account> bankSystem, Consumer<Client> clientBalance) {
        Client client = user.get();
        if (checkBalance.test(client)) {
            Account userAccount = cashMachine.apply(client);
            if (checkLimit.test(userAccount)) {
                clientBalance.accept(client);
                bankSystem.accept(userAccount);
            }
            else {System.out.println("It is over the limit!");}
        }
        else {System.out.println("You don't have enough money!");}
    }
}