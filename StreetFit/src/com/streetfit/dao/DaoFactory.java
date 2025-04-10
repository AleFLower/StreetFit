package com.streetfit.dao;

public interface DaoFactory {
	LoginDao getLoginDAO();
	AddStageDao getAddStageDao();
}
