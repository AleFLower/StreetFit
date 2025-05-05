package com.streetfit.daoinmemory;

import com.streetfit.model.TrainingStage;

public interface Observer {
    void update(TrainingStage stage);
}