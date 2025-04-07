package com.streetfit.dao;

import com.streetfit.daofs.LoginFSDAO;

public class FSFactory implements DaoFactory{

	public Dao getLoginDAO() {
		return new LoginFSDAO();     
	}
	
	 //other  future methods here
}
