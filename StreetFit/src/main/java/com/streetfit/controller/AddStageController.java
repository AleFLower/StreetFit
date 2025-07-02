package main.java.com.streetfit.controller;

import java.util.ArrayList;
import java.util.List;

import main.java.com.streetfit.beans.TrainingStageBean;
import main.java.com.streetfit.dao.AddStageDao;
import main.java.com.streetfit.dao.DAOFactory;
import main.java.com.streetfit.model.TrainingStage;
import main.java.com.streetfit.exception.*;

public class AddStageController{  //controller that communicates with DAO
	
	private AddStageDao dao;   //it communicates with DAO, whatever it is(FS, JDBC or MEMORY)
	
	public AddStageController() {
		try {
			this.dao = DAOFactory.getDefaultDAO().getAddStageDao();  //get the DAO for the chosen persistence layer
		}
		catch(RuntimeException e) {
			throw new IllegalStateException("Failed to initialize  due to DAO error", e);
		}
	}
	
	public void addstage(TrainingStageBean stageBean) {
		
		TrainingStage stage = new TrainingStage(stageBean.getTitle(),stageBean.getItinerary(),stageBean.getCategory(),stageBean.getDate(),stageBean.getPlace(),stageBean.getMaxParticipants());
		
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
	
	public List<TrainingStageBean> getAllStages() {
		
		List <TrainingStage> stageList;
		
		if(dao == null) {
			throw new IllegalStateException("Error");
		}
		try {
			stageList = dao.getStages();
		}catch(DAOException e) {
	
		throw new IllegalStateException("Dao Error");
	     }
		
		 // 🔁 Conversione da Model a Bean
	    List<TrainingStageBean> beanList = new ArrayList<>();
	    for (TrainingStage s : stageList) {
	        beanList.add(new TrainingStageBean(
	            s.getTitle(),
	            s.getItinerary(),
	            s.getCategory(),
	            s.getDate(),
	            s.getLocation(),
	            s.getMaxParticipants()
	        ));
	    }
		
		return beanList;
	}
}
