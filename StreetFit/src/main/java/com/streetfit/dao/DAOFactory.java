package main.java.com.streetfit.dao;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DAOFactory {

    public abstract LoginDao getLoginDAO();
    public abstract AddStageDao getAddStageDao();
    public abstract JoinStageDao getJoinStageDao();

    public static DAOFactory getDefaultDAO() {
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream("res/config.properties")) {
            properties.load(input);
            String daoType = properties.getProperty("DAO_TYPE");

            if (daoType == null) {
                throw new IllegalStateException("DAO_TYPE not specified in configuration.");
            }

            return switch (daoType.toUpperCase()) {
                case "JDBC" -> new JDBCDAOFactory();
                case "FS" -> new FSDAOFactory();
                case "MEMORY" -> new InMemoryDAOFactory();
                default -> throw new IllegalArgumentException("Unsupported DAO_TYPE: " + daoType);
            };
        } catch (IOException e) {
            throw new IllegalStateException("Could not load DAO config: " + e.getMessage(), e);
        }
    }
}
