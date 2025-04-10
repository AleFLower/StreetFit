package com.streetfit.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FactorySingletonDAO {

    private static DaoFactory instance = null;

    private FactorySingletonDAO() {}

    public static DaoFactory getDefaultDAO() {
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
                throw new IllegalStateException("Error while loading config file: " + e.getMessage(), e);
            }
        }
        return instance;
    }
}

