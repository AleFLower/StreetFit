package com.streetfit.controller;

import com.streetfit.dao.FactorySingletonDAO;



import com.streetfit.dao.Dao;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Credentials;

public class LoginController implements Controller {
    private Dao loginDAO;

    public LoginController() {
        try {
            this.loginDAO = FactorySingletonDAO.getDefaultDAO().getLoginDAO();  
        } catch (RuntimeException e) {
        	throw new IllegalStateException("Failed to initialize LoginController due to DAO error", e);
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
            return loginDAO.getCredentials(username, password);   
        } catch (DAOException e) {
        	 throw new SecurityException("Authentication failed for user: " + username, e);
        }
    }
}
