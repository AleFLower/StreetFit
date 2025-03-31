package com.StreetFit.controllerCLI;


import com.StreetFit.ViewCLI.LoginViewCLI;
import com.StreetFit.beans.CredentialsBean;
import com.StreetFit.controller.Controller;
import com.StreetFit.model.Credentials;
import com.StreetFit.controller.LoginController;

public class LoginControllerCLI implements Controller {

    private Credentials credentials;   // Memorizza le credenziali
    private CredentialsBean cred;
    

    @Override
    public void start() {
        // Recupera le credenziali dall'interfaccia di login
        try {
            cred = LoginViewCLI.authenticate();  // La view gestisce l'input dell'utente
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


