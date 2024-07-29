package org.example;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Process {
    public static void main(String[] args) {
        Process process = new Process();
        process.processClient(
                () -> new Client("Sean", 35, "vancouver", "123-456-7890", "sean@gmail.com", gender.MALE, accountType.PERSONAL, 2315.30),
                m -> 5000 > m.getBankBalance(),
                m -> {
                    Account account = new Account(1, accountType.PERSONAL);
                    account.setAccountLevel(accountLevel.SILVER);
                    account.setLimit(1000);
                    return account;
                },
                f -> System.out.println("Customer Sean's account level is " + f.getAccountLevel() + " and his daily limit is " + f.getLimit())
        );

        double amount = 1050;
        process.processClient(
                () -> new Client("Penny", 36, "vancouver", "123-456-7891", "penny@gmail.com", gender.FEMALE, accountType.PERSONAL, 1100),
                m -> {
                    m.setBankBalance(m.getBankBalance() - amount);
                    return m.getBankBalance() >= 0;
                },
                m -> amount <= m.getLimit(),
                m -> {
                    Account account = new Account(2, accountType.PERSONAL);
                    account.setName(m.getName());
                    account.setBalance(m.getBankBalance());
                    account.setAccountLevel(accountLevel.SILVER);
                    account.setLimit(1000);
                    return account;
                },
                f -> System.out.print("Customer " + f.getName() + "'s balance is " + f.getBalance() + " and his account limit is " + (f.getLimit() - amount)),
                () -> System.out.println("It is over the limit!"),
                () -> System.out.println("You don't have enough money!")
        );
    }

    void processClient(
            Supplier<Client> userInfo,
            Predicate<Client> filter,
            Function<Client, Account> accountCreator,
            Consumer<Account> accountProcessor
    ) {
        Client client = userInfo.get();
        if (filter.test(client)) {
            Account account = accountCreator.apply(client);
            accountProcessor.accept(account);
        }
    }

    void processClient(
            Supplier<Client> userInfo,
            Predicate<Client> checkBalance,
            Predicate<Account> checkLimit,
            Function<Client, Account> accountCreator,
            Consumer<Account> accountProcessor,
            Runnable overLimitAction,
            Runnable insufficientBalanceAction
    ) {
        Client client = userInfo.get();
        if (checkBalance.test(client)) {
            Account account = accountCreator.apply(client);
            if (checkLimit.test(account)) {
                accountProcessor.accept(account);
            } else {
                overLimitAction.run();
            }
        } else {
            insufficientBalanceAction.run();
        }
    }
}
