package main.java.com.streetfit.dao;

import main.java.com.streetfit.daojdbc.AddStageProcedureDAO;
import main.java.com.streetfit.daojdbc.JoinStageProcedureDao;
import main.java.com.streetfit.daojdbc.LoginProcedureDAO;

public class JDBCDAOFactory extends DAOFactory {
	
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
