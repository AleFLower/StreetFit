package com.streetfit.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.streetfit.beans.CredentialsBean;
import com.streetfit.model.Role;

public class LoginViewCLI {

    private static final Logger logger = LoggerFactory.getLogger(LoginViewCLI.class);

    private LoginViewCLI() {
        // private constructor to prevent instantiation
    }

    public static int showMenu() {
        Scanner sc = new Scanner(System.in);
        logger.info("Welcome to streetFit");
        logger.info("1.Signup");
        logger.info("2.Login");
        logger.info("3.Login with Google");
        logger.info("Enter your choice: ");
        return sc.nextInt();
    }

    public static CredentialsBean signup() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            logger.info("username: ");
            String username = reader.readLine();

            logger.info("password: ");
            String password = reader.readLine();

            // suppongo che nel mio sistema ci siano un trainer e piu partecipanti per ora
            return new CredentialsBean(username, password, Role.PARTICIPANT);
        } catch (IllegalArgumentException e) {
            logger.info("Error: " + e.getMessage());
            logger.info("Please try again.\n");
        }

        return null;
    }

    public static CredentialsBean authenticate() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                logger.info("username: ");
                String username = reader.readLine();

                logger.info("password: ");
                String password = reader.readLine();

                return new CredentialsBean(username, password, null);
            } catch (IllegalArgumentException e) {
                logger.info("Error: " + e.getMessage());
                logger.info("Please try again.\n");
            }
        }
    }
}
