package com.streetfit.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        try (InputStream input = new FileInputStream("res/config.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            String viewType = properties.getProperty("VIEW_TYPE");
            if (viewType == null) {
                throw new IllegalStateException("Error: VIEW_TYPE not specified in config file.");
            }

            // Scelta tra CLI e FX in base alla configurazione
            switch (MainType.valueOf(viewType)) {
                case CLI:
                    MainCLI.run();
                    break;
                case FX:
                    MainFX.run();
                    break;
                default:
                    throw new IllegalArgumentException("Error: View type not valid -> " + viewType);
            }
        } catch (IOException e) {
            System.err.println("Error while loading configuration: " + e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("Configuration error: " + e.getMessage());
        }
    }
}
