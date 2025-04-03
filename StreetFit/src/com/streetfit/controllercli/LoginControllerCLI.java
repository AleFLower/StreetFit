package com.streetfit.controllercli;


import com.streetfit.model.Credentials;
import com.streetfit.viewcli.LoginViewCLI;
import com.streetfit.beans.CredentialsBean;
import com.streetfit.controller.Controller;
import com.streetfit.controller.LoginController;

public class LoginControllerCLI implements Controller {

    private Credentials credentials;   // Memorizza le credenziali
    
    @Override
    public void start() {
        // Recupera le credenziali dall'interfaccia di login
        try {
            Credentials cred = LoginViewCLI.authenticate();  // La view gestisce l'input dell'utente
            credentials = new Credentials(cred.getUsername(), cred.getPassword(), cred.getRole());
        } catch (Exception e) {
        	 throw new RuntimeException("Error: " + e.getMessage());
           
        }

        // Se le credenziali sono valide, continua con il login
        if (credentials != null) {
            // Passa le credenziali a LoginController per la logica di autenticazione
            LoginController loginController = new LoginController();
            try {
                credentials = loginController.login(credentials.getUsername(), credentials.getPassword());
            } catch (RuntimeException e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }
        }
    }

    // Metodo per ottenere le credenziali, utile in altre parti del programma
    public Credentials getCred() {
        return credentials;
    }
}


