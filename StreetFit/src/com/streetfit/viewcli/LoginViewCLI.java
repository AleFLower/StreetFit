package com.streetfit.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.streetfit.beans.CredentialsBean;
import com.streetfit.model.Role;


public class LoginViewCLI {
	
	private LoginViewCLI() {
        // private constructor to prevent instantiation
    }
	
	public static int showMenu() {
		Scanner sc = new Scanner(System.in) ;
			System.out.println("Welcome to streetFit"); //NOSONAR
			System.out.println("1.Signup");  //NOSONAR
			System.out.println("2.Login"); //NOSONAR
			System.out.println("3.Login with Google");  //NOSONAR
			System.out.println("Enter your choice: "); //NOSONAR
			return sc.nextInt();
			
	}
	
	public static CredentialsBean signup()throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
        	
            System.out.print("username: "); //NOSONAR
            String username = reader.readLine();

            System.out.print("password: ");//NOSONAR
            String password = reader.readLine();
            
            //suppongo che nel mio sistema ci siano un trainer e piu partecipanti per ora
            

            return new CredentialsBean(username, password, Role.PARTICIPANT);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());//NOSONAR
            System.out.println("Please try again.\n");//NOSONAR
        }
		
		return null;
		
	}

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