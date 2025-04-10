package com.streetfit.dao;

import com.streetfit.daoinmemory.InMemoryAddStageDAO;
import com.streetfit.daoinmemory.InMemoryLoginDAO;

public class InMemoryFactory implements DaoFactory {
    @Override
    public LoginDao getLoginDAO() {
        return new InMemoryLoginDAO();
    }
    
    
  public AddStageDao getAddStageDao() {
	  return new InMemoryAddStageDAO();
  }
}
