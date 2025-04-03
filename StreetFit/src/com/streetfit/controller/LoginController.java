package com.streetfit.controller;

import com.streetfit.DAO.FactorySingletonDAO;
import com.streetfit.DAO.LoginDAO;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Credentials;

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
<<<<<<< HEAD
            credentials = loginDAO.getCredentials(username, password);  
=======
            credentials = loginDAO.getCredentials(username, password);   
>>>>>>> 0365f24 (Modified package name)
            return credentials;
        } catch (DAOException e) {
            throw new RuntimeException("Authentication error: " + e.getMessage());
        }
    }
}
