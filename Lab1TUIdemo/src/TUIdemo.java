package com.mybank.tui;

import jexer.TAction;
import jexer.TApplication;
import jexer.TField;
import jexer.TText;
import jexer.TWindow;
import jexer.event.TMenuEvent;
import jexer.menu.TMenu;

/**
 *
 * @author Alexander 'Taurus' Babich
 */
public class TUIdemo extends TApplication {

    private static final int ABOUT_APP = 2000;
    private static final int CUST_INFO = 2010;

    public static void main(String[] args) throws Exception {
        TUIdemo tdemo = new TUIdemo();
        (new Thread(tdemo)).start();
    }

    public TUIdemo() throws Exception {
        super(BackendType.SWING);

        addToolMenu();
        //custom 'File' menu
        TMenu fileMenu = addMenu("&File");
        fileMenu.addItem(CUST_INFO, "&Customer Info");
        fileMenu.addDefaultItem(TMenu.MID_SHELL);
        fileMenu.addSeparator();
        fileMenu.addDefaultItem(TMenu.MID_EXIT);
        //end of 'File' menu  

        addWindowMenu();

        //custom 'Help' menu
        TMenu helpMenu = addMenu("&Help");
        helpMenu.addItem(ABOUT_APP, "&About...");
        //end of 'Help' menu 

        setFocusFollowsMouse(true);
        //Customer window
        ShowCustomerDetails();
    }

    @Override
    protected boolean onMenu(TMenuEvent menu) {
        if (menu.getId() == ABOUT_APP) {
            messageBox("About", "\t\t\t\t\t   Just a simple Jexer demo.\n\nCopyright \u00A9 2019 Alexander \'Taurus\' Babich").show();
            return true;
        }
        if (menu.getId() == CUST_INFO) {
            ShowCustomerDetails();
            return true;
        }
        return super.onMenu(menu);
    }

    private void ShowCustomerDetails() {
    TWindow custWin = addWindow("Customer Window", 2, 1, 40, 10, TWindow.NOZOOMBOX);
    custWin.newStatusBar("Enter valid customer number and press Show...");

    custWin.addLabel("Enter customer number: ", 2, 2);
    TField custNo = custWin.addField(24, 2, 3, false);
    TText details = custWin.addText("Owner Name: \nAccount Type: \nAccount Balance: ", 2, 4, 38, 8);
    custWin.addButton("&Show", 28, 2, new TAction() {
        @Override
        public void DO() {
            try {
                int custNum = Integer.parseInt(custNo.getText());
                // Get customer from the bank
                Bank bank = new Bank();
                Customer customer = bank.getCustomer(custNum);

                if (customer != null) {
                    // Get the first account of the customer
                    Account account = customer.getAccount(0);

                    // Display customer details
                    String ownerName = customer.getFirstName() + " " + customer.getLastName();
                    String accountType = account instanceof CheckingAccount ? "Checking" : "Savings";
                    double accountBalance = account.getBalance();

                    details.setText("Owner Name: " + ownerName + " (id=" + custNum + ")\n" +
                                    "Account Type: " + accountType + "\n" +
                                    "Account Balance: $" + accountBalance);
                } else {
                    details.setText("Customer not found.");
                }
            } catch (Exception e) {
                messageBox("Error", "You must provide a valid customer number!").show();
            }
        }
    });
}

}
