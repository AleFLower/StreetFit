package com.streetfit.DAO;

import com.streetfit.DaoInMemory.InMemoryLoginDAO;
public class InMemoryFactory extends FactorySingletonDAO {
    @Override
    public LoginDAO getLoginDAO() {
        return new InMemoryLoginDAO();
    }
    
    //other  future methods here
}
