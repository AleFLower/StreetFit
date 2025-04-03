package com.streetfit.DAO;

import com.streetfit.DAOFS.LoginFSDAO;

public class FSFactory extends FactorySingletonDAO{

	public LoginDAO getLoginDAO() {
		return new LoginFSDAO();     
	}
	
	 //other  future methods here
}
