package com.streetfit.daofs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.streetfit.dao.LoginDao;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Credentials;
import com.streetfit.model.Role;


public class LoginFSDAO implements LoginDao{
    private static final String CSV_FILE = "res/users.csv"; // Percorso del file CSV
    
    @Override
    public Credentials getCredentials(String username, String password) throws DAOException {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                // Una volta letta l'intestazione, la salto
                if (!headerSkipped) {
                    headerSkipped = true;
                } else {
                    String[] data = line.split(",");
                    // Ignora righe malformate
                    if (data.length != 3) {
                        continue;
                    }

                    String fileUsername = data[0].trim();
                    String filePassword = data[1].trim();
                    String fileRole = data[2].trim();

                    // Confronta username e password (usa SHA-256 ora)
                    if (fileUsername.equals(username) && filePassword.equals(hashSHA256(password))) {
                        return new Credentials(username, password, Role.valueOf(fileRole.toUpperCase()));
                    }
                }
            }
        } catch (IOException e) {
            throw new DAOException("Error while reading CSV file: " + e.getMessage());
        }

        return null; // Nessuna corrispondenza trovata
    }

    // Metodo per calcolare l'hash SHA-256 della password
    private String hashSHA256(String input) {
        try {
            // Usa SHA-256 al posto di MD5
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }
}
