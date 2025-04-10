package com.streetfit.dao;

import com.streetfit.exception.DAOException;
import com.streetfit.model.Stage;

public interface AddStageDao {
  public void  addStage(Stage s) throws DAOException;
}
