package main.java.com.streetfit.controllercli;


import main.java.com.streetfit.model.Credentials;
import main.java.com.streetfit.viewcli.LoginViewCLI;

import java.io.IOException;

import main.java.com.streetfit.beans.CredentialsBean;
import main.java.com.streetfit.controller.LoginController;

public class LoginControllerCLI {

    private Credentials credentials;   // Memorizza le credenziali
    private LoginController loginController = new LoginController();
  
    public void start() {
    	
    	int choice;
    	do {	
        choice = LoginViewCLI.showMenu();
    	
    	switch(choice) {
    	case 1:
    		signup();
    		break;
    	case 2:
    		login();
    		break;
    	case 3:
    		LoginViewCLI.showLoginError("Not implemented yet");
    		break;
    	default:
    		throw new IllegalStateException("Not a valid choice");   //just for now
    	}
    	
    	}while(choice != 2);
    	 
   }
    
    private void signup() {
        boolean retry = true;

        while (retry) {
            try {
                CredentialsBean cred = LoginViewCLI.signup();
                loginController.signup(new Credentials(cred.getUsername(), cred.getPassword(), cred.getRole()));
                retry = false; // Signup riuscito, esce dal loop
            } catch (IOException e) {
                throw new IllegalStateException("Error while reading input");
            } catch (IllegalStateException e) {
                LoginViewCLI.showLoginError(e.getMessage());
                retry = LoginViewCLI.askRetry(); // Chiede all’utente se vuole riprovare
                if (!retry) {
                    LoginViewCLI.showBackToMenu();
                }
            }
        }
    }


    private void login() {
        boolean retry = true;

        while (retry) {
            try {
                CredentialsBean cred = LoginViewCLI.authenticate();
                credentials = new Credentials(cred.getUsername(), cred.getPassword(), cred.getRole());

                // Effettua il login
                credentials = loginController.login(credentials.getUsername(), credentials.getPassword());

                // Login riuscito
                LoginViewCLI.showSuccess(credentials.getUsername());
                retry = false;

            } catch (Exception e) {
                LoginViewCLI.showLoginError(e.getMessage());

                if (!LoginViewCLI.askRetry()) {
                    retry = false;
                    LoginViewCLI.showBackToMenu();
                }
            }
        }
    }


    // Metodo per ottenere le credenziali, utile in altre parti del programma
    public Credentials getCred() {
        return credentials;
    }
}

