package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Process {
    public static void main(String[] args) {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client("Sean", 35, "vancouver", "123-456-7890", "sean@gmail.com", gender.MALE, accountType.PERSONAL, 2500.00));
        clients.add(new Client("Penny", 36, "burnaby", "123-456-7891", "penny@gmail.com", gender.FEMALE, accountType.PERSONAL, 5500.00));
        clients.add(new Client("Jason", 50, "toronto", "456-612-7890", "jason@hotmail.com", gender.MALE, accountType.PERSONAL, 252500.00));
        clients.add(new Client("Kelly", 37, "richmond", "123-456-7892", "kelly@gmail.com", gender.FEMALE, accountType.PERSONAL, 7500.00));
        clients.add(new Client("Tony", 38, "vancouver", "123-456-7896", "tony@gmail.com", gender.MALE, accountType.BUSINESS, 32500.00));
        clients.stream().map(Client::getName).forEach(System.out::println);
        System.out.println("===============================");
        clients.stream().
                max(Comparator.comparingInt(Client::getAge)).       // terminal
                map(Client::getEmail).                              // intermediate
                ifPresent(System.out::println);                     // optional class? terminal
        System.out.println("===============================");
        clients.stream()
                .sorted(Comparator.comparingInt(Client::getAge).reversed())         // intermediate
                .map(Client::getName)                                               // intermediate
                .forEach(System.out::println);                                      // terminal
        System.out.println("===============================");
        clients.stream().
                filter(client -> client.getEmail().endsWith("@gmail.com")).         // intermediate
                map(Client::getName).                                               // intermediate
                forEach(System.out::println);                                       // terminal
        System.out.println("===============================");
        List<String> femaleEmails = clients.stream()
                .filter(client -> client.getGender() == gender.FEMALE)              // intermediate
                .sorted(Comparator.comparingInt(Client::getAge).reversed())         // intermediate
                .map(Client::getEmail)                                              // intermediate
                .toList();                                                          // terminal

        femaleEmails.forEach(System.out::println);
        System.out.println("===============================");

        clients.stream().
                max(Comparator.comparingDouble(Client::getBankBalance))
                .map(c -> c.getName() + " " +  c.getBankBalance())
                .ifPresent(System.out::println);
        System.out.println("===============================");

        Process process = new Process();
        String email = "penny@gmail.com";
        String phoneNumber = "789-543-0000";
        process.updateProfile(email, clients, phoneNumber);
        System.out.println("===============================");

        List<String> result = clients.stream().sorted(Comparator.comparingInt(Client::getAge)).filter(s -> s.getAge() > 35).limit(3).skip(1)
                .map(Client::getName).peek(System.out::println).toList();
        System.out.println(result);
        System.out.println("===============================");
        clients.stream().sorted(Comparator.comparingInt(Client::getAge)).takeWhile(s -> s.getAge() < 38).map(Client::getName)
                .forEach(System.out::println);
        System.out.println("===============================");
        clients.stream().map(Client::getBankBalance).reduce(Double::sum).ifPresent(System.out::println);
        System.out.println("===============================");
        System.out.println(clients.stream().filter(c -> c.getBankBalance() > 6000).count() + " clients have balance more than 6000!");
        System.out.println("===============================");
        System.out.println(clients.stream().allMatch(c -> c.getBankBalance() > 6000)
                ? "All clients have more than 6000"
                : "Not all clients have more than 6000");
    }

    void updateProfile (String email, List<Client> clients, String newPhoneNumber) {
        clients.stream().
                filter(client -> (email).equals(client.getEmail())).        // intermediate
                findAny().                                                // terminal
                ifPresent(c -> {c.setPhone(newPhoneNumber);                 // terminal
                System.out.println(c.getName() + "'s phone number is " + c.getPhone());});
    }
}