package test.java.com.streetfit;

import static org.junit.jupiter.api.Assertions.assertEquals;  // Cambia da org.junit.Assert a org.junit.jupiter.api.Assertions

import java.sql.Date;

import org.junit.jupiter.api.Test;

import main.java.com.streetfit.beans.StageBean;
import main.java.com.streetfit.controller.AddStageController;
import main.java.com.streetfit.model.TrainingStage;

class TestAddStage {

    @Test
    // test che verifica se viene aggiunta una nuova stage, allora la lista di stage ha dimensione 1
    void testCreateStage() {
        int dim = 0;
        StageBean bean = new StageBean("Streetworkout RM", "circuit PHA", "functional",Date.valueOf("2025-10-10"),"Rome", 20 );
        AddStageController controller = new AddStageController();

        try {
            controller.addstage(new TrainingStage(bean.getTitle(), bean.getItinerary(), bean.getCategory(), bean.getDate(), bean.getPlace(), bean.getMaxParticipants()));
            dim = controller.getAllStages().size();
        } catch(Exception e) {
            dim = -1;
        }

        assertEquals(1, dim);  // Usa assertEquals di JUnit 5
    }
}

