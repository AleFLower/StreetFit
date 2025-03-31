package com.StreetFit.DAO;

import com.StreetFit.DAOJDBC.LoginProcedureDAO;

public class JDBCFactory extends FactorySingletonDAO {
    @Override
    public LoginDAO getLoginDAO() {
        return new LoginProcedureDAO();
    }
    
    
    //other  future methods here
}
