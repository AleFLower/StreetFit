package main.java.com.streetfit.daojdbc;


import java.io.*;
import java.sql.*;
import java.util.Properties;

import main.java.com.streetfit.model.Role;

public class ConnectionFactory {
    private static Connection connection;

    private ConnectionFactory() {}

    static {
        // Does not work if generating a jar file
        try (InputStream input = new FileInputStream("res/db.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            String connectionUrl = properties.getProperty("CONNECTION_URL");
            String user = properties.getProperty("LOGIN_USER");
            String pass = properties.getProperty("LOGIN_PASS");

            connection = DriverManager.getConnection(connectionUrl, user, pass);
        } catch (IOException | SQLException e) {
        	throw new IllegalArgumentException("Error");
        }
    }

    public static Connection getConnection()  {
        return connection;
    }

    public static void changeRole(Role role) {
        try {
            // Chiudi la connessione esistente
            connection.close();

            // Crea una nuova connessione usando il file di configurazione
            connection = createConnectionForRole(role);

        } catch (SQLException e) {
            // Gestione dell'errore per la chiusura della connessione
          throw new IllegalArgumentException("Error");  //for now, we will adjust later...
        }
    }
    

    private static Connection createConnectionForRole(Role role) {
        try (InputStream input = new FileInputStream("res/db.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            String connectionUrl = properties.getProperty("CONNECTION_URL");
            String user = properties.getProperty(role.name() + "_USER");
            String pass = properties.getProperty(role.name() + "_PASS");

            // Restituisce la nuova connessione
            return DriverManager.getConnection(connectionUrl, user, pass);

        } catch (IOException | SQLException e) {
  
       
            return null; // Se si verifica un errore, restituisci null
        }
    }

}