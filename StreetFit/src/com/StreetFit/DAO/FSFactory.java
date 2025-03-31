package com.StreetFit.DAO;

import com.StreetFit.DAOFS.LoginFSDAO;

public class FSFactory extends FactorySingletonDAO{

	public LoginDAO getLoginDAO() {
		return new LoginFSDAO();     
	}
	
	 //other  future methods here
}
