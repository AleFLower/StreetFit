package com.streetfit.dao;

import com.streetfit.daojdbc.AddStageProcedureDAO;
import com.streetfit.daojdbc.LoginProcedureDAO;

public class JDBCFactory implements DaoFactory {

    public LoginDao getLoginDAO() {
        return new LoginProcedureDAO();
    }
    
    public AddStageDao getAddStageDao() {
    	return new AddStageProcedureDAO();
    }
}
