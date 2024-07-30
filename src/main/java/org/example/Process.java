package org.example;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Process {
    private static int nextAccountId = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Process process = new Process();

        // Client creation with lambda
        Supplier<Client> seanSupplier = () -> {
            System.out.println("Enter client details for Sean:");
            System.out.println("=====================================");
            return createClientFromUserInput(scanner);
        };

        process.processClient(
                seanSupplier,
                client -> client.getBankBalance() < 5000,
                client -> createAccount(client, accountLevel -> {
                    if (accountLevel < 5000) return org.example.accountLevel.SILVER;
                    else if (accountLevel < 10000) return org.example.accountLevel.GOLD;
                    else if (accountLevel < 100000) return org.example.accountLevel.DIAMOND;
                    else return org.example.accountLevel.BLACK;
                }),
                account -> System.out.println(account.toString())
        );

        System.out.println("\n\n=== Processing Withdrawal ===");
        double amount = 1050;

        // Another client with lambda
        Supplier<Client> pennySupplier = () -> {
            System.out.println("Enter client details for Penny:");
            System.out.println("=====================================");
            return createClientFromUserInput(scanner);
        };

        process.processClient(
                pennySupplier,
                client -> {
                    client.setBankBalance(client.getBankBalance() - amount);
                    return client.getBankBalance() >= 0;
                },
                account -> amount <= account.getLimit(),
                client -> createAccount(client, accountLevel -> {
                    if (accountLevel < 5000) return org.example.accountLevel.SILVER;
                    else if (accountLevel < 10000) return org.example.accountLevel.GOLD;
                    else if (accountLevel < 100000) return org.example.accountLevel.DIAMOND;
                    else return org.example.accountLevel.BLACK;
                }),
                account -> System.out.print("Customer " + account.getName() + "'s balance is " + account.getBalance() + " and their account limit is " + (account.getLimit() - amount)),
                () -> System.out.println("It is over the limit!"),
                () -> System.out.println("You don't have enough money!")
        );
    }

    private static Client createClientFromUserInput(Scanner scanner) {
        System.out.println("Enter client name:");
        String name = scanner.nextLine();

        System.out.println("Enter client age:");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter client city:");
        String city = scanner.nextLine();

        System.out.println("Enter client phone:");
        String phone = scanner.nextLine();

        System.out.println("Enter client email:");
        String email = scanner.nextLine();

        System.out.println("Enter client gender (MALE/FEMALE):");
        gender gender = org.example.gender.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("Enter account type (PERSONAL/BUSINESS):");
        accountType accountType = org.example.accountType.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("Enter initial bank balance:");
        double balance = Double.parseDouble(scanner.nextLine());

        return new Client(name, age, city, phone, email, gender, accountType, balance);
    }


    private static Account createAccount(Client client, Function<Double, accountLevel> accountLevelFunction) {
        Account account = new Account(nextAccountId++, client.getAccountType());
        account.setName(client.getName());
        account.setBalance(client.getBankBalance());

        accountLevel level = accountLevelFunction.apply(client.getBankBalance());
        account.setAccountLevel(level);

        // Set account limit based on account level
        int limit = switch (level) {
            case SILVER -> 1000;
            case GOLD -> 3000;
            case DIAMOND -> 10000;
            default -> Integer.MAX_VALUE; // No limit
        };
        account.setLimit(limit);

        return account;
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
