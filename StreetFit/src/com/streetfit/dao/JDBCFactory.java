package com.streetfit.dao;

import com.streetfit.daojdbc.LoginProcedureDAO;

public class JDBCFactory extends FactorySingletonDAO {
    @Override
    public Dao getLoginDAO() {
        return new LoginProcedureDAO();
    }
    
    
    //other  future methods here
}
