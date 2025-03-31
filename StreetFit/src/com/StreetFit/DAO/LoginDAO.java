package com.StreetFit.DAO;

import com.StreetFit.exception.DAOException;
import com.StreetFit.model.Credentials;

public interface LoginDAO {
    Credentials getCredentials(String username, String password) throws DAOException;
}
