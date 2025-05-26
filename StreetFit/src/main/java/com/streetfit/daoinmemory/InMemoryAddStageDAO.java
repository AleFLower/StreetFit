package main.java.com.streetfit.daoinmemory;

import java.util.ArrayList;
import java.util.List;

import main.java.com.streetfit.dao.AddStageDao;
import main.java.com.streetfit.exception.DAOException;
import main.java.com.streetfit.model.TrainingStage;

public class InMemoryAddStageDAO implements AddStageDao {
	
    private static List<TrainingStage> stageList = new ArrayList<>();

    @Override
    public void addStage(TrainingStage stage) throws DAOException {

        if (stage == null) {
            throw new DAOException("Invalid stage data.");
        }
        List<TrainingStage> existingStages = getStages(); // carica stage esistenti

        for (TrainingStage existing : existingStages) {
        	if (existing.getTitle().equals(stage.getTitle())) {
        	    throw new DAOException("Stage already exists");
        	}
        }
        stageList.add(stage);
        
      
    }

    public List<TrainingStage> getAllStages() {
        return new ArrayList<>(stageList);
    }

	@Override
	public List<TrainingStage> getStages() throws DAOException {
		
		return stageList;
	}

}
