package com.streetfit.daojdbc;


import java.io.*;
import java.sql.*;
import java.util.Properties;

import com.streetfit.model.Role;

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
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return connection;
    }

    public static void changeRole(Role role) {
        try {
            connection.close(); // Close existing connection

            try (InputStream input = new FileInputStream("res/db.properties")) {
                Properties properties = new Properties();
                properties.load(input);

                String connectionUrl = properties.getProperty("CONNECTION_URL");
                String user = properties.getProperty(role.name() + "_USER");
                String pass = properties.getProperty(role.name() + "_PASS");

                connection = DriverManager.getConnection(connectionUrl, user, pass);
            } catch (IOException | SQLException e) {
                e.printStackTrace(); // Handle exceptions here
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the case when closing the connection fails
        }
    }
}
