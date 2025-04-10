package com.streetfit.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.streetfit.beans.CredentialsBean;


public class LoginViewCLI {

    public static CredentialsBean authenticate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    

        while (true) {
            try {
                System.out.print("username: "); //NOSONAR
                String username = reader.readLine();

                System.out.print("password: ");//NOSONAR
                String password = reader.readLine();

                return new CredentialsBean(username, password, null);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());//NOSONAR
                System.out.println("Please try again.\n");//NOSONAR
            }
        }
    }

}

