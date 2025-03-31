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
            this.loginDAO = FactorySingletonDAO.getDefaultDAO().getLoginDAO();  //se jdbc, questo sarà pggetto loginProcedureDAO, se FS ci sarà un altra classe che restituirà un tipo di loginDAO, se in memory un altro
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
            credentials = loginDAO.getCredentials(username, password);   //ogni DAO che implemento avrà il suo metodo getCredentials, non gli importa che sia DB, FS, o memory
            return credentials;
        } catch (DAOException e) {
            throw new RuntimeException("Authentication error: " + e.getMessage());
        }
    }
}
