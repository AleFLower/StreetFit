package com.streetfit.dao;

import com.streetfit.daofs.LoginFSDAO;

public class FSFactory extends FactorySingletonDAO{

	public LoginDAO getLoginDAO() {
		return new LoginFSDAO();     
	}
	
	 //other  future methods here
}
