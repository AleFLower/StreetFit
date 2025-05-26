package main.java.com.streetfit.dao;

import main.java.com.streetfit.exception.DAOException;
import main.java.com.streetfit.model.Credentials;

public interface LoginDao {
	//method that every "procedure" must implement
	  Credentials getCredentials(String username, String password) throws DAOException;
	  public void signup(Credentials cred)throws DAOException;
}
