package com.StreetFit.DAO;

import com.StreetFit.DaoInMemory.InMemoryLoginDAO;
public class InMemoryFactory extends FactorySingletonDAO {
    @Override
    public LoginDAO getLoginDAO() {
        return new InMemoryLoginDAO();
    }
    
    //other  future methods here
}
