package com.streetfit.controller;

import com.streetfit.dao.FactorySingletonDAO;
import com.streetfit.dao.LoginDAO;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Credentials;

public class LoginController implements Controller {
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

            Credentials credentials = loginDAO.getCredentials(username, password);   
            return credentials;
        } catch (DAOException e) {
            throw new RuntimeException("Authentication error: " + e.getMessage());
        }
    }
}
