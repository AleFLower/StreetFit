package com.streetfit.dao;

public interface DaoFactory {
	//methods to GET the default DAO for every use cases
	LoginDao getLoginDAO();
	AddStageDao getAddStageDao();
	JoinStageDao getJoinStageDao();
}
