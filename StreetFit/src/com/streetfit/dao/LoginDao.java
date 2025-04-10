package com.streetfit.dao;

import com.streetfit.exception.DAOException;
import com.streetfit.model.Credentials;

public interface LoginDao {
	  Credentials getCredentials(String username, String password) throws DAOException;
}
