package com.streetfit.dao;

import com.streetfit.daoinmemory.InMemoryLoginDAO;
public class InMemoryFactory extends FactorySingletonDAO {
    @Override
    public LoginDAO getLoginDAO() {
        return new InMemoryLoginDAO();
    }
    
    //other  future methods here
}
