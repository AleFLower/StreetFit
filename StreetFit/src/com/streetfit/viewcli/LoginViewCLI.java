package com.streetfit.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.streetfit.beans.CredentialsBean;
import com.streetfit.model.Role;
import com.streetfit.utils.CLIHelper;

public class LoginViewCLI {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private LoginViewCLI() {
        // private constructor to prevent instantiation
    }

    public static int showMenu() {
        CLIHelper.print("Welcome to StreetFit");
        CLIHelper.print("1. Signup");
        CLIHelper.print("2. Login");
        CLIHelper.print("3. Login with Google");
        CLIHelper.print("Enter your choice: ");
        try {
            String input = reader.readLine();
            return Integer.parseInt(input);
        } catch (IOException | NumberFormatException e) {
            CLIHelper.printError("Invalid input. Please enter a number.");
            return -1;
        }
    }

    public static CredentialsBean signup() throws IOException {
        try {
            CLIHelper.print("username: ");
            String username = reader.readLine();

            CLIHelper.print("password: ");
            String password = reader.readLine();

            return new CredentialsBean(username, password, Role.PARTICIPANT);
        } catch (IllegalArgumentException e) {
            CLIHelper.printError("Error: " + e.getMessage());
            CLIHelper.print("Please try again.\n");
            return null;
        }
    }

    public static CredentialsBean authenticate() throws IOException {
        CLIHelper.print("username: ");
        String username = reader.readLine();

        CLIHelper.print("password: ");
        String password = reader.readLine();

        return new CredentialsBean(username, password, null);
    }
}

