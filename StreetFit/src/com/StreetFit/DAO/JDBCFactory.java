package com.streetfit.DAO;

import com.streetfit.DAOJDBC.LoginProcedureDAO;

public class JDBCFactory extends FactorySingletonDAO {
    @Override
    public LoginDAO getLoginDAO() {
        return new LoginProcedureDAO();
    }
    
    
    //other  future methods here
}
