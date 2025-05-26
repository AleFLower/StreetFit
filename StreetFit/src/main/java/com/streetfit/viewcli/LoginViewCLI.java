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
        String username = "";
        String password = "";

        // Username validation loop
        while (true) {
            CLIHelper.print("Username: ");
            username = reader.readLine().trim();

            // Check if username is empty or too short
            if (username.isEmpty()) {
                CLIHelper.printError("Username cannot be empty.");
                continue;
            }
            if (username.length() < 4) {
                CLIHelper.printError("Username too short (minimum 4 characters).");
                continue;
            }
            // Check if username contains only valid characters (letters, numbers, .-_)
            if (!username.matches("^[a-zA-Z0-9._-]+$")) {
                CLIHelper.printError("Username can only contain letters, digits, '.', '_', or '-'.");
                continue;
            }
            break;
        }

        // Password validation loop
        while (true) {
            CLIHelper.print("Password: ");
            password = reader.readLine().trim();

            // Check if password is empty or too short
            if (password.isEmpty()) {
                CLIHelper.printError("Password cannot be empty.");
                continue;
            }
            if (password.length() < 6) {
                CLIHelper.printError("Password too short (minimum 6 characters).");
                continue;
            }
            // Check if password contains at least one digit
            if (!password.matches(".*\\d.*")) {
                CLIHelper.printError("Password must contain at least one digit.");
                continue;
            }
            // Check if password contains both upper and lowercase letters
            if (!password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*")) {
                CLIHelper.printError("Password must contain both uppercase and lowercase letters.");
                continue;
            }
            break;
        }

        // Return a new CredentialsBean with the validated username and password
        return new CredentialsBean(username, password, Role.PARTICIPANT);
    }

    public static CredentialsBean authenticate() throws IOException {
        String username = "";
        String password = "";

        // Username validation loop
        while (true) {
            CLIHelper.print("Username: ");
            username = reader.readLine().trim();

            if (username.isEmpty()) {
                CLIHelper.printError("Username cannot be empty.");
                continue;
            }
            break;
        }

        // Password validation loop
        while (true) {
            CLIHelper.print("Password: ");
            password = reader.readLine().trim();

            if (password.isEmpty()) {
                CLIHelper.printError("Password cannot be empty.");
                continue;
            }
            break;
        }

        // Return a new CredentialsBean with the provided username and password
        return new CredentialsBean(username, password, null);
    }
}
