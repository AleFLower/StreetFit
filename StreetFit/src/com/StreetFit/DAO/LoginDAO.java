package com.streetfit.DAO;

import com.streetfit.exception.DAOException;
import com.streetfit.model.Credentials;

public interface LoginDAO {
    Credentials getCredentials(String username, String password) throws DAOException;
}
