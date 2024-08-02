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
    }

    void updateProfile (String email, List<Client> clients, String newPhoneNumber) {
        clients.stream().filter(client -> (email).equals(client.getEmail())).findFirst().ifPresent(c -> {c.setPhone(newPhoneNumber);
        System.out.println(c);});
    }
}