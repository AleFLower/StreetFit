package com.streetfit.dao;

import com.streetfit.daojdbc.AddStageProcedureDAO;
import com.streetfit.daojdbc.JoinStageProcedureDao;
import com.streetfit.daojdbc.LoginProcedureDAO;

public class JDBCFactory implements DaoFactory {
	
	//methods to return the procedure at database level that does the work in the persistence layer

    public LoginDao getLoginDAO() {
        return new LoginProcedureDAO();
    }
    
    public AddStageDao getAddStageDao() {
    	return new AddStageProcedureDAO();
    }

	@Override
	public JoinStageDao getJoinStageDao() {
		return new JoinStageProcedureDao();
	}
}
