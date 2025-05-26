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
    		throw new IllegalStateException("Not implemented yet");  //just for now    		
    	default:
    		throw new IllegalStateException("Not a valid choice");   //just for now
    	}
    	
    	}while(choice != 2);
    	 
   }
    
    private void signup() {
		
    	try {
			CredentialsBean cred = LoginViewCLI.signup();
			loginController.signup(new Credentials(cred.getUsername(),cred.getPassword(), cred.getRole()));
			
		} catch (IOException e) {
			
		throw new IllegalStateException("Error");  //just for now	
		}
    
	}

	private void login() {
    
 // Recupera le credenziali dall'interfaccia di login
    try {
      CredentialsBean cred = LoginViewCLI.authenticate();  // La view gestisce l'input dell'utente
    credentials = new Credentials(cred.getUsername(), cred.getPassword(), cred.getRole());
    } catch (Exception e) {
    	throw new IllegalArgumentException("Error during authentication: " + e.getMessage());
       
    }

    
        // Passa le credenziali a LoginController per la logica di autenticazione
       
        try {
            credentials = loginController.login(credentials.getUsername(), credentials.getPassword());
        } catch (RuntimeException e) {
        	throw new IllegalStateException("Authentication failed: " + e.getMessage());
        }
    }

    // Metodo per ottenere le credenziali, utile in altre parti del programma
    public Credentials getCred() {
        return credentials;
    }
}

