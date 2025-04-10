package com.streetfit.daoinmemory;

import java.util.ArrayList;
import java.util.List;

import com.streetfit.dao.AddStageDao;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Stage;

public class InMemoryAddStageDAO implements AddStageDao {
	
    private static List<Stage> stageList = new ArrayList<>();

    @Override
    public void addStage(Stage stage) throws DAOException {

        if (stage == null) {
            throw new DAOException("Invalid stage data.");
        }
        stageList.add(stage);
        
       System.out.println(stageList);
    }

    public List<Stage> getAllStages() {
        return new ArrayList<>(stageList);
    }

}
