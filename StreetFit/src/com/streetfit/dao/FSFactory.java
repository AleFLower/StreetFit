package com.streetfit.dao;

import com.streetfit.daofs.LoginFSDAO;
import com.streetfit.daofs.AddStageFSDAO;

public class FSFactory implements DaoFactory{

	public LoginDao getLoginDAO() {
		return new LoginFSDAO();     
	}
	
	public AddStageDao getAddStageDao() {
		return new AddStageFSDAO();
	}
	
	
}
