package com.streetfit.controllercli;


import com.streetfit.model.Credentials;
import com.streetfit.viewcli.LoginViewCLI;
import com.streetfit.beans.CredentialsBean;
import com.streetfit.controller.LoginController;

public class LoginControllerCLI implements Controller {

    private Credentials credentials;   // Memorizza le credenziali
    
    @Override
    public void start() {
        // Recupera le credenziali dall'interfaccia di login
        try {
          CredentialsBean cred = LoginViewCLI.authenticate();  // La view gestisce l'input dell'utente
        credentials = new Credentials(cred.getUsername(), cred.getPassword(), cred.getRole());
        } catch (Exception e) {
        	throw new IllegalArgumentException("Error during authentication: " + e.getMessage());
           
        }

        
            // Passa le credenziali a LoginController per la logica di autenticazione
            LoginController loginController = new LoginController();
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

