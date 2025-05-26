package main.java.com.streetfit.controller;

import java.util.List;

import main.java.com.streetfit.dao.AddStageDao;
import main.java.com.streetfit.dao.FactorySingletonDAO;
import main.java.com.streetfit.model.TrainingStage;
import main.java.com.streetfit.exception.*;

public class AddStageController{  //controller that communicates with DAO
	
	private AddStageDao dao;   //it communicates with DAO, whatever it is(FS, JDBC or MEMORY)
	


	public AddStageController() {
		try {
			this.dao = FactorySingletonDAO.getDefaultDAO().getAddStageDao();  //get the DAO for the chosen persistence layer
		}
		catch(RuntimeException e) {
			throw new IllegalStateException("Failed to initialize  due to DAO error", e);
		}
	}
	
	public void addstage(TrainingStage stage) {
		
		if(dao == null) {
			throw new IllegalStateException("Error");
		}
		
		try {			
			dao.addStage(stage);  //it calls the method that every DAO must implements(it is written in the interface AddStageDAO)
			  
		}
		catch(DAOException e) {
			
			throw new IllegalStateException("Dao Error");
		}
	}
	
	public List<TrainingStage> getAllStages() {
		
		List <TrainingStage> stageList;
		
		if(dao == null) {
			throw new IllegalStateException("Error");
		}
		try {
			stageList = dao.getStages();
		}catch(DAOException e) {
	
		throw new IllegalStateException("Dao Error");
	     }
		
		return stageList;
	}
}
