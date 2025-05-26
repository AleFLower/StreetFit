package main.java.com.streetfit.dao;

import main.java.com.streetfit.daoinmemory.InMemoryAddStageDAO;
import main.java.com.streetfit.daoinmemory.InMemoryLoginDAO;
import main.java.com.streetfit.daoinmemory.JoinStageInMemoryDao;

public class InMemoryFactory implements DaoFactory {
	
	//methods to return the procedure at memory level that does the work in the persistence layer
	
    @Override
    public LoginDao getLoginDAO() {
        return new InMemoryLoginDAO();
    }
    
    
  public AddStageDao getAddStageDao() {
	  return new InMemoryAddStageDAO();
  }
  
  public JoinStageDao getJoinStageDao() {
	  return new JoinStageInMemoryDao();
  }
}
