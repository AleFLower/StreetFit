package main.java.com.streetfit.controller;


import main.java.com.streetfit.dao.DAOFactory;
import main.java.com.streetfit.dao.LoginDao;

import main.java.com.streetfit.exception.DAOException;
import main.java.com.streetfit.model.Credentials;

public class LoginController  {
    private LoginDao loginDAO;

    public LoginController() {
        try {
            this.loginDAO = DAOFactory.getDefaultDAO().getLoginDAO();  
        } catch (RuntimeException e) {
        
        	throw new IllegalStateException("Failed to initialize LoginController due to DAO error", e);
        }
    }

    public void signup(Credentials cred) {
    	   if (loginDAO == null) {
               throw new IllegalStateException("Error");
           }
    	   try {
              loginDAO.signup(cred);   
           } catch (DAOException e) {
        	 
        		 throw new SecurityException("Signup failed");
           }
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
