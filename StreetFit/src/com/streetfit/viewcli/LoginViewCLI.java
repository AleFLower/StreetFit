package com.streetfit.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.streetfit.beans.CredentialsBean;


public class LoginViewCLI {
    private Scanner sc = new Scanner(System.in);

    public int showMenu() {
        int choice = -1;

        System.out.println("-----STREETFIT-----");
        System.out.println("1. Login");
        System.out.println("2. Quit");

        System.out.print("Please enter your choice: ");
        
        if (sc.hasNextInt()) {
            choice = sc.nextInt();
        } else {
            System.out.println("Error: please insert a valid number");
            sc.next(); // Pulisce l'input errato
        }

        return choice;
    }

    public static CredentialsBean authenticate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    

        while (true) {
            try {
                System.out.print("username: ");
                String username = reader.readLine();

                System.out.print("password: ");
                String password = reader.readLine();

                return new CredentialsBean(username, password, null);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
    }

}

