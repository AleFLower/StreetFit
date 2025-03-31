package com.StreetFit.DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class FactorySingletonDAO {
    protected static FactorySingletonDAO instance = null;

    protected FactorySingletonDAO() {}

    public static FactorySingletonDAO getDefaultDAO() {
        if (instance == null) {
            Properties properties = new Properties();
            try (InputStream input = new FileInputStream("res/config.properties")) {
                properties.load(input);
                String daoType = properties.getProperty("DAO_TYPE");

                if (daoType == null) {
                    throw new IllegalStateException("Error: DAO type not specified in configuration file.");
                }

                switch (daoType.toUpperCase()) {
                    case "JDBC":
                        instance = new JDBCFactory();
                        break;
                    case "FS":
                        instance = new FSFactory();
                        break;
                    case "MEMORY":
                        instance = new InMemoryFactory();
                        break;
                    default:
                        throw new IllegalArgumentException("Error: DAO type not valid -> " + daoType);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error while loading config file: " + e.getMessage(), e);
            }
        }
        return instance;
    }

    // Metodi astratti per le procedure DAO
    public abstract LoginDAO getLoginDAO();
    
    //other  future methods here
}

