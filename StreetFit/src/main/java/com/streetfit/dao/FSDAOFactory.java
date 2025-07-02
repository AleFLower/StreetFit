package main.java.com.streetfit.dao;

import main.java.com.streetfit.daofs.LoginFSDAO;
import main.java.com.streetfit.daofs.AddStageFSDAO;
import main.java.com.streetfit.daofs.JoinStageFSDao;

public class FSDAOFactory extends DAOFactory{
	
	//methods to return the procedure at file system level that does the work in the persistence layer

	public LoginDao getLoginDAO() {
		return new LoginFSDAO();     
	}
	
	public AddStageDao getAddStageDao() {
		return new AddStageFSDAO();
	}
	
	public JoinStageDao getJoinStageDao() {
		return new JoinStageFSDao();
	}
	
}
