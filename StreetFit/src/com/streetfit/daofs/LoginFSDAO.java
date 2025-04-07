package com.streetfit.daofs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.streetfit.dao.Dao;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Credentials;
import com.streetfit.model.Role;

public class LoginFSDAO implements Dao{
	private static final String CSV_FILE = "res/users.csv"; // Percorso del file CSV
	

	@Override
    public Credentials getCredentials(String username, String password) throws DAOException {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                // Una volta letta l'intestazione, segnalo che è stata saltata
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // Continua a leggere la riga successiva
                }

                String[] data = line.split(",");
                if (data.length != 3) continue; // Ignora righe malformate

                String fileUsername = data[0].trim();
                String filePassword = data[1].trim();
                String fileRole = data[2].trim();
                
                // Confronta username e password con hash MD5
                if (fileUsername.equals(username) && filePassword.equals(hashMD5(password))) {
                    return new Credentials(username, password, Role.valueOf(fileRole.toUpperCase()));
                }
            }
        } catch (IOException e) {
            throw new DAOException("Error while reading CSV file: " + e.getMessage());
        }
        return null; // Nessuna corrispondenza trovata
    }

    // Metodo per calcolare l'hash MD5 della password
    private String hashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
        	throw new IllegalStateException("MD5 algorithm not available", e);
        }
    }
}
