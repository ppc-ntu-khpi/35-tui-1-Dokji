/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mybank.tui;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer getCustomer(int index) {
        if (index >= 0 && index < customers.size()) {
            return customers.get(index);
        }
        return null;
    }

    public int getNumOfCustomers() {
        return customers.size();
    }

    public void loadData(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");
                int numOfAccounts = Integer.parseInt(data[2]);

                Customer customer = new Customer(data[0], data[1]);
                for (int i = 0; i < numOfAccounts; i++) {
                    line = reader.readLine();
                    String[] accountData = line.split("\t");
                    if (accountData[0].equals("S")) {
                        SavingsAccount account = new SavingsAccount(Double.parseDouble(accountData[1]), Double.parseDouble(accountData[2]));
                        customer.addAccount(account);
                    } else if (accountData[0].equals("C")) {
                        CheckingAccount account = new CheckingAccount(Double.parseDouble(accountData[1]), Double.parseDouble(accountData[2]));
                        customer.addAccount(account);
                    }
                }

                addCustomer(customer);
            }
        }
    }
}

