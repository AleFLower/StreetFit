package com.streetfit.controller;

import com.streetfit.dao.AddStageDao;
import com.streetfit.dao.FactorySingletonDAO;
import com.streetfit.model.Stage;
import com.streetfit.exception.*;

public class AddStageController{  //controller that communicates with DAO
	
	private AddStageDao dao;
	
	public AddStageController() {
		try {
			this.dao = FactorySingletonDAO.getDefaultDAO().getAddStageDao();
		}
		catch(RuntimeException e) {
			throw new IllegalStateException("Failed to initialize  due to DAO error", e);
		}
	}
	
	public void addstage(Stage stage) {
		
		if(dao == null) {
			throw new IllegalStateException("Error");
		}
		
		try {			
			dao.addStage(stage);
		}
		catch(DAOException e) {
			throw new IllegalStateException("Dao Error");
		}
	}
}
