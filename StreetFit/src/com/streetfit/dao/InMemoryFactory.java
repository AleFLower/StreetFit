package com.streetfit.dao;

import com.streetfit.daoinmemory.InMemoryLoginDAO;

public class InMemoryFactory implements DaoFactory {
    @Override
    public Dao getLoginDAO() {
        return new InMemoryLoginDAO();
    }
    
    //other  future methods here
}
