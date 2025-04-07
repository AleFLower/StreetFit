package com.streetfit.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class FactorySingletonDAO {
    protected static FactorySingletonDAO instance = null;

    protected FactorySingletonDAO() {}

    public static FactorySingletonDAO getDefaultDAO() {
        if (instance == null) {
            // Spostiamo la logica di configurazione in un metodo separato
            instance = createFactoryFromConfig();
        }
        return instance;
    }

    private static FactorySingletonDAO createFactoryFromConfig() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("res/config.properties")) {
            properties.load(input);
            String daoType = properties.getProperty("DAO_TYPE");

            if (daoType == null) {
                throw new IllegalStateException("Error: DAO type not specified in configuration file.");
            }

            return createFactory(daoType);
        } catch (IOException e) {
            throw new IllegalStateException("Error while loading config file: " + e.getMessage(), e);
        }
    }

    private static FactorySingletonDAO createFactory(String daoType) {
        switch (daoType.toUpperCase()) {
            case "JDBC":
                return new JDBCFactory();
            case "FS":
                return new FSFactory();
            case "MEMORY":
                return new InMemoryFactory();
            default:
                throw new IllegalArgumentException("Error: DAO type not valid -> " + daoType);
        }
    }

    // Metodi astratti per le procedure DAO
    public abstract Dao getLoginDAO();
}


