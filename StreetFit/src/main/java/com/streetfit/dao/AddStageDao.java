package main.java.com.streetfit.dao;

import java.util.List;

import main.java.com.streetfit.exception.DAOException;
import main.java.com.streetfit.model.TrainingStage;

public interface AddStageDao {  
	//methods that every addstage "procedure" must implements
  public void  addStage(TrainingStage s) throws DAOException;
  public List<TrainingStage> getStages() throws DAOException;
}
