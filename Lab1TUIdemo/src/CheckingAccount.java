/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mybank.tui;

public class CheckingAccount extends Account {
    private double overdraftAmount;

    public CheckingAccount(double balance, double overdraftAmount) {
        super(balance);
        this.overdraftAmount = overdraftAmount;
    }

    public double getOverdraftAmount() {
        return overdraftAmount;
    }

    public void setOverdraftAmount(double overdraftAmount) {
        this.overdraftAmount = overdraftAmount;
    }

    @Override
    public void calculateInterest() {
        // Calculate interest for checking account
    }
}


