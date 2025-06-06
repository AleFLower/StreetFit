package main.java.com.streetfit.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import main.java.com.streetfit.beans.CredentialsBean;
import main.java.com.streetfit.model.Role;
import main.java.com.streetfit.utils.CLIHelper;

public class LoginViewCLI {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private LoginViewCLI() {
        // private constructor to prevent instantiation
    }

    public static int showMenu() {
        int choice = -1;

        while (true) {
            CLIHelper.print("=== Welcome to StreetFit ===");
            CLIHelper.print("1. Sign up");
            CLIHelper.print("2. Log in");
            CLIHelper.print("3. Log in with Google");
            CLIHelper.print("Please enter your choice (1-3): ");

            try {
                String input = reader.readLine();
                choice = Integer.parseInt(input.trim());

                if (choice >= 1 && choice <= 3) {
                    return choice;
                } else {
                    CLIHelper.printError("Invalid choice. Please select a number between 1 and 3.");
                }
            } catch (IOException e) {
                CLIHelper.printError("Input error. Please try again.");
            } catch (NumberFormatException e) {
                CLIHelper.printError("Invalid input. Please enter a number.");
            }
        }
    }

    
    public static void showSuccess(String username) {
        CLIHelper.print("Login successful. Welcome " + username + "!");
    }

    public static void showLoginError(String message) {
        CLIHelper.printError("Login failed: " + message);
    }

    public static boolean askRetry() {
        CLIHelper.print("Do you want to retry? (y/n): ");
        try {
            String input = reader.readLine().trim().toLowerCase();
            return input.equals("y");
        } catch (IOException e) {
            CLIHelper.printError("Errore while reading input.");
            return false;
        }
    }

    public static void showBackToMenu() {
        CLIHelper.print("Back to home page...");
    }


    public static CredentialsBean signup() throws IOException {
        String username;
        String password;

        // Username validation loop
        boolean valid = false;
        do {
            CLIHelper.print("Username: ");
            username = reader.readLine().trim();

            if (username.isEmpty()) {
                CLIHelper.printError("Username cannot be empty.");
            } else if (username.length() < 4) {
                CLIHelper.printError("Username too short (minimum 4 characters).");
            } else {
                valid = true;
            }
        } while (!valid);

        // Password validation loop
        valid = false;
        do {
            CLIHelper.print("Password: ");
            password = reader.readLine().trim();

            if (password.isEmpty()) {
                CLIHelper.printError("Password cannot be empty.");
            } else if (password.length() < 6) {
                CLIHelper.printError("Password too short (minimum 6 characters).");
            } else {
                valid = true;
            }
        } while (!valid);

        return new CredentialsBean(username, password, Role.PARTICIPANT);
    }


    public static CredentialsBean authenticate() throws IOException {
        String username;
        String password;

        // Username input
        do {
            CLIHelper.print("Username: ");
            username = reader.readLine().trim();

            if (username.isEmpty()) {
                CLIHelper.printError("Username cannot be empty.");
            }
        } while (username.isEmpty());

        // Password input
        do {
            CLIHelper.print("Password: ");
            password = reader.readLine().trim();

            if (password.isEmpty()) {
                CLIHelper.printError("Password cannot be empty.");
            }
        } while (password.isEmpty());

        return new CredentialsBean(username, password, null);
    }

}
