package com.streetfit.dao;

import com.streetfit.exception.DAOException;
import com.streetfit.model.Credentials;

public interface Dao {
    Credentials getCredentials(String username, String password) throws DAOException;
    //rename Dao into DAO and add all methods here(?)
}
