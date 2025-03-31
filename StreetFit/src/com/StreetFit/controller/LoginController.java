package com.StreetFit.controller;

import com.StreetFit.DAO.FactorySingletonDAO;
import com.StreetFit.DAO.LoginDAO;
import com.StreetFit.exception.DAOException;
import com.StreetFit.model.Credentials;

public class LoginController implements Controller {
    private Credentials credentials;
    private LoginDAO loginDAO;

    public LoginController() {
        try {
            this.loginDAO = FactorySingletonDAO.getDefaultDAO().getLoginDAO();  
        } catch (RuntimeException e) {
           throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void start() {
        // Il metodo start non fa nulla qui, gestito da CLI o FX
    }

    // Metodo per gestire il login e restituire le credenziali
    public Credentials login(String username, String password) {
        if (loginDAO == null) {
            throw new IllegalStateException("Error");
        }

        try {
            credentials = loginDAO.getCredentials(username, password);  
            return credentials;
        } catch (DAOException e) {
            throw new RuntimeException("Authentication error: " + e.getMessage());
        }
    }
}
