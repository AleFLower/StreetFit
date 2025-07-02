package test.java.com.streetfit;

import static org.junit.jupiter.api.Assertions.assertEquals;  // Cambia da org.junit.Assert a org.junit.jupiter.api.Assertions

import java.sql.Date;

import org.junit.jupiter.api.Test;

import main.java.com.streetfit.beans.TrainingStageBean;
import main.java.com.streetfit.controller.AddStageController;


class TestAddStage {

    @Test
    // test che verifica se viene aggiunta una nuova stage, allora la lista di stage ha dimensione 1
    void testCreateStage() {
        int dim = 0;
        TrainingStageBean bean = new TrainingStageBean("Streetworkout RM", "circuit PHA", "functional",Date.valueOf("2025-10-10"),"Rome", 20 );
        AddStageController controller = new AddStageController();

        try {
            controller.addstage(bean);
            dim = controller.getAllStages().size();
        } catch(Exception e) {
            dim = -1;
        }

        assertEquals(1, dim);  // Usa assertEquals di JUnit 5
    }
}
